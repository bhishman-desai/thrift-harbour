import axios, { AxiosResponse } from "axios";
import { Credentials, ErrorResponse } from "../types/AuthTypes";
import {
  AuctionListingResponse,
  ImmediateListingResponse,
  ListingDataTypes,
} from "../types/ListingTypes";
import { Path } from "../utils/Path";

export class ListingService {
  path = new Path(process.env.NODE_ENV);

  async immediateListing(
    payload: ListingDataTypes,
    token?: string | null
  ): Promise<[ImmediateListingResponse | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const immediateListUrl = this.path.getListingUrl("immediate-listing");
    const requestUrl = baseUrl + immediateListUrl;
    console.log("requestParams", payload);
    try {
      const formData = new FormData();

      Object.entries(payload).forEach(([key, value]) => {
        console.log("key value", key, value);
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

      console.log("response in service", response);

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
    console.log("requestParams", payload);
    try {
      const formData = new FormData();

      Object.entries(payload).forEach(([key, value]) => {
        console.log("key value", key, value);
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

      console.log("response in service", response);

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
}
