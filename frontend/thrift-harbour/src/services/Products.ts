import axios from "axios";
import { ErrorResponse } from "../types/AuthTypes";

import { Path } from "../utils/Path";

export class ProductsService {
  path = new Path(process.env.NODE_ENV);

  async getAllListedProducts(
    token?: string | null
  ): Promise<[any | null, ErrorResponse | null]> {
    const baseUrl = this.path.getBaseUrl();
    const getAllProducts = this.path.getProductsUrl("get-all-products");
    const requestUrl = baseUrl + getAllProducts;

    try {
      const response = await axios.get(requestUrl, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      // Handle errors
      console.log("in service", response);
      return [response, null];
    } catch (error) {
      console.error("Error fetching data:", error);
      throw error;
    }
  }
}
