import { useEffect } from "react";
import useAuth from "../../hooks/useAuth";
import { Container } from "./HomeSreenStyles";
import { useNavigate } from "react-router-dom";

const Home: React.FC = () => {
  const navigate = useNavigate();

  const { handleLogout } = useAuth();

  const handleClick = () => {
    handleLogout && handleLogout();
    navigate("/login");
  };

  return (
    <Container>
      <h1>Home Screen</h1>
      <button onClick={() => handleClick()}>Logout</button>
    </Container>
  );
};

export default Home;
