import React, { useEffect } from "react";
import "../styles/CartPage.css";
import CartLists from "../components/cart/CartLists";
import { GiShoppingCart } from "react-icons/gi";
import { useAppDispatch, useAppSelector } from "../redux/store";
import { Link } from "react-router-dom";
import { removeAlarm } from "../redux/ModalSlice";

const CartPage = () => {
  const cart = useAppSelector((state) => state.cart);
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(removeAlarm());
  }, [cart, dispatch]);

  if (cart.length === 0) {
    return (
      <div className="cart-container">
        <GiShoppingCart size="400" />
        <span>Cart가 비어있습니다.</span>
        <Link
          to="/"
          style={{ paddingLeft: "40px", color: "grey", margin: "10px" }}
        >
          계속 쇼핑하기
        </Link>
      </div>
    );
  } else {
    return (
      <div>
        <CartLists cart={cart} />
      </div>
    );
  }
};

export default CartPage;
