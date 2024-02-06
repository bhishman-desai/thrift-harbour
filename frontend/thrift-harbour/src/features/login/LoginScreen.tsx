import react from "react";
import { Container, HarbourImage, LoginSignup } from "./LoginStyles";
import ThriftHarbour from "../../asset/ThriftHarbour.png";

const Login: React.FC = () => {
  return (
    <Container>
      <LoginSignup>
        <p>
          <strong>Welcome to Thrift Harbour!</strong>
        </p>
      </LoginSignup>
      <HarbourImage>
        {/* <img height={"100%"} width={"100%"} src={ThriftHarbour} /> */}
      </HarbourImage>
    </Container>
  );
};

export default Login;
