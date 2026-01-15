import Dashboard from "./pages/Dashboard"
import Logs from "./pages/Logs"
import "./App.css"

function App() {
  return (
    <div className="app">
      <h1 className="app-title">ECOTRACK (Experiment – 1)</h1>

      <div className="card">
        <Dashboard />
        <Logs />
      </div>
    </div>
  )
}

export default App
