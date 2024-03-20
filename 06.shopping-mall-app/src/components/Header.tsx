import { useEffect, useState } from "react";
import "../styles/Header.css";
import { Link, NavLink, useNavigate } from "react-router-dom";
import { LuShoppingCart } from "react-icons/lu";
import { MdLogin, MdLogout } from "react-icons/md";
import styled from "styled-components";
import CartModal from "./cart/CartModal";
import { authService } from "../firebase/firebaseConfig";
import { userSignOut } from "../redux/UserSlice";
import { useAppDispatch, useAppSelector } from "../redux/store";

const Header = () => {
  const [userStatus, setUserStatus] = useState(false);
  const dispatch = useAppDispatch();
  const modal = useAppSelector((state) => state.modal);
  const navigate = useNavigate();

  const handleLogOut = () => {
    authService
      .signOut()
      .then(() => {
        dispatch(userSignOut());
        navigate("/");
      })
      .catch((error) => {
        console.log(error);
      });
  };
  useEffect(() => {
    authService.onAuthStateChanged((user) => {
      if (user) {
        setUserStatus(true);
      } else {
        setUserStatus(false);
      }
    });
  }, [modal]);

  return (
    <div className="header">
      <div className="header-container">
        <div className="header-container__logo">
          <Link to="/" style={{ textDecoration: "none", color: "black" }}>
            Shop
          </Link>
        </div>
        <div className="header-container-info">
          <NavStyle to="cart" style={{ textDecoration: "none" }}>
            <LuShoppingCart size="24" />
          </NavStyle>
          {modal.alarm !== 0 ? (
            <div className="notifications-icon-container">
              <div className="notifications-count">{modal.alarm}</div>
            </div>
          ) : (
            ""
          )}
          {!userStatus ? (
            <NavStyle to="login" style={{ textDecoration: "none" }}>
              <MdLogin size="24" />
            </NavStyle>
          ) : (
            <NavStyle
              to="/"
              style={{ textDecoration: "none" }}
              onClick={handleLogOut}
            >
              <MdLogout size="24" />
            </NavStyle>
          )}
          {modal.isActive && <CartModal modal={modal} />}
        </div>
      </div>
    </div>
  );
};

const NavStyle = styled(NavLink)`
  padding-left: 20px;
  padding-top: 8px;

  border: none;
  background-color: transparent;
  color: black;
  cursor: pointer;

  &:hover {
    color: lightgray;
  }
`;

export default Header;
