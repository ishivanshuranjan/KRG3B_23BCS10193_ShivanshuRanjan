import { useNavigate } from "react-router-dom";

export default function Login() {
  const navigate = useNavigate();

  const handleLogin = () => {
    localStorage.setItem("token", "true");
    navigate("/dashboard");
  };

//   return (
//     <div style={{ padding: 20 }}>
//       <h2>Login Page</h2>
//       <button onClick={handleLogin}>Login</button>
//     </div>
//   );
return (
  <div
    style={{
      display: "flex",
      justifyContent: "center",
      alignItems: "center",
      height: "100vh",
    }}
  >
    <div style={{ textAlign: "center" }}>
      <h2>Login Page</h2>
      <button onClick={handleLogin}>Login</button>
    </div>
  </div>
);
}

