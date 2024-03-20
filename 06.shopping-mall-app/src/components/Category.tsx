import { useEffect, useState } from "react";
import "../styles/Category.css";
import Lists from "./Lists";
import { useAppDispatch } from "../redux/store";
import { CategoryProduct, limitProduct } from "../redux/thunk/storeThunk";

const Category = () => {
  const [category, setCategory] = useState("All");

  const dispatch = useAppDispatch();

  useEffect(() => {
    if (category === "All") {
      dispatch(limitProduct(10));
    } else {
      dispatch(CategoryProduct(category));
    }
  }, [category, dispatch]);

  return (
    <div className="category-container">
      <div className="category-container-box">
        <span>Product</span>
        <div className="category-container__btn">
          <button onClick={() => setCategory("All")}>모두</button>
          <button onClick={() => setCategory("electronics")}>전자기기</button>
          <button onClick={() => setCategory("jewelery")}>의류</button>
          <button onClick={() => setCategory("men's clothing")}>
            남성의류
          </button>
          <button onClick={() => setCategory("women's clothing")}>
            여성의류
          </button>
        </div>
        <Lists />
      </div>
    </div>
  );
};

export default Category;
