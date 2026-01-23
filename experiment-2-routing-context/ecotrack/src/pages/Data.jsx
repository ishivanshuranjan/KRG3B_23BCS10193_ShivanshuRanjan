const ecoData = [
  {
    id: 1,
    category: "Electricity Usage",
    value: "120 kWh",
    impact: "Medium",
  },
  {
    id: 2,
    category: "Water Consumption",
    value: "450 Liters",
    impact: "Low",
  },
  {
    id: 3,
    category: "Carbon Emission",
    value: "18 kg CO₂",
    impact: "High",
  },
  {
    id: 4,
    category: "Waste Generated",
    value: "6 kg",
    impact: "Medium",
  },
];

const Data = () => {
  return (
    <div style={{ padding: "20px" }}>
      <h2>Environmental Data</h2>

      <table border="1" cellPadding="10" cellSpacing="0">
        <thead>
          <tr>
            <th>ID</th>
            <th>Category</th>
            <th>Value</th>
            <th>Impact Level</th>
          </tr>
        </thead>

        <tbody>
          {ecoData.map((item) => (
            <tr key={item.id}>
              <td>{item.id}</td>
              <td>{item.category}</td>
              <td>{item.value}</td>
              <td>{item.impact}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Data;
