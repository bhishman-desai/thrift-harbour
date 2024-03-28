import React, { useContext, useEffect, useState } from "react";
import { Container, Menuoption } from "./ProfilepopupStyles";
import { useNavigate } from "react-router-dom";

const Profilepopup: React.FC = () => {
  let navigate = useNavigate();
  const [token, setToken] = useState(localStorage.getItem("token"));

  useEffect(() => {
    if (token?.length === 0) {
      navigate("/");
    }
  }, [token]);

  const onClickLogout = async () => {
    localStorage.removeItem("token");
    localStorage.removeItem("uId");
    setToken("");
  };

  return (
    <>
      <Container onClick={() => onClickLogout()}>Log Out</Container>
    </>
  );
};

export default Profilepopup;
