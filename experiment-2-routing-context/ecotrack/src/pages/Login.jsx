import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

const Login = () => {
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleLogin = () => {
    login();
    navigate("/");
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Login</h2>
      <button onClick={handleLogin}>Login to EcoTrack</button>
    </div>
  );
};

export default Login;
