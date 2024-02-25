import style from "styled-components";

export const Container = style.div`
  margin:top:12px;
  height: 150px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #091c7a;
`;

export const ProductNameAndDescription = style.div`
 width:50%;
`;

export const ImageSLiderConainer = style.div`
position: absolute; /* Position the slider absolutely */
top: 50%; /* Place it at the vertical center */
left: 50%; /* Place it at the horizontal center */
transform: translate(-50%, -50%); /* Center the slider */
width: 80%;
`;

export const ProductShowcase = style.div`
 display:flex;
 flex-direction:row;
 margin-top:24px;
`;

export const UserInfo = style.div`
  width:50%;
`;

export const ChangeStatus = style.form`
  margin-top:8px;
  width:100%;
  display: flex;
  flex-direction:column;
  justify-content: center; 
  align-items: center
`;

export const DropDown = style.form`
  margin-top:8px;
  width:100%;
  display: flex;
  justify-content: center; 
  align-items: center
`;
