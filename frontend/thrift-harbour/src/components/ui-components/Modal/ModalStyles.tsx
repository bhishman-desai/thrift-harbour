import styled from "styled-components";

export const Overlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1;
`;

export const BodyContainer = styled.div`
  position: relative;
  background-color: #ffffff;
  padding: 24px 24px 24px 24px;
  border-radius: 4px;
`;

export const CloseButtonContainer = styled.div`
  position: relative;
  width: 100%;
  height: 20px;
`;
export const CloseButton = styled.button`
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  cursor: pointer;
`;

export const ModalTitle = styled.h2`
  font-family: "Montserrat", sans-serif;
  font-weight: 700;
  font-size: 24px;
  // margin-top: 40px;
  margin-bottom: 20px;
  color: green;
  display:flex;
  justify-content:center;
  text-align-center;
`;

export const SliderContainer = styled.div`
  position: absolute;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 80%; /* Set the width as needed */
`;
