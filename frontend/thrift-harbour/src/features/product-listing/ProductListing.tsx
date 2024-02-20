import {
  Button,
  Container,
  Description,
  FormContainer,
  IconContainer,
  ImageGrid,
  Img,
  Listing,
  NamePrice,
  ProductImage,
  UploadImageModal,
} from "./ProductListingStyles";
import ImageIcon from "../../assets/icons/ImageIcon";
import EditIcon from "../../assets/icons/EditIcon";
import {
  Field,
  Form,
  Label,
  RegisterButton,
} from "../registration/RegistrationStyles";
import TextField from "@mui/material/TextField";
import Select, { SelectChangeEvent } from "@mui/material/Select";
import { useRef, useState } from "react";
import MenuItem from "@mui/material/MenuItem";
import InputLabel from "@mui/material/InputLabel";
import { ClipLoader } from "react-spinners";
import Modal from "../../components/ui-components/Modal/Modal";
import ImageList from "@mui/material/ImageList";
import ImageListItem from "@mui/material/ImageListItem";

const ProductListing: React.FC = () => {
  const uploadImage = () => {
    console.log("upload image");
  };

  const [sellCategory, setSellCategory] = useState("");
  const [productCategory, setProductCategory] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [auctionSlot, setAuctionSlot] = useState("");
  const [selectedFiles, setSelectedFiles] = useState<File[]>([]);
  const [imagePreview, setImagePreview] = useState<string | null>(null);
  const [showModal, setShowModal] = useState(false);

  const fileInputRef = useRef<HTMLInputElement>(null);

  const handleSellCategory = (event: SelectChangeEvent) => {
    setSellCategory(event.target.value);
  };

  const handleProductCategory = (event: SelectChangeEvent) => {
    setProductCategory(event.target.value);
  };

  const handleAuctionSlot = (event: SelectChangeEvent) => {
    setAuctionSlot(event.target.value);
  };

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && event.target.files.length > 0) {
      const filesArray = Array.from(event.target.files).slice(0, 5); // Limit to max 5 files
      setSelectedFiles(filesArray);
      console.log("length", filesArray.length);
      setShowModal(true);
    }
  };

  const handleIconClick = (
    event: React.MouseEvent<SVGSVGElement, MouseEvent>
  ) => {
    event.preventDefault();
    if (fileInputRef.current) {
      fileInputRef.current.click();
    }
  };

  const handleUpload = () => {
    if (selectedFiles.length > 0) {
      // Here you can handle the file upload, e.g., send them to a server
      console.log("Selected files:", selectedFiles);
    }
  };

  const closeModal = () => {
    setShowModal(false);
    setSelectedFiles([]);
  };

  return (
    <>
      <Container>
        <Listing onClick={() => handleIconClick}>
          <label htmlFor="file-input">
            <ProductImage>
              <ImageIcon height={24} width={24} />
            </ProductImage>
            {/* <IconContainer>
              <EditIcon />
            </IconContainer> */}
          </label>
          <input
            multiple
            ref={fileInputRef}
            id="file-input"
            type="file"
            accept="image/*" // Specify accepted file types (in this case, images)
            onChange={handleFileChange}
            style={{ display: "none" }}
          />
          {imagePreview && (
            <img
              src={imagePreview}
              alt="Selected"
              style={{ maxWidth: "100%", maxHeight: "200px" }}
            />
          )}
        </Listing>
        <FormContainer>
          <NamePrice>
            <Field style={{ width: "48%" }}>
              <TextField
                // error
                // id="standard-error-helper-text"
                label="Product Name"
                defaultValue=""
                // helperText="Incorrect entry."
                variant="outlined"
              />
            </Field>

            <Field style={{ width: "48%" }}>
              <TextField
                // error
                // id="standard-error-helper-text"
                label="Product Price"
                defaultValue=""
                // helperText="Incorrect entry."
                variant="outlined"
              />
            </Field>
          </NamePrice>

          <NamePrice>
            <Field style={{ width: "48%" }}>
              <TextField
                // id="outlined-multiline-static"
                label="Product Description"
                multiline
                defaultValue=""
              />
            </Field>

            <Field style={{ width: "48%" }}>
              <InputLabel>Product category</InputLabel>
              <Select
                labelId="demo-simple-select-disabled-label"
                // id="demo-simple-select-disabled"
                value={productCategory}
                label="Product category"
                onChange={handleProductCategory}
              >
                <MenuItem value={"Furniture"}>Furniture</MenuItem>
                <MenuItem value={"Electronics"}>Electronics</MenuItem>
                <MenuItem value={"Lifestyle "}>Lifestyle </MenuItem>
                <MenuItem value={"Artworks"}>Artworks</MenuItem>
              </Select>
            </Field>
          </NamePrice>

          <NamePrice>
            <Field style={{ width: "48%" }}>
              <InputLabel>Auction Slot</InputLabel>
              <Select
                labelId="demo-simple-select-disabled-label"
                // id="demo-simple-select-disabled"
                value={auctionSlot}
                label="Auction Slot"
                onChange={handleAuctionSlot}
              >
                <MenuItem value={"Thursday"}>Thursday</MenuItem>
                <MenuItem value={"Next Thursday"}>Next Thursday</MenuItem>
              </Select>
            </Field>

            <Field style={{ width: "48%" }}>
              <InputLabel>Sell category</InputLabel>
              <Select
                labelId="demo-simple-select-disabled-label"
                // id="demo-simple-select-disabled"
                value={sellCategory}
                label="Sell category"
                onChange={handleSellCategory}
              >
                <MenuItem value={"Auction"}>Auction</MenuItem>
                <MenuItem value={"Direct"}>Direct</MenuItem>
              </Select>
            </Field>
          </NamePrice>
          <Button>
            <RegisterButton type="submit" style={{ marginTop: "8px" }}>
              {isLoading ? (
                <ClipLoader color="#ffffff" loading={isLoading} size={20} />
              ) : (
                "Submit"
              )}
            </RegisterButton>
          </Button>
        </FormContainer>
      </Container>

      {showModal && selectedFiles.length > 0 && (
        <Modal onClose={closeModal}>
          {/* <UploadImageModal> */}
          {/* <ImageGrid>
            {selectedFiles &&
              selectedFiles.map((selectedFiles, index) => (
                <Img
                  key={index}
                  src={URL.createObjectURL(selectedFiles)}
                  alt={`Selected Image ${index + 1}`}
                />
              ))}
          </ImageGrid> */}

          <ImageList sx={{ width: 500, height: 450 }} cols={3} rowHeight={164}>
            {selectedFiles &&
              selectedFiles.map((image, index) => (
                <ImageListItem key={index}>
                  <img
                    style={{
                      height: "164px",
                      width: "164px",
                    }}
                    srcSet={
                      `${image}` + `?w=164&h=164&fit=crop&auto=format&dpr=2 2x`
                    }
                    src={`${URL.createObjectURL(image)}`}
                    // alt={`Selected Image ${index + 1}`}
                    loading="lazy"
                  />
                </ImageListItem>
              ))}
          </ImageList>

          {/* <RegisterButton type="submit" style={{ marginTop: "8px" }}>
              "Upload"
            </RegisterButton> */}
          {/* </UploadImageModal> */}
        </Modal>
      )}
    </>
  );
};

export default ProductListing;
