import React from "react";

export type IconComponentProps = {
  width?: number;
  height?: number;
  color?: string;
};
const IconComponent: React.FC<IconComponentProps> = (props) => {
  const style = {
    width: props.width || "14px",
    height: props.height || "16px",
    fill: props.color || "currentColor",
  };

  return (
    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 192 512" style={style}>
      {/* Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com
      License - https://fontawesome.com/license/free
      Copyright 2024 Fonticons, Inc. */}
      <path d="M48 80a48 48 0 1 1 96 0A48 48 0 1 1 48 80zM0 224c0-17.7 14.3-32 32-32H96c17.7 0 32 14.3 32 32V448h32c17.7 0 32 14.3 32 32s-14.3 32-32 32H32c-17.7 0-32-14.3-32-32s14.3-32 32-32H64V256H32c-17.7 0-32-14.3-32-32z" />
    </svg>
  );
};

export default IconComponent;
