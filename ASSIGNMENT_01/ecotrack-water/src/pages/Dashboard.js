import Navbar from "../components/Navbar";

export default function Dashboard() {
//   return (
//     <div style={{ padding: 20 }}>
//       <Navbar />
//       <h2>Dashboard</h2>
//     </div>
//   );
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
      <h2>Dashboard</h2>
      <p>Welcome to EcoTrack Wellness Dashboard</p>
    </div>
  </div>
);
}