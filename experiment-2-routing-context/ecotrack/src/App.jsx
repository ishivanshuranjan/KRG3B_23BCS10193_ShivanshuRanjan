import { Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Logs from "./pages/Logs";
import Data from "./pages/Data";
import ProtectedRoute from "./routes/ProtectedRoute";

function App() {
  return (
    <>
      <Header />

      <Routes>
        {/* Public Route */}
        <Route path="/login" element={<Login />} />

        {/* Protected Routes */}
        <Route
          path="/"
          element={
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/logs"
          element={
            <ProtectedRoute>
              <Logs />
            </ProtectedRoute>
          }
        />

        <Route
          path="/data"
          element={
            <ProtectedRoute>
              <Data />
            </ProtectedRoute>
          }
        />
      </Routes>
    </>
  );
}

export default App;
