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
      return "";
    }
  }

  getAuthUrl(service: string) {
    if (service === "signup") {
      return "/auth/signup";
    }
    if (service === "signin") {
      return "/auth/signin";
    }
  }
}
