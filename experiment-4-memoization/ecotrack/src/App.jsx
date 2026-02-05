import { Container, Typography, Divider } from "@mui/material";
import { useState, useCallback } from "react";

import MemoButton from "./components/MemoButton";
import ImpactCalculator from "./components/ImpactCalculator";
import EcoList from "./components/EcoList";

function App() {
  // simple state
  const [count, setCount] = useState(0);
  const [impact, setImpact] = useState(2);

  // memoized function (IMPORTANT)
  const increaseCount = useCallback(() => {
    setCount((prev) => prev + 1);
  }, []);

  // normal data (list)
  const ecoItems = ["Tree", "Water", "Energy"];

  return (
    <Container sx={{ mt: 4 }}>
      {/* App Title */}
      <Typography variant="h4" gutterBottom>
        EcoTrack App
      </Typography>

      {/* Counter Section */}
      <Typography variant="h6">
        Count: {count}
      </Typography>

      <MemoButton
        text="Increase Count"
        onClick={increaseCount}
      />

      <Divider sx={{ my: 3 }} />

      {/* Impact Section */}
      <Typography variant="h6">
        Environmental Impact Value
      </Typography>

      <MemoButton
        text="Increase Impact"
        onClick={() => setImpact((prev) => prev + 1)}
      />

      <ImpactCalculator value={impact} />

      <Divider sx={{ my: 3 }} />

      {/* List Section */}
      <Typography variant="h6">
        Eco Items
      </Typography>

      <EcoList items={ecoItems} />
    </Container>
  );
}

export default App;
