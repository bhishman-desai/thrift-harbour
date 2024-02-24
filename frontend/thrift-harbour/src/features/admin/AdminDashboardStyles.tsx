import style from "styled-components";

export const Container = style.div`
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #091c7a;
`;

export const TabsContainer = style.div`
  margin-top: 10px;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-around;
  background-color: #fff;
//   padding: 16px;
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
  color: ${(props) => (props.selected ? " #731DCF" : "")};
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease-in-out;
  background-color: #fff;
  border-bottom: ${(props) => (props.selected ? "4px solid #731DCF" : "")};
`;

export const Grid = style.div`
  display: flex;
  flex-wrap: wrap;
  gap: 18px;
  padding:16px 16px 16px 16px;
`;

export const ProductCard = style.div`
display:flex;
flex-direction:row;
  box-sizing: border-box;
  padding:12px 12px 12px 12px;
  border: 2px solid black;
  width: calc(33% - 10px);
  height: 175px;
  aspect-ratio: 1;
  background-color: #fff;
  flex-grow: 0;
`;

export const ImageContainer = style.div`
  height:30%;
  width:10%;
  display:flex;
  justify-content:center;
  align-items:center;
  background-color:#f0f0f0;
  border:1px solid red;
  border-radius: 8px;
  padding: 12px;
`;

export const Image = style.div`
 display:flex;
 height:100%;
 width:100%;

`;

export const Rest = style.div`
display:flex;
flex-direction:row;
justify-content:space-between;
width:100%;
margin-left:12px;

`;

export const ProductNameAndDescription = style.div`
 display:flex;
 flex-direction:column;
 width:75%;
`;

export const ProductName = style.div`
 display:flex;
 width:100%;
 align-items:center;
`;

export const ProductDescription = style.div`
 display:flex;
 width:100%;
 align-items:center;
 margin-top:8px;
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
//  height:25%;
//  width:100%;
//  display:flex;
//  justify-content:center;
//  align-items:center;

`;
