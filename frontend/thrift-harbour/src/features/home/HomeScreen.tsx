import useAuth from "../../hooks/useAuth";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { HomeParent } from "./HomeSreenStyles";
import { Auth } from "../../services/Auth";
import { HamburgerMenuProps, MenuItem } from "../../types/ListingTypes";
import Navbar from "../../components/ui-components/navbar/Navbar";

const Home: React.FC = () => {
  const navigate = useNavigate();
  const auth = new Auth();

  const { token, handleLogout } = useAuth();
  const [authorized, setAuthorized] = useState(false);
  const [error, setError] = useState(false);

  const items: HamburgerMenuProps = {
    menuItems: [
      {
        id: 1,
        label: "List Product",
      },
      {
        id: 1,
        label: "List Product",
      },
      {
        id: 1,
        label: "List Product",
      },
      {
        id: 1,
        label: "List Product",
      },
    ],
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
          } else if (error) {
            setError(true);
            setAuthorized(false);
          } else {
            setError(true);
          }
        } catch (error) {
          setError(true);
        }
      }
    })();
  }, [token]);

  const toggleError = () => {
    setError(false);
    handleLogout && handleLogout();
    navigate("/login");
  };

  const handleClick = () => {
    handleLogout && handleLogout();
    navigate("/login");
  };
  return (
    <>
      {/* {authorized && (
        <HomeParent>
          <h1>Hello from user!</h1>
          <button onClick={() => handleClick()}>logout</button>
        </HomeParent>
      )}
      {error && (
        <Modal onClose={toggleError}>
          <p style={{ color: "red" }}>Something went wrong try again!</p>
        </Modal>
      )} */}
      <Navbar />

      {/* <Hamburg menuItems={items.menuItems} /> */}
    </>
  );
};

export default Home;
