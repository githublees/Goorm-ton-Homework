import React, { useEffect, useState } from "react";
import { FaRegTrashCan } from "react-icons/fa6";
import "../../styles/CartModal.css";
import { NavLink } from "react-router-dom";
import styled from "styled-components";
import { useAppDispatch, useAppSelector } from "../../redux/store";
import { ModalType, unActive } from "../../redux/ModalSlice";
import { removeProduct } from "../../redux/CartSlice";

const CartModal = ({ modal }: { modal: ModalType }) => {
  const dispatch = useAppDispatch();
  const product = useAppSelector((state) => state.product);

  const [render, setRender] = useState(modal.isActive);

  useEffect(() => {
    if (modal) {
      setRender(true);
    }
  }, [modal]);

  const onAnimationEnd = () => {
    if (render) {
      setTimeout(() => {
        setRender(false);
      }, 1600);
    } else {
      dispatch(unActive());
    }
  };

  const handleDelete = () => {
    dispatch(removeProduct(product.id));
    dispatch(unActive());
  };

  return (
    <div className="cartmodal-container">
      <div className="wrapper-modal">
        <div
          className="modal"
          style={{
            animation: `${
              render ? "fadeIn 1s forwards" : "fadeOut 2s forwards"
            }`,
          }}
          onAnimationEnd={onAnimationEnd}
        >
          <button onClick={handleDelete} className="modal__delete">
            <FaRegTrashCan size="24" style={{ color: "gray" }} />
          </button>
          <div className="modal-container__img">
            <img className="modal__img" src={product.image} alt="img" />
          </div>
          <div className="modal-container">
            <div className="modal__category">{product.category}</div>
            <div className="modal__title">{product.title}</div>
            <div className="modal__exp">{`${product.description}`}</div>
          </div>
          <div className="modal__cost">가격: {`$${product.price}`}</div>
          {/* <button className="modal__cartbtn"> */}
          <NavStyle to="cart" style={{ textDecoration: "none", color: "grey" }}>
            장바구니 가기
          </NavStyle>
          {/* </button> */}
        </div>
      </div>
    </div>
  );
};

const NavStyle = styled(NavLink)`
  grid-column: 1/3;

  display: grid;
  align-items: center;

  font-size: 20px;
  font-weight: bold;
  border: 0px;
  cursor: pointer;
`;
export default CartModal;
