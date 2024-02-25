export interface MenuItem {
  id: number;
  label: string;
}

export interface HamburgerMenuProps {
  menuItems: MenuItem[];
}

export interface ListingDataTypes {
  productName: string;
  productPrice: number;
  productDescription: string;
  sellCategory: string;
  productImages: File[];
  productCategory: string;
  auctionSlot?: string;
}

export interface TouchedFieldsType {
  productName: boolean;
  productPrice: boolean;
  productDescription: boolean;
  sellCategory: boolean;
  productCategory: boolean;
  auctionSlot?: boolean;
}

export interface ImmediateListingResponse {
  immediateSaleListingID: string;
}

export interface AuctionListingResponse {
  auctionSaleListingID: string;
}

export interface ImmediateListing {
  immediateSaleListingID: string;
  productName: string;
  productDescription: string;
  price: number;
  category: string;
  sellerEmail: string;
  active: boolean;
  // approverEmail: null,
  // messageFromApprover: null,
  // dateOfApproval: null,
  // createdDate: 2024-02-21T21:33:27.021+00:00,
  // updatedDate: 2024-02-21T21:33:27.021+00:00,
  sold: boolean;
  rejected: boolean;
  approved: boolean;
  productImages: string[];
}

export interface AuctionListing {
  auctionSaleListingID: string;
  productName: string;
  productDescription: string;
  price: number;
  category: string;
  sellerEmail: string;
  active: boolean;
  // approverEmail: null,
  // messageFromApprover: null,
  // dateOfApproval: null,
  // createdDate: 2024-02-21T21:33:27.021+00:00,
  // updatedDate: 2024-02-21T21:33:27.021+00:00,
  sold: boolean;
  rejected: boolean;
  approved: boolean;
  productImages: string[];
}

export interface GetAllImmediateListingResponse {
  status: number;
  data: ImmediateListing[];
}

export interface ImageUrls {
  imageURLs: string[];
}

export interface GetAllImmediateListingImagesResponse {
  status: number;
  data: ImageUrls;
}

export interface GetAllAuctionListingResponse {
  status: number;
  data: AuctionListing[];
}

export interface AdminGetAllListingResponse {
  status: number;
  data: AdminGetAllListingResponseType[];
}

export interface AdminGetImmediateSaleProductByIdType {
  immediateSaleListingID: string;
  productName: string;
  price: number;
  active: boolean;
  approved: boolean;
  rejected: boolean;
  productImages: string[];
  productDescription: string;
}

export interface AdminGetImmediateSaleProductById {
  status: number;
  data: AdminGetImmediateSaleProductByIdType;
}

export interface AdminGetAllListingResponseType {
  immediateSaleListingID: string;
  productName: string;
  price: number;
  active: boolean;
  approved: boolean;
  rejected: boolean;
  productImages: string[];
  messageFromApprover?: string;
}

export interface SubmitReviewRequest {
  listingId: string;
  status: string;
  sellCategory: string;
  message: string;
}

export interface SubmitReviewResponse {
  listingId: string;
  status: string;
}

export interface SubmitReviewResponsetype {
  data: SubmitReviewResponse;
  status: number;
}

export interface ApprovedDeniedProducts {
  immediateSaleListingID: string;
  productName: string;
  productDescription: string;
  price: number;
  category: string;
  sellerEmail: string;
  imageURLs: string[];
  active: boolean;
  approverEmail: string;
  messageFromApprover: string;
  dateOfApproval: string;
  rejected: boolean;
  sold: boolean;
  approved: boolean;
  productImages: string[];
}

export interface GetApprovedProductsResponsetype {
  data: ApprovedDeniedProducts[];
  status: number;
}
