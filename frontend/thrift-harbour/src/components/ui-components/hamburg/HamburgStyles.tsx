import styled from "styled-components";

export const HamburgerIcon = styled.div`
  width: 30px;
  height: 3px;
  background-color: #333;
  margin: 5px 0;
`;

export const HamburgerContainer = styled.button`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  width: 40px;
  height: 40px;
  background: transparent;
  border: none;
  cursor: pointer;
`;

export const Menu = styled.div<{ isOpen: boolean }>`
  position: fixed;
  top: 0;
  left: ${({ isOpen }) =>
    isOpen ? 0 : "-300px"}; /* Adjust the width of the menu */
  bottom: 0;
  width: 300px; /* Adjust the width of the menu */
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 0 5px 5px 0; /* Adjust as needed */
  padding: 10px;
  z-index: 10;
  transition: left 0.3s ease;
`;

export const MenuItemWrapper = styled.div`
  margin-bottom: 5px;
`;

export const MenuItemButton = styled.button`
  background: none;
  border: none;
  cursor: pointer;
`;

export const CloseButton = styled.button`
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  cursor: pointer;
`;

export const Bg = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 55px; /* Adjust width as needed */
  height: 55px; /* Adjust height as needed */
  background-color: darkblue;
  border-radius: 50%;
`;
