import axios, { AxiosResponse } from "axios";
import {
  Credentials,
  ErrorResponse,
  ForgotPasswordResponse,
  GetUserResponse,
  LoginCredentials,
  LoginResponse,
  ResetPasswordRequest,
  ResetPasswordResponse,
  SignUpResponse,
} from "../types/AuthTypes";
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
      return [
        null,
        {
          status: error?.response.status,
          message: error?.response.data.message,
        } as ErrorResponse,
      ];
    }
  }

  async signInUser(
    requestParams: LoginCredentials
  ): Promise<[LoginResponse | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const signInUrl = this.path.getAuthUrl("signin");
    const requestUrl = baseUrl + signInUrl;

    try {
      const response: AxiosResponse<LoginResponse> = await axios.post(
        requestUrl,
        requestParams
      );
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

  async getUser(
    token: string
  ): Promise<[GetUserResponse | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const getUserUrl = this.path.getAuthUrl("getUser");
    const requestUrl = baseUrl + getUserUrl;

    try {
      const response = await axios.get(requestUrl, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log("user response", response);
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

  async getAdmin(
    token: string
  ): Promise<[GetUserResponse | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const getAdminUrl = this.path.getAuthUrl("getAdmin");
    const requestUrl = baseUrl + getAdminUrl;

    try {
      const response = await axios.get(requestUrl, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      console.log("user response", response);
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

  async forgotPassword(
    email: string
  ): Promise<[ForgotPasswordResponse | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const getForgotPassUrl = this.path.getAuthUrl("forgot-password");
    const requestUrl = baseUrl + getForgotPassUrl;
    try {
      const response = await axios.post(requestUrl, { email: email });
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

  async resetPassword(
    requestParams: ResetPasswordRequest
  ): Promise<[ResetPasswordResponse | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const getResetPassUrl = this.path.getAuthUrl("reset-password");
    const requestUrl = baseUrl + getResetPassUrl;
    try {
      const response: AxiosResponse<ResetPasswordResponse> = await axios.post(
        requestUrl,
        requestParams
      );
      return [
        { status: response.status, message: response.data?.message },
        null,
      ];
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
