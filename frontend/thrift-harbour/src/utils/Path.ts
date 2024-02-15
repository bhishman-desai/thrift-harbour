export class Path {
  environment: string;
  constructor(environment: string) {
    this.environment = environment;
  }

  getBaseUrl() {
    if (this.environment === "development") {
      return "http://localhost:8080/api/v1";
    } else {
      //TODO : production env endpoint
      return "http://localhost:8080/api/v1";
    }
  }

  getAuthUrl(service: string) {
    if (service === "signup") {
      return "/auth/signup";
    }
    if (service === "signin") {
      return "/auth/signin";
    }
    if (service === "getUser") {
      return "/user";
    }
    if (service === "forgot-password") {
      return "/auth/forgot-password";
    }
    if (service === "reset-password") {
      return "/auth/reset-password";
    }
  }
}
