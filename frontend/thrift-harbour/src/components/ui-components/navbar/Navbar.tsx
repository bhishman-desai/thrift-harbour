import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import ProfileIcon from "../../../assets/icons/ProfileIcon";
import ProductListing from "../../../features/product-listing/ProductListing";
import { HamburgerMenuProps } from "../../../types/ListingTypes";
import Profilepopup from "../Profilepopup/Profilepopup";

import {
  NavContainer,
  TabsOptionsContainer,
  Option,
  Tabs,
  Profile,
  ProfileIconBg,
} from "./NavbarStyles";

const Navbar: React.FC<any> = () => {
  const NavOptions = [
    {
      key: "List Product",
      value: "List Product",
      isSelected: false,
    },
    {
      key: "Dashboard",
      value: "Dashboard",
      isSelected: true,
    },

    {
      key: "My Listed Products",
      value: "My Listed Products",
      isSelected: false,
    },
  ];

  const [currentSelected, setCurrentSelected] = useState("Dashboard");
  const [isProfileClicked, setIsProfileClicked] = useState(false);
  // useEffect(() => {
  //   const handleDocumentClick = () => {
  //     setIsProfileBgHovered(false);
  //   };

  //   if (isProfileBgHovered) {
  //     document.addEventListener("click", handleDocumentClick);
  //   }

  //   return () => {
  //     document.removeEventListener("click", handleDocumentClick);
  //   };
  // }, [isProfileBgHovered]);

  const onClickOption = (key: string) => {
    setCurrentSelected(key);
  };

  return (
    <>
      <NavContainer>
        {/* <ReviewBoosterIcon height={"125"} width={"74"} /> */}
        <TabsOptionsContainer>
          <Tabs>
            {NavOptions.map((option) => {
              return (
                <Option
                  style={{ color: "#ffffff" }}
                  currentSelectd={currentSelected === option.key}
                  onClick={() => onClickOption(option.key)}
                >
                  {option.value}
                </Option>
              );
            })}
          </Tabs>
          <Profile onClick={() => setIsProfileClicked(!isProfileClicked)}>
            <ProfileIconBg>
              <ProfileIcon />
            </ProfileIconBg>
          </Profile>
        </TabsOptionsContainer>
      </NavContainer>
      {/* {currentSelected === "Dashboard" && <Dashboard />}
      {currentSelected === "Contact us" && <ContactUs />} */}
      {currentSelected === "List Product" && <ProductListing />}
      {isProfileClicked && <Profilepopup />}
    </>
  );
};

export default Navbar;
