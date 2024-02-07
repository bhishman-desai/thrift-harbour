import useAuth from "../../hooks/useAuth";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

const Home: React.FC = () => {
  const navigate = useNavigate();

  const { token, handleLogout } = useAuth();
  const handleClick = () => {
    handleLogout && handleLogout();
    navigate("/login");
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      navigate("/login");
    }
  }, [token]);

  return (
    <>
      <button onClick={() => handleClick()}>logout</button>
    </>
  );
};

export default Home;
