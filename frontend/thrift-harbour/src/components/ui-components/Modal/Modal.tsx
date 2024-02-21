import React from "react";
import Close from "../../../assets/icons/Close";
import {
  BodyContainer,
  CloseButton,
  CloseButtonContainer,
  ModalTitle,
  Overlay,
} from "./ModalStyles";
import "./ModalStyles.tsx";

export interface ModalProps {
  onClose: () => void;
  children?: React.ReactNode;
  title?: string;
}
const Modal: React.FC<ModalProps> = ({ onClose, children, title }) => {
  return (
    <Overlay onClick={onClose}>
      <BodyContainer onClick={(e) => e.stopPropagation()}>
        {/* <CloseButtonContainer> */}
        <CloseButton onClick={onClose}>
          <Close color="red" height={24} width={24} />
        </CloseButton>
        {/* </CloseButtonContainer> */}
        <ModalTitle>{title}</ModalTitle>
        {children}
      </BodyContainer>
    </Overlay>
  );
};

export default Modal;
