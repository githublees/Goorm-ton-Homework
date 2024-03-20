import { useCallback, useEffect, useState } from "react";
import CartList from "./CartList";
import "../../styles/CartLists.css";
import { useAppDispatch } from "../../redux/store";
import { CartType, calcProduct } from "../../redux/CartSlice";

const CartLists = ({ cart }: { cart: CartType[] }) => {
  const [total, setTotal] = useState(0);

  const dispatch = useAppDispatch();

  const totalCalc = useCallback(() => {
    let sum: number = 0;
    cart.forEach((list) => {
      sum += list.price * list.count;
    });

    setTotal(sum);
  }, [cart]);

  useEffect(() => {
    totalCalc();
  }, [totalCalc]);

  return (
    <div className="cartlists-container">
      {cart.map((list) => (
        <CartList key={list.id} cart={list} />
      ))}
      <div className="cartlists-container-box">
        <span>합계: ${Math.round(total * 100) / 100}</span>
        <button onClick={() => dispatch(calcProduct())}>계산하기</button>
      </div>
    </div>
  );
};

export default CartLists;
