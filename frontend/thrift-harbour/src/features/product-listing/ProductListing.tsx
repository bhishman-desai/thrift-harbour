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
  UploadButtonContainer,
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
import { FormEvent, useRef, useState } from "react";
import MenuItem from "@mui/material/MenuItem";
import InputLabel from "@mui/material/InputLabel";
import { ClipLoader } from "react-spinners";
import Modal from "../../components/ui-components/Modal/Modal";
import ImageList from "@mui/material/ImageList";
import ImageListItem from "@mui/material/ImageListItem";
import { ListingDataTypes, TouchedFieldsType } from "../../types/ListingTypes";
import { ListingService } from "../../services/Listing";

const ProductListing: React.FC = () => {
  const listingService = new ListingService();
  const [isLoading, setIsLoading] = useState(false);
  const [selectedFiles, setSelectedFiles] = useState<File[]>([]);
  const [imagePreview, setImagePreview] = useState<string | null>(null);
  const [showModal, setShowModal] = useState(false);
  const [touchedFields, setTouchedFields] = useState({} as TouchedFieldsType);
  const [thumbnailUrl, setThumbnailUrl] = useState<string | null>(null);

  const token = localStorage.getItem("token");
  const [listingData, setListingData] = useState({
    productName: "",
    productPrice: 0,
    productDescription: "",
    sellCategory: "",
    productImages: [],
    productCategory: "",
    auctionSlot: "",
  } as ListingDataTypes);

  const handleFieldBlur = (field: string) => {
    setTouchedFields({
      ...touchedFields,
      [field]: true,
    });
  };

  const fileInputRef = useRef<HTMLInputElement>(null);

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && event.target.files.length > 0) {
      const filesArray = Array.from(event.target.files).slice(0, 5); // Limit to max 5 files
      setSelectedFiles(filesArray);
      console.log("length", filesArray.length);
      const reader = new FileReader();
      reader.onload = () => {
        if (reader.result) {
          setThumbnailUrl(reader.result as string);
        }
      };
      reader.readAsDataURL(event.target.files[0]);
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
      setListingData({ ...listingData, productImages: selectedFiles });
      console.log("Selected files:", selectedFiles);
    }
  };

  const closeModal = () => {
    setShowModal(false);
    setSelectedFiles([]);
  };

  const handleFormSubmit = async (e: FormEvent) => {
    e.preventDefault();
    console.log("formData =>", listingData);

    try {
      const [data, error] = await listingService.immediateListing(
        listingData,
        token
      );
      console.log("data", data);
      if (data?.userID) {
      }
    } catch (error) {
      setIsLoading(false);
    }
  };
  return (
    <>
      <Container>
        <Listing onClick={() => handleIconClick}>
          <label htmlFor="file-input">
            {thumbnailUrl ? (
              <ProductImage>
                <img
                  style={{ borderRadius: "50%" }}
                  height={"100%"}
                  width={"100%"}
                  src={thumbnailUrl}
                  alt="Thumbnail"
                />
              </ProductImage>
            ) : (
              <ProductImage>
                <ImageIcon height={24} width={24} />
              </ProductImage>
            )}
            {/* <ProductImage>
              <ImageIcon height={24} width={24} />
            </ProductImage> */}
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
          {/* {imagePreview && (
            <img
              src={imagePreview}
              alt="Selected"
              style={{ maxWidth: "100%", maxHeight: "200px" }}
            />
          )} */}
        </Listing>
        <FormContainer onSubmit={handleFormSubmit}>
          <NamePrice>
            <Field style={{ width: "48%" }}>
              <TextField
                error={
                  listingData.productName.length === 0 &&
                  touchedFields.productName
                }
                id="standard-error-helper-text"
                label="Product Name"
                value={listingData.productName}
                helperText={
                  listingData.productName.length === 0 &&
                  touchedFields.productName &&
                  "It should not be empty"
                }
                variant="outlined"
                onBlur={() => handleFieldBlur("productName")}
                onChange={(e) => {
                  setListingData({
                    ...listingData,
                    productName: e.target.value,
                  });
                }}
              />
            </Field>

            <Field style={{ width: "48%" }}>
              <TextField
                type="number"
                error={!listingData.productPrice && touchedFields.productPrice}
                id="standard-error-helper-text"
                label="Product Price"
                value={listingData.productPrice}
                helperText={
                  !listingData.productPrice &&
                  touchedFields.productPrice &&
                  "Required field"
                }
                variant="outlined"
                onChange={(e) => {
                  setListingData({
                    ...listingData,
                    productPrice: parseInt(e.target.value),
                  });
                }}
                onBlur={() => handleFieldBlur("productPrice")}
              />
            </Field>
          </NamePrice>

          <NamePrice>
            <Field style={{ width: "48%" }}>
              <TextField
                error={
                  listingData.productDescription.length === 0 &&
                  touchedFields.productDescription
                }
                id="outlined-multiline-static"
                label="Product Description"
                multiline
                helperText={
                  listingData.productDescription.length === 0 &&
                  touchedFields.productDescription &&
                  "Required Field"
                }
                value={listingData.productDescription}
                onChange={(e) => {
                  setListingData({
                    ...listingData,
                    productDescription: e.target.value,
                  });
                }}
                onBlur={() => handleFieldBlur("productDescription")}
              />
            </Field>

            <Field style={{ width: "48%" }}>
              <InputLabel>Product category</InputLabel>
              <Select
                labelId="demo-simple-select-disabled-label"
                id="demo-simple-select-disabled"
                value={listingData.productCategory}
                label="Product category"
                onChange={(e) => {
                  setListingData({
                    ...listingData,
                    productCategory: e.target.value,
                  });
                }}
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
              <InputLabel>Sell category</InputLabel>
              <Select
                labelId="demo-simple-select-disabled-label"
                id="demo-simple-select-disabled"
                value={listingData.sellCategory}
                label="Sell category"
                onChange={(e) => {
                  setListingData({
                    ...listingData,
                    sellCategory: e.target.value,
                  });
                }}
              >
                <MenuItem value={"AUCTION"}>Auction</MenuItem>
                <MenuItem value={"DIRECT"}>Direct</MenuItem>
              </Select>
            </Field>
            <Field style={{ width: "48%" }}>
              <InputLabel>Auction Slot</InputLabel>
              <Select
                disabled={listingData.sellCategory === "DIRECT"}
                labelId="demo-simple-select-disabled-label"
                id="demo-simple-select-disabled"
                value={listingData.auctionSlot}
                label="Auction Slot"
                onChange={(e) => {
                  setListingData({
                    ...listingData,
                    auctionSlot: e.target.value,
                  });
                }}
              >
                <MenuItem value={"Thursday"}>Thursday</MenuItem>
                <MenuItem value={"Next Thursday"}>Next Thursday</MenuItem>
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
        <Modal onClose={closeModal} title={"Selected Images"}>
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
                    loading="lazy"
                  />
                </ImageListItem>
              ))}
          </ImageList>

          <UploadButtonContainer>
            <RegisterButton
              onClick={() => handleUpload()}
              style={{ width: "50%" }}
              type="submit"
            >
              Upload
            </RegisterButton>
          </UploadButtonContainer>
        </Modal>
      )}
    </>
  );
};

export default ProductListing;
