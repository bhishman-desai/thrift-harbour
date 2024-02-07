import useAuth from "../../hooks/useAuth";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { HomeParent } from "./HomeSreenStyles";
import { Auth } from "../../services/Auth";
import Modal from "../../components/ui-components/Modal/Modal";

const Home: React.FC = () => {
  const navigate = useNavigate();
  const auth = new Auth();

  const { token, handleLogout } = useAuth();
  const [authorized, setAuthorized] = useState(false);
  const handleClick = () => {
    handleLogout && handleLogout();
    navigate("/login");
  };

  useEffect(() => {
    const token = localStorage.getItem("token");

    (async () => {
      if (!token) {
        navigate("/login");
      } else {
        try {
          const [data, error] = await auth.getUser(token);
          if (data?.status === 200) {
            setAuthorized(true);
          }
          if (error) {
            setAuthorized(false);
          }
        } catch (error) {}
      }
    })();
  }, [token]);

  return (
    <>
      {authorized && (
        <HomeParent>
          <h1>Hello from user!</h1>
          <button onClick={() => handleClick()}>logout</button>
        </HomeParent>
      )}
    </>
  );
};

export default Home;
