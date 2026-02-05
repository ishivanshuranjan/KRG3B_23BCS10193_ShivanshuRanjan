import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchLogs, addLog, removeLog } from '../store/logsSlice';

const Logs = () => {
  const dispatch = useDispatch();

  const { data: logs, status, error } = useSelector(
    (state) => state.logs
  );

  const [activity, setActivity] = useState('');
  const [carbon, setCarbon] = useState('');

  useEffect(() => {
    if (status === 'idle') {
      dispatch(fetchLogs());
    }
  }, [status, dispatch]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!activity || !carbon) return;

    dispatch(
      addLog({
        id: Date.now(),
        activity,
        carbon: Number(carbon),
      })
    );

    setActivity('');
    setCarbon('');
  };

  return (
    <div style={{ padding: '1rem' }}>
      <h3>Daily Logs (Redux)</h3>

      {status === 'loading' && <p>Loading logs...</p>}
      {status === 'failed' && <p>Error: {error}</p>}

      {status === 'succeeded' && (
        <>
          <form onSubmit={handleSubmit}>
            <input
              placeholder="Activity"
              value={activity}
              onChange={(e) => setActivity(e.target.value)}
            />
            <input
              type="number"
              placeholder="Carbon (kg CO₂)"
              value={carbon}
              onChange={(e) => setCarbon(e.target.value)}
            />
            <button>Add Log</button>
          </form>

          <ul>
            {logs.map((log) => (
              <li key={log.id}>
                {log.activity} — {log.carbon} kg CO₂
                <button
                  onClick={() => dispatch(removeLog(log.id))}
                >
                  ❌
                </button>
              </li>
            ))}
          </ul>
        </>
      )}
    </div>
  );
};

export default Logs;
