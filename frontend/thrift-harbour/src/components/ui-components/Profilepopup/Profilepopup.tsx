import React, { useContext, useEffect, useState } from "react";
import { Container, Menuoption } from "./ProfilepopupStyles";
import { useNavigate } from "react-router-dom";

const Profilepopup: React.FC = () => {
  let navigate = useNavigate();
  const onClickLogout = async () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <>
      <Container>
        <Menuoption>Profile</Menuoption>
        <Menuoption onClick={() => onClickLogout()}>Log Out</Menuoption>
      </Container>
    </>
  );
};

export default Profilepopup;
