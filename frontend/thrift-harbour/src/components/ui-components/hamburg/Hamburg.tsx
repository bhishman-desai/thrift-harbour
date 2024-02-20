// Sidebar.tsx
import React, { useState } from "react";
import HamburgIcon from "../../../assets/icons/Hamburg";
import { HamburgerMenuProps } from "../../../types/ListingTypes";
import {
  Bg,
  CloseButton,
  HamburgerContainer,
  HamburgerIcon,
  Menu,
  MenuItemButton,
  MenuItemWrapper,
} from "./HamburgStyles";

const Hamburg: React.FC<HamburgerMenuProps> = ({ menuItems }) => {
  interface MenuItem {
    id: number;
    label: string;
  }

  interface HamburgerMenuProps {
    menuItems: MenuItem[];
  }

  const [isOpen, setIsOpen] = useState(false);

  const toggleMenu = () => {
    setIsOpen(!isOpen);
  };

  return (
    <div>
      <HamburgerContainer onClick={toggleMenu}>
        <Bg>
          <HamburgIcon height={20} width={20} color={"#fff"} />
        </Bg>
      </HamburgerContainer>
      {isOpen && (
        <Menu isOpen={isOpen}>
          <CloseButton onClick={toggleMenu}>X</CloseButton>
          {menuItems.map((item) => (
            <MenuItemWrapper key={item.id}>
              <MenuItemButton>{item.label}</MenuItemButton>
            </MenuItemWrapper>
          ))}
        </Menu>
      )}
    </div>
  );
};

export default Hamburg;
