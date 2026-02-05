import React from "react";
import { ListItem, ListItemText } from "@mui/material";

const EcoItem = React.memo(({ name }) => {
  console.log("Item rendered:", name);
  return (
    <ListItem>
      <ListItemText primary={name} />
    </ListItem>
  );
});

export default EcoItem;
