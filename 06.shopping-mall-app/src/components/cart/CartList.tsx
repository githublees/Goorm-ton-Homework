import { useEffect } from "react";
import {
  CartType,
  decreaseProduct,
  increaseProduct,
  removeProduct,
} from "../../redux/CartSlice";
import { useAppDispatch } from "../../redux/store";
import "../../styles/CartList.css";
import { FaRegTrashCan } from "react-icons/fa6";
import { IoIosArrowDropright, IoIosArrowDropleft } from "react-icons/io";

const CartList = ({ cart }: { cart: CartType }) => {
  const dispatch = useAppDispatch();

  useEffect(() => {}, [cart]);
  return (
    <div className="cartlist-container">
      <div className="cartlist-container-detail">
        <div className="cartlist-container__img">
          <img className="cartlist-item__img" src={cart.image} alt="img" />
        </div>
        <div className="cartlist-container-info">
          <div className="cartlist__category">{cart.category}</div>
          <div className="cartlist__title">{cart.title}</div>
          <div className="cartlist__cost">{`$${cart.price} x ${cart.count} = $${
            Math.round(cart.price * cart.count * 100) / 100
          }`}</div>
        </div>
      </div>
      <div className="cartlist-container-calc">
        <button onClick={() => dispatch(increaseProduct(cart.id))}>
          <IoIosArrowDropleft size="26" />
        </button>
        <input type="number" value={cart.count} readOnly max="99" min="1" />
        <button onClick={() => dispatch(decreaseProduct(cart.id))}>
          <IoIosArrowDropright size="26" />
        </button>
      </div>
      <button className="cartlist__delete">
        <FaRegTrashCan
          size="24"
          style={{ color: "gray" }}
          onClick={() => dispatch(removeProduct(cart.id))}
        />
      </button>
    </div>
  );
};

export default CartList;
