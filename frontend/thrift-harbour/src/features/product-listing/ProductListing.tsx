import { Container, InputCard } from "./ProductListingStyles";
import TextField from "@mui/material/TextField";
import { Field, Form, Label, Title } from "../registration/RegistrationStyles";

const ProductListing: React.FC = () => {
  return (
    <>
      <Container>
        <InputCard>
          <Title>List your product</Title>
          <Form>
            <Field>
              <Label>First Name</Label>
              <TextField
                error
                id="standard-error-helper-text"
                label="Error"
                defaultValue="Hello World"
                helperText="Incorrect entry."
                variant="outlined"
              />
            </Field>
          </Form>
        </InputCard>
      </Container>
    </>
  );
};

export default ProductListing;
