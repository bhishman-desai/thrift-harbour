import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import ProfileIcon from "../../../assets/icons/ProfileIcon";
import { HamburgerMenuProps } from "../../../types/ListingTypes";
import Hamburg from "../hamburg/Hamburg";
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
      key: "Dashboard",
      value: "Dashboard",
      isSelected: true,
    },
    {
      key: "Send Review",
      value: "Send Review",
      isSelected: false,
    },
    {
      key: "Change settings",
      value: "Change settings",
      isSelected: false,
    },
    {
      key: "Contact us",
      value: "Contact us",
      isSelected: false,
    },
  ];

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

  const [currentSelected, setCurrentSelected] = useState("Dashboard");
  const [isProfileBgHovered, setIsProfileBgHovered] = useState(false);
  useEffect(() => {
    const handleDocumentClick = () => {
      setIsProfileBgHovered(false);
    };

    if (isProfileBgHovered) {
      document.addEventListener("click", handleDocumentClick);
    }

    return () => {
      document.removeEventListener("click", handleDocumentClick);
    };
  }, [isProfileBgHovered]);

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
          <Profile onMouseEnter={() => setIsProfileBgHovered(true)}>
            <ProfileIconBg>
              <ProfileIcon />
            </ProfileIconBg>
          </Profile>
        </TabsOptionsContainer>
      </NavContainer>
      {/* {currentSelected === "Dashboard" && <Dashboard />}
      {currentSelected === "Send Review" && <SendReview />}
      {currentSelected === "Change settings" && <ChangeSettings />}
      {currentSelected === "Contact us" && <ContactUs />} */}
      {isProfileBgHovered && <Profilepopup />}
    </>
  );
};

export default Navbar;
