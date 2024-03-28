import styled from "styled-components";

export const Container = styled.div`
  position: absolute;
  display: flex;
  align-items: center;
  justify-content: center;
  right: 0.5rem;
  height: 2rem;
  width: 6rem;
  border-radius: 5px;
  background-color: #78b2e7;
`;
export const Menuoption = styled.div`
  height: 20%;
  width: 100%;
  color: white;
  text-align: center;
  border-bottom: 0.5px solid black;
  border-radius: 5px;
  cursor: pointer;
  &:hover {
    background-color: #1d85e4;
  }
`;
