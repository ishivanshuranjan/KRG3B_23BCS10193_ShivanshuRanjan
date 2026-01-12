import { logs } from "../data/logs"

const Dashboard = () => {
  const validLogs = logs.filter(log => log.carbon !== 0)

  const totalEmission = validLogs.reduce(
    (sum, log) => sum + log.carbon,
    0
  )

  return (
    <div className="section">
      <h2 className="section-title">Carbon Dashboard</h2>
      <hr />

      {validLogs.map(log => (
        <p key={log.id} className="item">
          {log.activity}: <strong>{log.carbon} kg CO₂</strong>
        </p>
      ))}

      <p className="total">
        Total Emission: {totalEmission} kg CO₂
      </p>
    </div>
  )
}

export default Dashboard
