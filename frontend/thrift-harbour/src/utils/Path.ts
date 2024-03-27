export class Path {
  environment: string;

  constructor(environment: string) {
    this.environment = environment;
  }

  getBaseUrl() {
    if (this.environment === "development") {
      // return "http://172.17.1.50:3000/api/v1";
      return "http://localhost:8080/api/v1";
    } else if (this.environment === "local") {
      return "http://localhost:8080/api/v1";
    } else {
      return "/thrift/api/v1";
    }
  }

  getUserUrl(service: string) {
    if (service === "immediatesale-product-detail") {
      return "/user/get-immediate-sale-product/";
    }
    if (service === "get-user-details") {
      return "/user/get-user-details/";
    }
    if (service === "add-buyer-ratings") {
      return "/user/add-buyer-ratings";
    }
    if (service === "add-seller-ratings") {
      return "/user/add-seller-ratings";
    }
    if (service === "auctionsale-product-detail") {
      return "/user/get-auction-sale-product/";
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
    if (service === "getAdmin") {
      return "/admin";
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
    if (service === "get-all-immediate-listing") {
      return "/users/listing/get-all-immediate-listing";
    }
    if (service === "get-all-auction-listing") {
      return "/users/listing/get-all-auction-listing";
    }
  }

  getAdminUrl(service: string) {
    if (service === "get-all-immediatesale-listing") {
      return "/admin/get-all-immediatesale-listing";
    }
    if (service === "get-immediatesale-product") {
      return "/admin/get-immediatesale-product";
    }
    if (service === "review-request") {
      return "/admin/review-request";
    }
    if (service === "get-approved-immediatesale-listing") {
      return "/admin/get-approved-immediatesale-listing";
    }
    if (service === "get-denied-immediatesale-listing") {
      return "/admin/get-denied-immediatesale-listing";
    }
    if (service === "get-all-sellers") {
      return "/admin/get-all-sellers";
    }
    if (service === "product-listing") {
      return "/admin/sellers";
    }
    if (service === "get-all-auction-listing") {
      return "/admin/get-all-auction-listing";
    }
    if (service === "get-auctionsale-product") {
      return "/admin/get-auctionsale-product";
    }
  }

  getChatUrl() {
    if (this.environment === "development") {
      return "http://localhost:8080";
    } else {
    }
  }

  getBiddingUrls(service: string) {
    if (service === "place-bid") {
      return "/user/place-bid";
    } else {
    }
  }
}
