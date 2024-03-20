import { createSlice } from "@reduxjs/toolkit";

export interface ModalType {
  isActive: boolean;
  alarm: number;
}

const initialState: ModalType = {
  isActive: false,
  alarm: 0,
};

export const modalSlice = createSlice({
  name: "modal",
  initialState,
  reducers: {
    active: (state) => {
      let count: number;
      if (state.alarm === 9) {
        return { isActive: true, alarm: state.alarm };
      } else {
        count = state.alarm + 1;
        return { isActive: true, alarm: count };
      }
    },
    unActive: (state) => {
      return { isActive: false, alarm: state.alarm };
    },
    removeAlarm: (state) => {
      return { isActive: false, alarm: 0 };
    },
  },
});

export const { active, unActive, removeAlarm } = modalSlice.actions;
export default modalSlice.reducer;
