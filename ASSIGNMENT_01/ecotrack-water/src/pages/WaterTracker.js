import { useState, useEffect, useCallback } from "react";
import Navbar from "../components/Navbar";
import CounterDisplay from "../components/CounterDisplay";

export default function WaterTracker() {
  const [count, setCount] = useState(0);
  const [goal, setGoal] = useState(8);
  const [tip, setTip] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const saved = localStorage.getItem("waterCount");
    if (saved) setCount(Number(saved));
  }, []);

  useEffect(() => {
    localStorage.setItem("waterCount", count);
  }, [count]);

  useEffect(() => {
    fetch("https://api.adviceslip.com/advice")
      .then((res) => res.json())
      .then((data) => {
        setTip(data.slip.advice);
        setLoading(false);
      })
      .catch(() => {
        setError("Failed to load tip");
        setLoading(false);
      });
  }, []);

  const addWater = useCallback(() => setCount((c) => c + 1), []);
  const removeWater = useCallback(() => setCount((c) => Math.max(0, c - 1)), []);
  const reset = () => setCount(0);

//   return (
//     <div style={{ padding: 20 }}>
return (
  <div style={{ padding: 20 }}>
    <Navbar />

    <div
      style={{
        maxWidth: 500,
        margin: "40px auto",
        padding: 20,
        border: "1px solid #ccc",
        borderRadius: 10,
        textAlign: "center",
      }}
    >
      <h2>Water Tracker</h2>

      <div>
        <label>Daily Goal: </label>
        <input
          type="number"
          value={goal}
          onChange={(e) => setGoal(Number(e.target.value))}
        />
      </div>

      <CounterDisplay count={count} goal={goal} />

      <div style={{ margin: "10px 0" }}>
        <button onClick={addWater}>+</button>
        <button onClick={removeWater}>-</button>
        <button onClick={reset}>Reset</button>
      </div>

      {count >= goal && (
        <p style={{ color: "green" }}>Goal Reached 🎉</p>
      )}

      <h3>Today's Health Tip</h3>
      {loading && <p>Loading...</p>}
      {error && <p>{error}</p>}
      {!loading && !error && <p>{tip}</p>}
    </div>
  </div>
);
}