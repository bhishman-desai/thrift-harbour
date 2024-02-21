import {
  Button,
  Container,
  Description,
  FormContainer,
  IconContainer,
  ImageError,
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
import FormHelperText from "@mui/material/FormHelperText";
import FormControl from "@mui/material/FormControl";
import { Dates } from "../../utils/Dates";
import ErrorModal from "../../components/ui-components/SuccessErrorModal/SuccessErrorModal";
import SuccessErrorModal from "../../components/ui-components/SuccessErrorModal/SuccessErrorModal";

const ProductListing: React.FC = () => {
  const listingService = new ListingService();
  const dates = new Dates();
  const nextThursday = dates.getNextThursday();
  const nextToNextThursday = dates.getNextNextThursday();

  const [isLoading, setIsLoading] = useState(false);
  const [selectedFiles, setSelectedFiles] = useState<File[]>([]);
  const [imagePreview, setImagePreview] = useState<string | null>(null);
  const [showModal, setShowModal] = useState(false);
  const [touchedFields, setTouchedFields] = useState({} as TouchedFieldsType);
  const [thumbnailUrl, setThumbnailUrl] = useState<string | null>(null);
  const [imageError, setImageError] = useState(false);
  const [openModal, setOpenModal] = useState(false);
  const [error, setError] = useState(false);

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
      setShowModal(false);
      console.log("Selected files:", selectedFiles);
    }
  };

  const closeModal = () => {
    setSelectedFiles([]);
    setThumbnailUrl(null);
    setShowModal(false);
  };

  const emptyListingData = () => {
    setThumbnailUrl(null);
    setListingData({
      productName: "",
      productPrice: 0,
      productDescription: "",
      sellCategory: "",
      productImages: [],
      productCategory: "",
      auctionSlot: "",
    });
  };

  const setTouchedFalse = () => {
    setTouchedFields({
      productName: false,
      productPrice: false,
      productDescription: false,
      sellCategory: false,
      productCategory: false,
      auctionSlot: false,
    });
  };

  const handleFormSubmit = async (e: FormEvent) => {
    e.preventDefault();
    if (listingData.productImages.length === 0) {
      setImageError(true);
      return;
    }
    console.log("formData =>", listingData);
    setIsLoading(true);

    if (listingData.sellCategory === "DIRECT") {
      try {
        const [data, error] = await listingService.immediateListing(
          listingData,
          token
        );
        console.log("data", data);
        if (data?.immediateSaleListingID) {
          setIsLoading(false);
          emptyListingData();
          setTouchedFalse();
          setError(false);
          setOpenModal(true);
        } else {
          setIsLoading(false);
          emptyListingData();
          setTouchedFalse();
          setError(true);
          setOpenModal(true);
        }
      } catch (error) {
        setIsLoading(false);
        emptyListingData();
        setTouchedFalse();
        setError(true);
        setOpenModal(true);
      }
    }

    if (listingData.sellCategory === "AUCTION") {
      try {
        const [data, error] = await listingService.auctionListing(
          listingData,
          token
        );
        console.log("data", data);
        if (data?.auctionSaleListingID) {
          setIsLoading(false);
          emptyListingData();
          setTouchedFalse();
          setError(false);
          setOpenModal(true);
        } else {
          setIsLoading(false);
          emptyListingData();
          setTouchedFalse();
          setError(true);
          setOpenModal(true);
        }
      } catch (error) {
        setIsLoading(false);
        emptyListingData();
        setTouchedFalse();
        setError(true);
        setOpenModal(true);
      }
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
                <ImageIcon height={32} width={32} />
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
            required={true}
            multiple
            ref={fileInputRef}
            id="file-input"
            name="productImages"
            type="file"
            accept="image/png"
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
            {/* <Field style={{ width: "48%" }}> */}
            <FormControl sx={{ m: 1, width: "48%" }}>
              <TextField
                required={true}
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
            </FormControl>
            {/* </Field> */}

            {/* <Field style={{ width: "48%" }}> */}
            <FormControl sx={{ m: 1, width: "48%" }}>
              <TextField
                required={true}
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
            </FormControl>
            {/* </Field> */}
          </NamePrice>

          <NamePrice>
            {/* <Field style={{ width: "48%" }}>
             */}
            <FormControl sx={{ m: 1, width: "48%" }}>
              <TextField
                required={true}
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
            </FormControl>
            {/* </Field> */}

            {/* <Field style={{ width: "48%" }}> */}
            <FormControl
              sx={{ m: 1, width: "48%" }}
              error={
                listingData.productCategory.length === 0 &&
                touchedFields.productCategory
              }
            >
              <InputLabel>Product category</InputLabel>
              <Select
                required={true}
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
                onBlur={() => handleFieldBlur("productCategory")}
              >
                <MenuItem value={"Furniture"}>Furniture</MenuItem>
                <MenuItem value={"Electronics"}>Electronics</MenuItem>
                <MenuItem value={"Lifestyle "}>Lifestyle </MenuItem>
                <MenuItem value={"Artworks"}>Artworks</MenuItem>
              </Select>
              <FormHelperText>
                {listingData.productCategory.length === 0 &&
                  touchedFields.productCategory &&
                  "Required Field"}
              </FormHelperText>
            </FormControl>
            {/* </Field> */}
          </NamePrice>

          <NamePrice>
            {/* <Field style={{ width: "48%" }}> */}
            <FormControl
              sx={{ m: 1, width: "48%" }}
              error={
                listingData.sellCategory.length === 0 &&
                touchedFields.sellCategory
              }
            >
              <InputLabel>Sell category</InputLabel>
              <Select
                required={true}
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
                onBlur={() => handleFieldBlur("sellCategory")}
              >
                <MenuItem value={"AUCTION"}>Auction</MenuItem>
                <MenuItem value={"DIRECT"}>Direct</MenuItem>
              </Select>
              <FormHelperText>
                {listingData.sellCategory.length === 0 &&
                  touchedFields.sellCategory &&
                  "Required Field"}
              </FormHelperText>
            </FormControl>
            {/* </Field> */}

            {/* <Field style={{ width: "48%" }}> */}
            <FormControl
              sx={{ m: 1, width: "48%" }}
              error={
                listingData.sellCategory === "AUCTION" &&
                listingData.auctionSlot?.length === 0 &&
                touchedFields.auctionSlot
              }
            >
              <InputLabel>Auction Slot</InputLabel>
              <Select
                required={true}
                disabled={
                  listingData.sellCategory === "DIRECT" ||
                  listingData.sellCategory === ""
                }
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
                onBlur={() => handleFieldBlur("auctionSlot")}
              >
                <MenuItem value={nextThursday}>{nextThursday}</MenuItem>
                <MenuItem value={nextToNextThursday}>
                  {nextToNextThursday}
                </MenuItem>
              </Select>
              <FormHelperText>
                {listingData.sellCategory !== "DIRECT" &&
                  listingData.auctionSlot?.length === 0 &&
                  touchedFields.auctionSlot &&
                  "Required Field"}
              </FormHelperText>
            </FormControl>
            {/* </Field> */}
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

      <SuccessErrorModal
        type={error ? "ERROR" : "SUCCESS"}
        message={
          error
            ? "Something went wrong please try again!"
            : "Product listed successfully!"
        }
        open={openModal}
        setOpen={setOpenModal}
        title={error ? "error" : "Success"}
      />

      <SuccessErrorModal
        type="ERROR"
        message={"Please upload atleast one image!"}
        open={imageError}
        setOpen={setImageError}
        title={"error"}
      />
    </>
  );
};

export default ProductListing;
