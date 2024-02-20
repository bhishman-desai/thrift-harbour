import style from "styled-components";

export const Container = style.div`
  margin-top:5%;
  display: flex;
  flex-direction:column;
  background-color:#fff;
`;

export const Listing = style.div`
  display: flex;
  justify-content:center;
  width:100%;
`;

export const ProductImage = style.div`
  display: flex;
  align-items:center;
  justify-content:center;
  height:75px;
  width:75px;
  border-radius: 50%;
  margin-top:16px;
  border:1px solid black;
`;

export const IconContainer = style.div`
  display: flex;
  position:relative;
  top:65px;
  right:25px;
`;

export const FormContainer = style.form`
  margin-top:16px;
  height:100%;
  display:flex;
  flex-direction:column;
  justify-content:center;
  align-items:center;
`;

export const NamePrice = style.div`
  display:flex;
  justify-content:space-between;
  align-items:center;
  width:40%;
`;

export const Description = style.div`
  width:40%;
`;

export const Button = style.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 25%;
  margin-top: 14px;
`;

export const UploadImageModal = style.div`
  display:flex;
  flex-direction:column;
  border:1px solid red;
`;

export const ImageGrid = style.div`
border:1px solid red;
display: grid;
grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
grid-gap: 10px;
justify-content: center; 
align-items: center; 
`;

export const Img = style.img`
  width: auto;
  height: auto;
  max-width: 100%;
  max-height: 100%;
  object-fit: contain;
  border:1px solid black;
`;
