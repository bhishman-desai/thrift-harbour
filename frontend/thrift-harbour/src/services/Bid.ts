import { Path } from "../utils/Path";
import { ErrorResponse, GetUserResponse } from "../types/AuthTypes";
import axios, { AxiosResponse } from "axios";
import { ChatMessageType } from "../types/ChatTypes";

export class BidService {
  path = new Path(process.env.NODE_ENV);

  async placeBid(
    token: string,
    payload: any
  ): Promise<[any | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const placeBidUrl = this.path.getBiddingUrls("place-bid");
    const requestUrl = baseUrl + placeBidUrl!;
    console.log("request url for bidding", requestUrl);
    try {
      const response = await axios.post(requestUrl, payload, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log("bid response", response);
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
