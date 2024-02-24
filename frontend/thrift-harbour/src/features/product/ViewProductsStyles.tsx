import style from "styled-components";

export const Container = style.div`
  margin:top:12px;
  height: 150px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #091c7a;
`;

export const ImageSLiderConainer = style.div`
position: absolute; /* Position the slider absolutely */
top: 50%; /* Place it at the vertical center */
left: 50%; /* Place it at the horizontal center */
transform: translate(-50%, -50%); /* Center the slider */
width: 80%;
`;
