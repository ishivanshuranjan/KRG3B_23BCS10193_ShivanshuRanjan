import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';

/**
 * ASYNC THUNK
 */
export const fetchLogs = createAsyncThunk(
  'logs/fetchLogs',
  async () => {
    // simulate API call
    await new Promise((resolve) => setTimeout(resolve, 500));

    return [
      { id: 1, activity: 'Car Travel', carbon: 4 },
      { id: 2, activity: 'Electricity Usage', carbon: 6 },
      { id: 3, activity: 'Cycling', carbon: 0 },
    ];
  }
);

const initialState = {
  data: [],
  status: 'idle', // idle | loading | succeeded | failed
  error: null,
};

const logsSlice = createSlice({
  name: 'logs',
  initialState,
  reducers: {
    addLog(state, action) {
      state.data.push(action.payload);
    },
    removeLog(state, action) {
      state.data = state.data.filter(
        (log) => log.id !== action.payload
      );
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchLogs.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchLogs.fulfilled, (state, action) => {
        state.status = 'succeeded';
        state.data = action.payload;
      })
      .addCase(fetchLogs.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error.message;
      });
  },
});

export const { addLog, removeLog } = logsSlice.actions;
export default logsSlice.reducer;
