import axios from "axios";
import { ErrorResponse } from "../types/AuthTypes";
import { AdminGetAllListingResponse } from "../types/ListingTypes";
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
}
