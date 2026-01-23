const logsData = [
  {
    id: 1,
    action: "User Logged In",
    time: "2026-01-20 09:30 AM",
    status: "Success",
  },
  {
    id: 2,
    action: "Viewed Dashboard",
    time: "2026-01-20 09:32 AM",
    status: "Success",
  },
  {
    id: 3,
    action: "Accessed Data Page",
    time: "2026-01-20 09:35 AM",
    status: "Success",
  },
  {
    id: 4,
    action: "Logged Out",
    time: "2026-01-20 09:40 AM",
    status: "Success",
  },
];

const Logs = () => {
  return (
    <div style={{ padding: "20px" }}>
      <h2>System Logs</h2>

      <table border="1" cellPadding="10" cellSpacing="0">
        <thead>
          <tr>
            <th>ID</th>
            <th>Action</th>
            <th>Time</th>
            <th>Status</th>
          </tr>
        </thead>

        <tbody>
          {logsData.map((log) => (
            <tr key={log.id}>
              <td>{log.id}</td>
              <td>{log.action}</td>
              <td>{log.time}</td>
              <td>{log.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Logs;
