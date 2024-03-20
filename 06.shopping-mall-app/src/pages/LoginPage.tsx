import React, { ChangeEvent, useEffect, useState } from "react";
import {
  authService,
  setPersistence,
  browserSessionPersistence,
  signInWithEmailAndPassword,
} from "../firebase/firebaseConfig";
import "../styles/LoginPage.css";
import styled from "styled-components";
import { Link, useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { userSignIn } from "../redux/UserSlice";

const LoginPage = () => {
  const [isEmail, setIsEmail] = useState("");
  const [isPwd, setIsPwd] = useState("");
  const [errorMsg, setErrorMsg] = useState("");

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handleLogIn = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    // setPersistence란?
    // 인증 상태 지속성 설정
    // browserLocalPersistence(default): 브라우저가 닫히더라도 사용자 정보를 저장
    // browserSessionPersistence: 탭이 열려있는 동안에는 사용자 정보를 기억
    // inMemoryPersistence: 사용자 정보를 기억하지 않는다. 새로고침 시 로그아웃
    try {
      await setPersistence(authService, browserSessionPersistence);
      await signInWithEmailAndPassword(authService, isEmail, isPwd)
        .then(() => {
          dispatch(userSignIn({ email: isEmail, isActive: true }));
          navigate("/");
        })
        .catch((error) => {
          throw error.code;
        });
    } catch (error) {
      switch (error) {
        case "auth/user-not-found" || "auth/wrong-password":
          setErrorMsg("이메일 혹은 비밀번호가 일치하지 않습니다.");
          setIsPwd("");
          break;
        case "auth/missing-password":
          setErrorMsg("비밀번호 란이 비어있습니다.");
          break;
        case "auth/invalid-email":
          setErrorMsg("잘못된 이메일 형식입니다.");
          setIsEmail("");
          setIsPwd("");
          break;
        case "auth/too-many-requests":
          setErrorMsg("너무 많은 요청을 보냈습니다.");
          navigate("/login");
          break;
        default:
          setErrorMsg("로그인에 실패하였습니다.");
          setIsPwd("");
      }
    }
    // console.log(authService.currentUser);
  };

  const handleEmail = (e: ChangeEvent<HTMLInputElement>) => {
    setIsEmail(e.target.value);
  };
  const handlePwd = (e: ChangeEvent<HTMLInputElement>) => {
    setIsPwd(e.target.value);
  };

  useEffect(() => {}, []);

  return (
    <div className="login-container">
      <div className="login-container-form">
        <form onSubmit={handleLogIn}>
          <span>로그인</span>
          <input
            type="email"
            name="email"
            placeholder="email"
            value={isEmail}
            onChange={handleEmail}
          />
          <input
            type="text"
            name="pwd"
            placeholder="password"
            value={isPwd}
            onChange={handlePwd}
          />
          <div className="error-messege-container">
            <span style={{ color: "red" }}>{errorMsg}</span>
          </div>
          <button type="submit">로그인</button>
        </form>
        <NavStyle style={{ textDecoration: "none" }} to="/register">
          Go to SignUp
        </NavStyle>
      </div>
    </div>
  );
};

const NavStyle = styled(Link)`
  margin: 10px;

  font-size: 15px;

  border: 0px;
  border-bottom: 1px solid;

  color: blue;
  background-color: white;

  cursor: pointer;
  &:hover {
    color: blueviolet;
  }
`;

export default LoginPage;
