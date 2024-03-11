import { Path } from "../utils/Path";
import { ErrorResponse, GetUserResponse } from "../types/AuthTypes";
import axios, { AxiosResponse } from "axios";
import { ChatMessageType } from "../types/ChatTypes";

export class ChatService {
  path = new Path(process.env.NODE_ENV);

  async fetchChatHistory(
    senderID: string,
    recipientID: string,
  ): Promise<[ChatMessageType[] | null, ErrorResponse | null]> {
    const baseUrl = this.path.getChatUrl();
    const requestUrl = baseUrl + `/messages/${senderID}/${recipientID}`;

    try {
      const response: AxiosResponse<ChatMessageType[]> =
        await axios.get(requestUrl);
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

  async fetchChatUser(
    recipientID: string,
  ): Promise<[any | null, ErrorResponse | null]> {
    const token = localStorage.getItem("token");
    const baseUrl = this.path.getBaseUrl();
    const requestUrl = baseUrl + `/user/senders/${recipientID}`;

    try {
      const response = await axios.get(requestUrl, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return [{ status: response.status, message: response.data }, null];
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
