import axios, { AxiosResponse } from "axios";
import {
  Credentials,
  ErrorResponse,
  LoginCredentials,
  LoginResponse,
  SignUpResponse,
} from "../types/types";
import { Path } from "../utils/Path";

export class Auth {
  path = new Path(process.env.NODE_ENV);

  async signUpUser(
    requestParams: Credentials
  ): Promise<[SignUpResponse | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const signUpUrl = this.path.getAuthUrl("signup");
    const requestUrl = baseUrl + signUpUrl;

    try {
      const response: AxiosResponse<SignUpResponse> = await axios.post(
        requestUrl,
        requestParams
      );
      return [response.data, null];
    } catch (error: any) {
      console.log("error", error);
      return [null, error?.response.data as ErrorResponse];
    }
  }

  async signInUser(
    requestParams: LoginCredentials
  ): Promise<[LoginResponse | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const signUpUrl = this.path.getAuthUrl("signin");
    const requestUrl = baseUrl + signUpUrl;

    try {
      const response: AxiosResponse<LoginResponse> = await axios.post(
        requestUrl,
        requestParams
      );

      return [response.data, null];
    } catch (error: any) {
      return [null, error?.response.data as ErrorResponse];
    }
  }
}
