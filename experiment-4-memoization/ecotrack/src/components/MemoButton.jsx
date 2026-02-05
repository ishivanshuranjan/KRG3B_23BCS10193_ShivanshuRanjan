import React from "react";
import { Button } from "@mui/material";

const MemoButton = React.memo(({ text, onClick }) => {
  console.log("Button rendered");
  return (
    <Button variant="contained" onClick={onClick}>
      {text}
    </Button>
  );
});

export default MemoButton;
