import { useEffect, useState } from "react";
import ProductList from "../../../components/ProductList/ProductList";
import { AdminServices } from "../../../services/Admin";
import { ListingService } from "../../../services/Listing";
import { Grid, Header } from "../listed-products/ListedProductsStyles";

const ListedBySeller = () => {
  const admin = new AdminServices();
  const listing = new ListingService();
  const token = localStorage.getItem("token");
  const id = localStorage.getItem("uId");
  const [productsByseller, setProductsByseller] = useState<any>([]);
  const [error, setError] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    (async function () {
      try {
        const response = await admin.getSellerbyId(parseInt(id || "1"), token);

        if (response[0]?.status === 200) {
          setLoading(false);
          const data = response[0].data;

          data.map(async (product: any) => {
            const imagesResponse =
              await listing.getImmediateListedProductsImages(
                product.immediateSaleListingID,
                token
              );
            if (imagesResponse[0]?.status === 200) {
              product.productImages = imagesResponse[0].data.imageURLs;
            }
            setProductsByseller([...data]);
          });
        } else {
          setError(true);
          setLoading(false);
        }
      } catch (error) {
        setLoading(false);
        setError(true);
      }
    })();
  }, []);
  return (
    <>
      <Header style={{ marginLeft: "16px" }}>Listing Done by Seller</Header>
      {productsByseller.length === 0 ? (
        "Loading..."
      ) : (
        <Grid>
          {productsByseller.map((product: any) => {
            return <ProductList product={product} showViewButton={false} />;
          })}
        </Grid>
      )}
    </>
  );
};

export default ListedBySeller;
