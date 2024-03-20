import React, { useEffect } from "react";
import List from "./List";
import "../styles/Lists.css";
import { useAppSelector } from "../redux/store";

const Lists = () => {
  const products = useAppSelector((state) => state.products);

  useEffect(() => {}, [products]);
  return (
    <div className="list-container">
      {/* {list.map(()=> ())} */}
      <div className="list-container__products">
        <div className="list-container__showing">
          <span>showing: {products.length} items</span>
        </div>
        {products.map((list) => (
          <List key={list.id} product={list} />
        ))}
      </div>
    </div>
  );
};

export default Lists;
