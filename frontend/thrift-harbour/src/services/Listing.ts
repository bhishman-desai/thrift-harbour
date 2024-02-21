import axios, { AxiosResponse } from "axios";
import { Credentials, ErrorResponse } from "../types/AuthTypes";
import { ListingDataTypes } from "../types/ListingTypes";
import { Path } from "../utils/Path";

export class ListingService {
  path = new Path(process.env.NODE_ENV);

  async immediateListing(
    payload: ListingDataTypes,
    token?: string | null
  ): Promise<[any | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const immediateListUrl = this.path.getListingUrl("immediate-listing");
    const requestUrl = baseUrl + immediateListUrl;
    console.log("requestParams", payload);
    try {
      const formData = new FormData();

      Object.entries(payload).forEach(([key, value]) => {
        console.log("key value", key, value);
        formData.append(key, value);
      });
      //   formData.append("productName", payload.productName);
      //   formData.append("productDescription", payload.productDescription);
      //   formData.append("productImages", "C:UsersKrutikDesktopshankar bhagwan");
      //   formData.append("productImages", payload.productImages);
      //   formData.append("productPrice", String(payload.productPrice));
      //   formData.append("productCategory", payload.productCategory);
      //   formData.append("sellCategory", payload.sellCategory);
      //   formData.append("auctionSlot", "");

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
}
