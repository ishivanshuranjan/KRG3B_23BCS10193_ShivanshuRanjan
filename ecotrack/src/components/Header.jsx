const Header = ({ title }) => {
  return (
    <header
      style={{
        padding: "0.5rem",
        backgroundColor: "#2e7d32",
        color: "white",
      }}
    >
      <h1>{title}</h1>
    </header>
  )
}

export default Header
