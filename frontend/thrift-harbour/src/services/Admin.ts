import axios from "axios";
import { ErrorResponse } from "../types/AuthTypes";
import {
  AdminGetAllListingResponse,
  AdminGetAllListingResponseType,
  AdminGetImmediateSaleProductById,
  SubmitReviewRequest,
  SubmitReviewResponsetype,
} from "../types/ListingTypes";
import { Path } from "../utils/Path";

export class AdminServices {
  path = new Path(process.env.NODE_ENV);

  async getImmediateListedProducts(
    token?: string | null
  ): Promise<[AdminGetAllListingResponse | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const getallImmediateListingtUrl = this.path.getAdminUrl(
      "get-all-immediatesale-listing"
    );
    const requestUrl = baseUrl + getallImmediateListingtUrl;
    console.log("requestUrl===>", requestUrl);

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

  async getImmediateListedProductById(
    id: string,
    token?: string | null
  ): Promise<[AdminGetImmediateSaleProductById | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const getallImmediateListingtByIdUrl = this.path.getAdminUrl(
      "get-immediatesale-product"
    );
    const requestUrl = baseUrl + getallImmediateListingtByIdUrl + `/${id}`;
    console.log("requestUrl===>", requestUrl);

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

  async submitReview(
    payload: SubmitReviewRequest,
    token?: string | null
  ): Promise<[SubmitReviewResponsetype | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const getSubmitReviewUrl = this.path.getAdminUrl("review-request");
    const requestUrl = baseUrl + getSubmitReviewUrl;
    console.log("requestUrl===>", requestUrl);

    try {
      const response = await axios.post(requestUrl, payload, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      // Handle the response data
      return [response.data, null];
    } catch (error) {
      // Handle errors
      throw error;
    }
  }
}
