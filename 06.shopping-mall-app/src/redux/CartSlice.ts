import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { ProductType } from "./SingleSlice";

export interface CartType extends ProductType {
  count: number;
}

const initialState: CartType[] = [
  // {
  //   id: 0,
  //   title: "",
  //   price: 0,
  //   count: 0,
  //   category: "",
  //   description: "",
  //   image: "",
  // },
];

export const cartSlice = createSlice({
  name: "cart",
  initialState,
  reducers: {
    /* 수량 증가 */
    increaseProduct: (state: CartType[], action: PayloadAction<number>) => {
      state = state.map((item) => {
        if (item.id === action.payload) {
          if (item.count === 99) {
            return item;
          }
          item.count++;
        }

        return item;
      });
    },
    /* 수량 감소 */
    decreaseProduct: (state: CartType[], action: PayloadAction<number>) => {
      state = state.map((item) => {
        if (item.id === action.payload) {
          if (item.count === 1) {
            return item;
          }
          item.count--;
        }

        return item;
      });
    },
    /* 장바구니 물품 추가 */
    addProduct: (state: CartType[], action: PayloadAction<ProductType>) => {
      let flag = false;

      state.map((item) => {
        if (item.id === action.payload.id) {
          item.count += 1;
          flag = true;
          return item;
        }
        return item;
      });

      let newState: CartType = {
        id: action.payload.id,
        title: action.payload.title,
        price: action.payload.price,
        count: 1,
        category: action.payload.category,
        description: action.payload.description,
        image: action.payload.image,
      };

      if (!flag) {
        return [...state, newState];
      } else {
        return state;
      }
    },
    /* 장바구니 물품 삭제 */
    removeProduct: (state: CartType[], action: PayloadAction<number>) => {
      return state.filter((item) => item.id !== action.payload);
    },
    /* 장바구니 계산하기 */
    calcProduct: () => {
      return initialState;
    },
  },
});

export const {
  increaseProduct,
  decreaseProduct,
  addProduct,
  removeProduct,
  calcProduct,
} = cartSlice.actions;
export default cartSlice.reducer;
