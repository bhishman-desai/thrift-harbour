import style from "styled-components";

export const Grid = style.div`
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  padding:16px 16px 16px 16px;
`;

export const Header = style.div`
font-family: "Crimson Pro";
font-weight: 800;
font-size: 25px;
line-height: 58px;
width: 600px;
color: rgb(37, 37, 37);
`;
export const ProductCard = style.div`
  box-sizing: border-box;
  padding:12px 12px 12px 12px;
  border: 2px solid black;
  flex: 1  0 calc(25% - 10px); 
  aspect-ratio: 1;
  background-color: #fff;
  flex-grow: 0;
`;

export const ImageContainer = style.div`
  height:25%;
  width:100%;
  display:flex;
  justify-content:center;
  align-items:center;
`;

export const Image = style.div`
 display:flex;
 height:100%;
 width:25%;
`;

export const ProductName = style.div`
 display:flex;
 width:100%;
 height:10%;
 align-items:center;
`;

export const ProductDescription = style.div`
 display:flex;
 width:100%;
//  height:30%;
`;

export const ProductPrice = style.div`
 display:flex;
 width:100%;
 height:10%;
`;

export const ApproveStatus = style.div`
 display:flex;
 width:100%;
 height:10%;
`;

export const ViewButtonContainer = style.div`
 height:25%;
 width:100%;
 display:flex;
 justify-content:center;
 align-items:center;
`;

export const Button = style.div`
 color:#0C359E;
`;

export const NoListing = style.div`
  margin-top:16px;
  display:flex;
  flex-direction:row;
  justify-content:center;
  align-items:center;
`;

export const TabsContainer = style.div`
  margin-top: 10px;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-around;
  background-color: #f6f4eb;
  padding: 16px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);

  @media screen and (max-width: 768px) {
    flex-direction: column;
  }
`;

interface TabProps {
  selected: boolean;
}

export const Tab = style.div<TabProps>`
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 12px 24px;
  font-size: 18px;
  font-weight: bold;
  border-radius: 10px;
  cursor: pointer;
  transition: background-color 0.3s ease-in-out;
  background-color: ${(props) => (props.selected ? "#749bc2" : "")};

  &:hover {
    background-color: #749bc2;
    color: #fff;
  }

//   &:focus {
//     outline: none;
//     color: #fff;
//   }

  @media screen and (max-width: 768px) {
    margin-top: 12px;
    font-size: 16px;
  }
`;
