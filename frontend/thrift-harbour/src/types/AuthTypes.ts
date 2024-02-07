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
