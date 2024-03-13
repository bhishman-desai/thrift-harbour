import useAuth from "../../hooks/useAuth";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { HomeParent } from "./HomeSreenStyles";
import { Auth } from "../../services/Auth";
import { HamburgerMenuProps, MenuItem } from "../../types/ListingTypes";
import Navbar from "../../components/ui-components/navbar/Navbar";
import { LoginType } from "../../types/AuthTypes";
import AdminDashboard from "../admin/AdminDashboard";

const Home: React.FC = () => {
  const navigate = useNavigate();
  const auth = new Auth();

  const { token, handleLogout } = useAuth();
  const [authorized, setAuthorized] = useState(false);
  const [loginType, setLogintype] = useState<string | null>();
  const [error, setError] = useState(false);

  const navOptionsUsers = [
    {
      key: "List Product",
      value: "List Product",
      isSelected: false,
    },

    {
      key: "My Listed Products",
      value: "My Listed Products",
      isSelected: false,
    },

    {
      key: "Buy Products",
      value: "Buy Products",
      isSelected: false,
    },
  ];

  const navOptionsAdmin = [
    {
      key: "Dashboard",
      value: "Dashboard",
      isSelected: true,
    },
    {
      key: "Sellers",
      value: "Sellers",
      isSelected: true,
    },
    {
      key: "List By Sellers",
      value: "List By Sellers",
      isSelected: true,
    },
  ];

  useEffect(() => {
    const token = localStorage.getItem("token");

    (async () => {
      if (!token) {
        navigate("/login");
      } else {
        try {
          const [data, error] = await auth.getUser(token);
          if (data?.status === 200) {
            console.log("in user");
            setAuthorized(true);
            setLogintype("USER");
            return;
          } else if (error) {
            setError(true);
            setAuthorized(false);
          } else {
            setError(true);
          }
        } catch (error) {
          setError(true);
        }

        try {
          const [data, error] = await auth.getAdmin(token);
          if (data?.status === 200) {
            console.log("in admin");

            setAuthorized(true);
            setLogintype("ADMIN");
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

  return (
    <>
      {authorized && loginType === "ADMIN" ? (
        <Navbar navOptions={navOptionsAdmin} loginType={loginType} />
      ) : (
        <>
          {authorized && loginType === "USER" ? (
            <Navbar navOptions={navOptionsUsers} loginType={loginType} />
          ) : (
            <></>
          )}
        </>
        // <AdminDashboard />
      )}

      {/* {authorized && loginType === "ADMIN" && <AdminDashboard />} */}
    </>
  );
};

export default Home;
