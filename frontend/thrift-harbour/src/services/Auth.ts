import axios from "axios";
import { SignUpRequest } from "../types/types";
import { Path } from "../utils/Path";

export class Auth {
  path = new Path(process.env.NODE_ENV);

  async signUpUser(requestParams: SignUpRequest) {
    const baseUrl = this.path.getBaseUrl();
    const signUpUrl = this.path.getAuthUrl("signup");
    const requestUrl = baseUrl + signUpUrl;

    try {
      const response = await axios.post(requestUrl, requestParams);
      console.log("response of signup", response);
    } catch {
      console.log("error");
    }
  }
}
