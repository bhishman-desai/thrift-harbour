import style from "styled-components";

export const Main = style.div`
display:flex;
`;

export const Header = style.div`
  padding:16px 16px 0px 16px;
  line-height: 28px;
  color: rgb(33, 33, 33);
  font-family: Inter;
  font-size: 20px;
  font-weight: 600;
`;

export const Grid = style.div`
  display: flex;
  flex-wrap: wrap;
  gap: 18px;
  padding:16px 16px 16px 16px;
`;

export const Card = style.div`
  display:flex;
  flex-direction:column;
  box-sizing: border-box;
  padding:12px 12px 12px 12px;
  width: calc(24.6% - 10px);
  height: 300px;
  aspect-ratio: 1;
  background-color: #FAFAFA;
  flex-grow: 0;
`;

export const ImageContainer = style.div`
  height:60%;
  width:100%;
  display:flex;
  justify-content:center;
  align-items:center;
  border-radius: 8px;
`;

export const Image = style.div`
  display:flex;
  height:100%;
  width:100%;
`;

export const Ratings = style.div`
  margin-top:8px;
`;

export const NamePrice = style.div`
  display:flex;
  flex-direction:column;
  margin-top:8px;
`;

export const Name = style.div`
  font-family: "Work Sans";
  font-style: normal;
  font-weight: 700;
  font-size: 24px;
  line-height: 30px;
`;

export const Price = style.div`
  font-family: "Work Sans";
  font-style: normal;
  font-weight: 400;
  font-size: 20px;
  line-height: 30px;
  color: rgb(96, 96, 96);
`;
