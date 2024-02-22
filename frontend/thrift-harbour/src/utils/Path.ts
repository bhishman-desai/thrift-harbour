export class Path {
  environment: string;
  constructor(environment: string) {
    this.environment = environment;
  }

  getBaseUrl() {
    if (this.environment === "development") {
      return "http://localhost:8080/api/v1";
    } else {
      return "/thrift/api/v1";
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

  getListingUrl(service: string) {
    if (service === "immediate-listing") {
      return "/users/listing/create-immediatesale-listing";
    }
    if (service === "auction-listing") {
      return "/users/listing/create-auctionsale-listing";
    }
    if (service === "get-immediatesale-listing") {
      return "/users/listing/get-immediatesale-listing";
    }
    if (service === "get-immediatesale-images") {
      return "/users/listing/get-immediatesale-images";
    }
    if (service === "get-auctionsale-listing") {
      return "/users/listing/get-auctionsale-listing";
    }
    if (service === "get-auctionsale-images") {
      return "/users/listing/get-auctionsale-images";
    }
  }
}
