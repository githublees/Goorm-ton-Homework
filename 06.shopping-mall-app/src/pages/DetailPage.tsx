import React, { useEffect } from "react";
import { NavLink } from "react-router-dom";
import "../styles/DetailPage.css";
import styled from "styled-components";
import { useAppDispatch, useAppSelector } from "../redux/store";
import { addProduct } from "../redux/CartSlice";
import { active, unActive } from "../redux/ModalSlice";
import { SingleProduct } from "../redux/thunk/storeThunk";

const DetailPage = () => {
  const dispatch = useAppDispatch();
  const product = useAppSelector((state) => state.product);

  const handleModal = () => {
    dispatch(unActive());
    dispatch(SingleProduct(product.id));
    dispatch(addProduct(product));
    dispatch(active());
  };

  useEffect(() => {}, []);
  return (
    <div className="detail-container">
      <div className="detail-container__img">
        <img src={product.image} alt="img" />
      </div>
      <div className="detail-container-info">
        <span className="detail__category">{product.category}</span>
        <span className="detail__title">{product.title}</span>
        <span className="detail__cost">{`$${product.price}`}</span>
        <span className="detail__info">{product.description}</span>
        <div className="detail-container-box">
          <button onClick={handleModal}>장바구니에 담기</button>
          <NavStyle to="/cart" style={{ textDecoration: "none" }}>
            <span>장바구니 가기</span>
          </NavStyle>
        </div>
      </div>
    </div>
  );
};

const NavStyle = styled(NavLink)`
  display: flex;
  justify-content: center;
  align-items: center;

  width: 300px;
  height: 70px;

  font-size: 18px;
  font-weight: bold;
  color: white;
  border: 0px;
  border-radius: 5px;

  background-color: grey;
  color: white;

  &:hover {
    background-color: lightgray;
    color: white;
  }
`;

export default DetailPage;
