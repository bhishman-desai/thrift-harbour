import React from "react";
import {
  BodyContainer,
  CloseButton,
  CloseButtonContainer,
  Overlay,
} from "./ModalStyles";
import "./ModalStyles.tsx";

export interface ModalProps {
  onClose: () => void;
  children: React.ReactNode;
}
const Modal: React.FC<ModalProps> = ({ onClose, children }) => {
  return (
    <Overlay onClick={onClose}>
      <BodyContainer onClick={(e) => e.stopPropagation()}>
        <CloseButtonContainer>
          <CloseButton className="modal-close-btn" onClick={onClose}>
            X
          </CloseButton>
        </CloseButtonContainer>
        {children}
      </BodyContainer>
    </Overlay>
  );
};

export default Modal;
