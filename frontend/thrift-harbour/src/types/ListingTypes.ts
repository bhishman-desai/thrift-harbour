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

export interface AdminGetAllListingResponseType {
  immediateSaleListingID: string;
  productName: string;
  price: number;
  active: number;
  approved: boolean;
  rejected: boolean;
  productImages: string[];
}
