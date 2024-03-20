import { createSlice } from "@reduxjs/toolkit";
import { SingleProduct } from "./thunk/storeThunk";

export interface ProductType {
  id: number;
  title: string;
  price: number;
  category: string;
  description: string;
  image: string;
}

const initialState: ProductType = {
  id: 0,
  title: "",
  price: 0,
  category: "",
  description: "",
  image: "",
};

export const singleSlice = createSlice({
  name: "single",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(SingleProduct.pending, () => {
        // console.log("loading");
      })
      .addCase(SingleProduct.fulfilled, (state, action) => {
        return action.payload;
      })
      .addCase(SingleProduct.rejected, () => {
        console.log("failed");
      });
  },
});

export default singleSlice.reducer;
