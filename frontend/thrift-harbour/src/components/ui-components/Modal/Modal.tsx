import React from "react";
import Close from "../../../assets/icons/Close";
import {
  BodyContainer,
  CloseButton,
  CloseButtonContainer,
  Overlay,
} from "./ModalStyles";
import "./ModalStyles.tsx";

export interface ModalProps {
  onClose: () => void;
  children?: React.ReactNode;
  images?: File[];
}
const Modal: React.FC<ModalProps> = ({ onClose, children, images }) => {
  return (
    <Overlay onClick={onClose}>
      <BodyContainer onClick={(e) => e.stopPropagation()}>
        <CloseButtonContainer>
          <CloseButton className="modal-close-btn" onClick={onClose}>
            <Close color="red" height={16} width={16} />
          </CloseButton>
        </CloseButtonContainer>
        {children}
        {images &&
          images.map((image, index) => (
            <img
              key={index}
              src={URL.createObjectURL(image)}
              alt={`Selected Image ${index + 1}`}
            />
          ))}
      </BodyContainer>
    </Overlay>
  );
};

export default Modal;
