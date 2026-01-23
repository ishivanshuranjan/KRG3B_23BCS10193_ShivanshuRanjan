import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const Header = () => {
  const { isAuthenticated, logout } = useAuth();

  return (
    <header style={{ background: "#6BCF8E", padding: "15px" }}>
      <h2>EcoTrack</h2>

      <nav style={{ display: "flex", gap: "15px" }}>
        <Link to="/">Dashboard</Link>

        {isAuthenticated && (
          <>
            <Link to="/logs">Logs</Link>
            <Link to="/data">Data</Link>
          </>
        )}

        {!isAuthenticated && <Link to="/login">Login</Link>}
        {isAuthenticated && <button onClick={logout}>Logout</button>}
      </nav>
    </header>
  );
};

export default Header;
