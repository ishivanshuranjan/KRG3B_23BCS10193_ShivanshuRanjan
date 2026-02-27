// import { Link, useNavigate } from "react-router-dom";

// export default function Navbar() {
//   const navigate = useNavigate();

//   const logout = () => {
//     localStorage.removeItem("token");
//     navigate("/login");
//   };

//   return (
//     <div style={{ marginBottom: 20 }}>
//       <Link to="/dashboard">Dashboard</Link> |{" "}
//       <Link to="/dashboard/water">Water Tracker</Link> |{" "}
//       <button onClick={logout}>Logout</button>
//     </div>
//   );
// }

import { Link, useNavigate } from "react-router-dom";

export default function Navbar() {
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <div
      style={{
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        marginBottom: 20,
      }}
    >
      <h2>EcoTrack Feature Ticket — “Daily Water Tracker”</h2>

      <div>
        <Link to="/dashboard">Dashboard</Link> |{" "}
        <Link to="/dashboard/water">Water Tracker</Link> |{" "}
        <button onClick={logout}>Logout</button>
      </div>
    </div>
  );
}