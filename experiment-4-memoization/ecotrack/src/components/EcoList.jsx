import { List } from "@mui/material";
import EcoItem from "./EcoItem";

function EcoList({ items }) {
  return (
    <List>
      {items.map((item) => (
        <EcoItem key={item} name={item} />
      ))}
    </List>
  );
}

export default EcoList;
