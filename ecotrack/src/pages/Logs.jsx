import { logs } from "../data/logs"

const Logs = () => {
  const validLogs = logs.filter(log => log.carbon !== 0)

  const highCarbon = validLogs.filter(log => log.carbon > 4)
  const lowCarbon = validLogs.filter(log => log.carbon < 4)

  return (
    <div className="section">

      <h3 className="section-subtitle">
        High Carbon Activities (&gt; 4 kg CO₂)
      </h3>
      <hr />

      {highCarbon.map(log => (
        <p key={log.id} className="item high-carbon">
          {log.activity}: {log.carbon} kg CO₂
        </p>
      ))}

      <h3 className="section-subtitle">
        Low Carbon Activities (&lt; 4 kg CO₂)
      </h3>
      <hr />

      {lowCarbon.map(log => (
        <p key={log.id} className="item low-carbon">
          {log.activity}: {log.carbon} kg CO₂
        </p>
      ))}
    </div>
  )
}

export default Logs
