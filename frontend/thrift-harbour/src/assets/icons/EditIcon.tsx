import React from "react";
import { IconComponentProps } from "./Info";

const EditIcon: React.FC<IconComponentProps> = (props) => {
  const style = {
    width: props.width || "14px",
    height: props.height || "16px",
    fill: props.color || "currentColor",
  };
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      xmlnsXlink="http://www.w3.org/1999/xlink"
      viewBox="0 0 24 24"
      width="24"
      height="24"
      fill="none"
    >
      <g opacity="100%">
        <rect
          filter="url(#filter_dshadow_0_0_0_00000014)"
          x="0"
          y="0"
          width="24"
          height="24"
          fill="#111"
          rx="2"
        ></rect>
        <svg
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 512 512"
          width="14"
          height="14"
          fill="#fff"
          x="5"
          y="5"
        >
          <path d="M421.7 220.3L188.5 453.4L154.6 419.5L158.1 416H112C103.2 416 96 408.8 96 400V353.9L92.51 357.4C87.78 362.2 84.31 368 82.42 374.4L59.44 452.6L137.6 429.6C143.1 427.7 149.8 424.2 154.6 419.5L188.5 453.4C178.1 463.8 165.2 471.5 151.1 475.6L30.77 511C22.35 513.5 13.24 511.2 7.03 504.1C.8198 498.8-1.502 489.7 .976 481.2L36.37 360.9C40.53 346.8 48.16 333.9 58.57 323.5L291.7 90.34L421.7 220.3zM492.7 58.75C517.7 83.74 517.7 124.3 492.7 149.3L444.3 197.7L314.3 67.72L362.7 19.32C387.7-5.678 428.3-5.678 453.3 19.32L492.7 58.75z"></path>
        </svg>
      </g>
      <defs>
        <filter
          id="filter_dshadow_0_0_0_00000014"
          color-interpolation-filters="sRGB"
          filterUnits="userSpaceOnUse"
        >
          <feFlood flood-opacity="0" result="bg-fix"></feFlood>
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="alpha"
          ></feColorMatrix>
          <feOffset dx="0" dy="0"></feOffset>
          <feGaussianBlur stdDeviation="0"></feGaussianBlur>
          <feComposite in2="alpha" operator="out"></feComposite>
          <feColorMatrix
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.08 0"
          ></feColorMatrix>
          <feBlend
            mode="normal"
            in2="bg-fix"
            result="bg-fix-filter_dshadow_0_0_0_00000014"
          ></feBlend>
          <feBlend
            in="SourceGraphic"
            in2="bg-fix-filter_dshadow_0_0_0_00000014"
            result="shape"
          ></feBlend>
        </filter>
      </defs>
    </svg>
  );
};

export default EditIcon;
