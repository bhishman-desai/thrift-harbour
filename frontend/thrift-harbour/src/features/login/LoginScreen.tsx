import react from "react";
import { Container, HarbourImage } from "./LoginStyles";
import ThriftHarbour from "../../asset/ThriftHarbour.png";

const Login: React.FC = () => {
  return (
    <Container>
      <HarbourImage>
        <img height={"100%"} width={"100%"} src={ThriftHarbour} />
      </HarbourImage>
    </Container>
  );
};

export default Login;
