import axios from "axios";
import { ErrorResponse } from "../types/AuthTypes";
import {
  AdminGetAllListingResponse,
  AdminGetImmediateSaleProductById,
  GetApprovedProductsResponsetype,
  GetSellersResponsetype,
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

    try {
      const response = await axios.post(requestUrl, payload, {
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

  async getApprovedListing(
    token?: string | null
  ): Promise<[GetApprovedProductsResponsetype | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const getApprovedListingUrl = this.path.getAdminUrl(
      "get-approved-immediatesale-listing"
    );
    const requestUrl = baseUrl + getApprovedListingUrl;

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

  async getRejectedListing(
    token?: string | null
  ): Promise<[GetApprovedProductsResponsetype | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const getRejectedListingUrl = this.path.getAdminUrl(
      "get-denied-immediatesale-listing"
    );
    const requestUrl = baseUrl + getRejectedListingUrl;

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

  async getSellers(
    token?: string | null
  ): Promise<[GetSellersResponsetype | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const getSellersUrl = this.path.getAdminUrl("get-all-sellers");
    const requestUrl = baseUrl + getSellersUrl;

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

  async getSellerbyId(
    id: number,
    token?: string | null
  ): Promise<[any | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const getSellerByIdUrl = this.path.getAdminUrl("product-listing");
    const requestUrl =
      baseUrl + getSellerByIdUrl + `/${id}` + "/product-listing";

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

  async getUserbyIDAdmin(
    id: number,
    token?: string | null
  ): Promise<[any | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const requestUrl = baseUrl + `/admin/sellers` + `/${id}`;

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

  async getUserbyIDUser(
    id: number,
    token?: string | null
  ): Promise<[any | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const requestUrl = baseUrl + `/user/users` + `/${id}`;

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
}
