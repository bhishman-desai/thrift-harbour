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
}
