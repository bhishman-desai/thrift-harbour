import useAuth from "../../hooks/useAuth";
import { useNavigate } from "react-router-dom";

const Home: React.FC = () => {
  const navigate = useNavigate();

  const { handleLogout } = useAuth();

  const handleClick = () => {
    handleLogout && handleLogout();
    navigate("/login");
  };

  return <button onClick={() => handleClick()}>Logout</button>;
};

export default Home;
