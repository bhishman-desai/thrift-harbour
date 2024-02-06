import axios from "axios";
import { Credentials } from "../types/types";
import { Path } from "../utils/Path";

export class Auth {
  path = new Path(process.env.NODE_ENV);

  async signUpUser(requestParams: Credentials) {
    const baseUrl = this.path.getBaseUrl();
    const signUpUrl = this.path.getAuthUrl("signup");
    const requestUrl = baseUrl + signUpUrl;

    try {
      const response = await axios.post(requestUrl, requestParams);
      return response.status;
    } catch (error) {
      console.log("error", error);
      return error;
    }
  }
}
