import React, { ChangeEvent, useEffect, useState } from "react";
import {
  authService,
  createUserWithEmailAndPassword,
} from "../firebase/firebaseConfig";
import "../styles/LoginPage.css";
import styled from "styled-components";
import { Link, useNavigate } from "react-router-dom";

const RegisterPage = () => {
  const [isActive, setIsActive] = useState(false);
  const [isColor, setIsColor] = useState("white");
  const [isEmail, setIsEmail] = useState("");
  const [isPwd, setIsPwd] = useState("");
  const [isCheckPwd, setIsCheckPwd] = useState("");
  const [errorMsg, setErrorMsg] = useState(" ");

  const initialForm = () => {
    setIsEmail("");
    setIsPwd("");
    setIsCheckPwd("");
  };

  let navigate = useNavigate();

  useEffect(() => {
    if (isCheckPwd !== "") {
      if (isCheckPwd === isPwd && isPwd !== "") {
        setIsActive(true);
        setIsColor("green");
        setErrorMsg("비밀번호가 일치합니다.");
      } else {
        setIsActive(false);
        setIsColor("red");
        setErrorMsg("비밀번호가 서로 다릅니다.");
      }
    } else {
      setIsActive(false);
    }
  }, [isCheckPwd, isPwd]);

  const handleRegister = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      await createUserWithEmailAndPassword(authService, isEmail, isPwd)
        .then(() => {
          setErrorMsg(" ");
          alert("회원가입에 성공하였습니다.");
          navigate("/login");
        })
        .catch((error) => {
          throw error.code;
        });
    } catch (error) {
      setIsColor("red");
      switch (error) {
        case "auth/weak-password":
          setErrorMsg("비밀번호는 6자리 이상이어야 합니다");
          initialForm();
          break;
        case "auth/invalid-email":
          setErrorMsg("잘못된 이메일 주소입니다");
          initialForm();
          break;
        case "auth/email-already-in-use":
          setErrorMsg("이미 가입되어 있는 계정입니다.");
          initialForm();
          break;
        default:
          setErrorMsg("회원가입에 실패하였습니다.");
          initialForm();
          break;
      }
    }
  };

  const handleEmail = (e: ChangeEvent<HTMLInputElement>) => {
    setIsEmail(e.target.value);
  };
  const handlePwd = (e: ChangeEvent<HTMLInputElement>) => {
    setIsPwd(e.target.value);
  };
  const handleCheckPwd = (e: ChangeEvent<HTMLInputElement>) => {
    setIsCheckPwd(e.target.value);
  };

  return (
    <div className="login-container">
      <div className="login-container-form">
        <form onSubmit={handleRegister}>
          <span>회원가입</span>
          <input
            type="email"
            name="email"
            placeholder="email"
            value={isEmail}
            onChange={handleEmail}
          />
          <input
            type="password"
            name="pwd"
            placeholder="password"
            value={isPwd}
            onChange={handlePwd}
          />
          <input
            type="password"
            name="checkpwd"
            placeholder="password check"
            value={isCheckPwd}
            onChange={handleCheckPwd}
          />
          <div className="error-messege-container">
            <span style={{ color: `${isColor}` }}>{errorMsg}</span>
          </div>
          {isActive ? (
            <button type="submit">회원가입</button>
          ) : (
            <button disabled type="submit">
              회원가입
            </button>
          )}
        </form>
        <NavStyle style={{ textDecoration: "none" }} to="/login">
          Go to SignIn
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

export default RegisterPage;
