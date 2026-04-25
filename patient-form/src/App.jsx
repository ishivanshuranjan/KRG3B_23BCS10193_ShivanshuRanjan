import { useState } from "react";

function App() {
  const [patientName, setPatientName] = useState("");
  const [email, setEmail] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();

    if (patientName === "" || email === "") {
      alert("Both fields are required!");
      return;
    }

    console.log("Patient Name:", patientName);
    console.log("Email:", email);

    setPatientName("");
    setEmail("");
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Patient Form</h2>

      <form onSubmit={handleSubmit}>
        <div>
          <label>Name:</label><br />
          <input
            type="text"
            value={patientName}
            onChange={(e) => setPatientName(e.target.value)}
          />
        </div>

        <div>
          <label>Email:</label><br />
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>

        <br />
        <button type="submit">Submit</button>
      </form>
    </div>
  );
}

export default App;