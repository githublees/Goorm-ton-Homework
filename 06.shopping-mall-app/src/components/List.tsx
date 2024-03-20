import { useEffect } from "react";
import "../styles/List.css";
import { NavLink } from "react-router-dom";
import styled from "styled-components";
import { useAppDispatch } from "../redux/store";
import { SingleProduct } from "../redux/thunk/storeThunk";
import { ProductType } from "../redux/SingleSlice";
import { active, unActive } from "../redux/ModalSlice";
import { addProduct } from "../redux/CartSlice";

const List = ({ product }: { product: ProductType }) => {
  const dispatch = useAppDispatch();

  const handleModal = () => {
    dispatch(unActive());
    dispatch(SingleProduct(product.id));
    dispatch(addProduct(product));
    dispatch(active());
  };

  useEffect(() => {}, [dispatch]);

  return (
    <div className="list-item-container">
      <NavStyle
        to={`detail/${product.id}`}
        onClick={() => dispatch(SingleProduct(product.id))}
      >
        <img className="list-item__img" src={product.image} alt="img" />
      </NavStyle>
      <div className="list-item__title">
        <span>{product.title}</span>
      </div>
      {/* <div className="list-item-box"> */}
      <button onClick={handleModal} className="list-item__cart-btn">
        장바구니 담기
      </button>
      <div className="list-item__cost">
        <span>{`$${product.price}`}</span>
        {/* </div> */}
      </div>
    </div>
  );
};

const NavStyle = styled(NavLink)`
  margin: 20px 20px 0px 20px;
  grid-column: 1/-1;
  border: 1px solid lightgrey;
  cursor: pointer;
`;

export default List;
