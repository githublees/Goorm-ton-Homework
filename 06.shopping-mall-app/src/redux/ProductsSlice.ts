import { createSlice } from "@reduxjs/toolkit";
import { CategoryProduct, limitProduct } from "./thunk/storeThunk";
import { ProductType } from "./SingleSlice";

const initialState: ProductType[] = [
  // {
  //   id: 0,
  //   title: "",
  //   price: 0,
  //   category: "",
  //   description: "",
  //   image: "",
  // },
];

export const productsSlice = createSlice({
  name: "products",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(limitProduct.pending, () => {
        // console.log("loading");
      })
      .addCase(limitProduct.fulfilled, (state, action) => {
        return action.payload;
      })
      .addCase(limitProduct.rejected, () => {
        console.log("failed");
      });
    builder
      .addCase(CategoryProduct.pending, () => {
        // console.log("loading");
      })
      .addCase(CategoryProduct.fulfilled, (state, action) => {
        return action.payload;
      })
      .addCase(CategoryProduct.rejected, () => {
        console.log("failed");
      });
  },
});

export default productsSlice.reducer;
