import axios, { AxiosResponse } from "axios";
import { Credentials, ErrorResponse } from "../types/AuthTypes";
import {
  AuctionListingResponse,
  GetAllAuctionListingResponse,
  GetAllImmediateListingImagesResponse,
  GetAllImmediateListingResponse,
  ImmediateListingResponse,
  ListingDataTypes,
  ImmediateListing,
} from "../types/ListingTypes";
import { ImmediateSaleProductDetail, AuctionSaleProductDetail } from "../types/ProductSaleDetails";
import { Path } from "../utils/Path";

export class ListingService {
  path = new Path(process.env.NODE_ENV);


  async immediateSaleProductDetail(immediateSaleListingID: string | null,
     token?: string | null
     ): Promise<ImmediateSaleProductDetail>{
    const baseUrl = this.path.getBaseUrl();
    const immediateListUrl = this.path.getUserUrl("immediatesale-product-detail");
    const requestUrl = baseUrl + immediateListUrl + `${immediateSaleListingID}`;

    try {
      const response = await axios.get(requestUrl, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data as ImmediateSaleProductDetail;
    }
    catch(error){
      throw error;
    }
  };

  async auctionSaleProductDetail(auctionSaleListingID: string | null,
    token?: string | null): Promise<any>{
      const baseUrl = this.path.getBaseUrl();
      const auctionListUrl = this.path.getUserUrl("auctionsale-product-detail");
      const requestUrl = baseUrl + auctionListUrl + `${auctionSaleListingID}`;
  
      try {
        const response = await axios.get(requestUrl, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        return response;
      }
      catch(error){
        throw error;
      }
    }

  async immediateListing(
    payload: ListingDataTypes,
    token?: string | null
  ): Promise<[ImmediateListingResponse | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const immediateListUrl = this.path.getListingUrl("immediate-listing");
    const requestUrl = baseUrl + immediateListUrl;
    try {
      const formData = new FormData();

      Object.entries(payload).forEach(([key, value]) => {
        if (key === "productImages") {
          value.forEach((file: any) => {
            formData.append(key, file);
          });
        } else {
          formData.append(key, value);
        }
      });

      const response = await axios.post(requestUrl, formData, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "multipart/form-data",
        },
      });

      return [response.data, null];
    } catch (error: any) {
      return [
        null,
        {
          status: error?.response.status,
          message: error?.response.data.message,
        } as ErrorResponse,
      ];
    }
  }

  async auctionListing(
    payload: ListingDataTypes,
    token?: string | null
  ): Promise<[AuctionListingResponse | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const auctionListUrl = this.path.getListingUrl("auction-listing");
    const requestUrl = baseUrl + auctionListUrl;
    try {
      const formData = new FormData();

      Object.entries(payload).forEach(([key, value]) => {
        if (key === "productImages") {
          value.forEach((file: any) => {
            formData.append(key, file);
          });
        } else {
          formData.append(key, value);
        }
      });

      const response = await axios.post(requestUrl, formData, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "multipart/form-data",
        },
      });

      return [response.data, null];
    } catch (error: any) {
      return [
        null,
        {
          status: error?.response.status,
          message: error?.response.data.message,
        } as ErrorResponse,
      ];
    }
  }

  async getImmediateListedProducts(
    token?: string | null
  ): Promise<[GetAllImmediateListingResponse | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const getImmediateListingtUrl = this.path.getListingUrl(
      "get-immediatesale-listing"
    );
    const requestUrl = baseUrl + getImmediateListingtUrl;

    try {
      const response = await axios.get(requestUrl, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      // Handle the response data
      return [response, null];
    } catch (error) {
      // Handle errors
      throw error;
    }
  }

  async getImmediateListedProductsImages(
    immediateListingId: string,
    token?: string | null
  ): Promise<
    [GetAllImmediateListingImagesResponse | null, ErrorResponse | null]
  > {
    const baseUrl = this.path.getBaseUrl();
    const getImmediateListingImagesUrl = this.path.getListingUrl(
      "get-immediatesale-images"
    );
    const requestUrl =
      baseUrl + getImmediateListingImagesUrl + `/${immediateListingId}`;

    try {
      const response = await axios.get(requestUrl, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      // Handle the response data
      return [response, null];
    } catch (error) {
      // Handle errors
      console.error("Error fetching data:", error);
      throw error;
    }
  }

  async getAuctionListedProducts(
    token?: string | null
  ): Promise<[GetAllAuctionListingResponse | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const getAuctionListingImagesUrl = this.path.getListingUrl(
      "get-auctionsale-listing"
    );
    const requestUrl = baseUrl + getAuctionListingImagesUrl;

    try {
      const response = await axios.get(requestUrl, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      // Handle the response data
      return [response, null];
    } catch (error) {
      // Handle errors
      throw error;
    }
  }

  async getAuctionListedProductsImages(
    auctionListingId: string,
    token?: string | null
  ): Promise<
    [GetAllImmediateListingImagesResponse | null, ErrorResponse | null]
  > {
    const baseUrl = this.path.getBaseUrl();
    const getAuctionListingImagesUrl = this.path.getListingUrl(
      "get-auctionsale-images"
    );
    const requestUrl =
      baseUrl + getAuctionListingImagesUrl + `/${auctionListingId}`;

    try {
      const response = await axios.get(requestUrl, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      // Handle errors
      return [response, null];
    } catch (error) {
      console.error("Error fetching data:", error);
      throw error;
    }
  }
}
