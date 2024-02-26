import React from "react";
import Close from "../../../assets/icons/Close";
import ImageSlider from "../image-slider/ImageSlider";
import {
  BodyContainer,
  CloseButton,
  CloseButtonContainer,
  ModalTitle,
  Overlay,
  SliderContainer,
} from "./ModalStyles";
import "./ModalStyles.tsx";

export interface ModalProps {
  onClose: () => void;
  children?: React.ReactNode;
  title?: string;
  style?: React.CSSProperties;
}
const Modal: React.FC<ModalProps> = ({ onClose, children, title, style }) => {
  return (
    <Overlay onClick={onClose}>
      <div style={style}>
        <BodyContainer onClick={(e) => e.stopPropagation()}>
          {/* <CloseButtonContainer> */}
          <CloseButton onClick={onClose}>
            <Close color="red" height={24} width={24} />
          </CloseButton>
          {/* </CloseButtonContainer> */}
          <ModalTitle>{title}</ModalTitle>
          {children}
        </BodyContainer>
      </div>
    </Overlay>
  );
};

export default Modal;
