import axios from "axios";
import { ErrorResponse } from "../types/AuthTypes";

import { Path } from "../utils/Path";

export class UsersService {
  path = new Path(process.env.NODE_ENV);

  async getUserData(
    id: number,
    token?: string | null
  ): Promise<[any | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const getUserDetailsPath = this.path.getUserUrl("get-user-details");
    const requestUrl = `${baseUrl + getUserDetailsPath + id}`;

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

  async reviewSeller(
    payload: any,
    token?: string | null
  ): Promise<[any | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const addSellerUrl = this.path.getUserUrl("add-seller-ratings");
    const requestUrl = baseUrl + addSellerUrl;
    try {
      const response = await axios.post(requestUrl, payload, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });
      return [response.status, null];
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

  async reviewBuyer(
    payload: any,
    token?: string | null
  ): Promise<[any | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const addBuyerUrl = this.path.getUserUrl("add-buyer-ratings");
    const requestUrl = baseUrl + addBuyerUrl;
    try {
      const response = await axios.post(requestUrl, payload);

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
