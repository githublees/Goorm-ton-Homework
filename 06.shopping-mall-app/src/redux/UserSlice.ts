import { PayloadAction, createSlice } from "@reduxjs/toolkit";

interface UserType {
  email: string;
  isActive: boolean;
}

const initialState: UserType = {
  email: "",
  isActive: false,
};

export const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    userSignIn: (state, action: PayloadAction<UserType>) => {
      return action.payload;
    },
    userSignOut: (state) => {
      return { email: "", isActive: false };
    },
    //   공부
    //   extraReducers: {},
  },
});

export const { userSignIn, userSignOut } = userSlice.actions;
export default userSlice.reducer;
