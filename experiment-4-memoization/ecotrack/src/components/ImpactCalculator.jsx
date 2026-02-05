import { Typography } from "@mui/material";
import { useMemo } from "react";

function ImpactCalculator({ value }) {
  const result = useMemo(() => {
    console.log("Calculating impact...");
    let total = 0;
    for (let i = 0; i < 1000000; i++) {
      total += value;
    }
    return total;
  }, [value]);

  return (
    <Typography>
      Environmental Impact: {result}
    </Typography>
  );
}

export default ImpactCalculator;
