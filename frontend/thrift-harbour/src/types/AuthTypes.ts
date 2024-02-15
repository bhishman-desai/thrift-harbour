export interface Credentials {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

export interface SignUpResponse {
  status: number;
  userID: number;
}

export interface LoginCredentials {
  email: string;
  password: string;
}

export interface LoginResponse {
  status: number;
  token: string;
  refreshToken: string;
}

export interface ErrorResponse {
  status: number;
  message: string;
}

export interface GetUserResponse {
  status: number;
  message: string;
}

export interface ForgotPasswordCredentials {
  email: string;
}

export interface ResetPasswordFields {
  newPassword: string;
  confirmPassword: string;
}

export interface ForgotPasswordResponse {
  status: number;
  message: string;
}

export interface ResetPasswordRequest {
  password: string;
  token: string;
}

export interface ResetPasswordResponse {
  status: number;
  message: string;
}
