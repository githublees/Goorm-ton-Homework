import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "../../api/axios";
import { requests } from "../../api/requests";
import { ProductType } from "../SingleSlice";

export const limitProduct = createAsyncThunk<ProductType[], number, {}>(
  "products/limitProduct",
  async (limit) => {
    const response = await axios.get(requests.fetchProduct, {
      params: {
        limit: limit,
      },
    });

    return response.data;
  }
);

export const CategoryProduct = createAsyncThunk<ProductType[], string, {}>(
  "products/CategoryProduct",
  async (category) => {
    const response = await axios.get(requests.fetchCategory + category);

    return response.data;
  }
);

export const SingleProduct = createAsyncThunk<ProductType, number, {}>(
  "single/SingleProduct",
  async (id) => {
    const response = await axios.get(`${requests.fetchProduct}/${id}`);

    return response.data;
  }
);
