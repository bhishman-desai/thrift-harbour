import {
  ImageContainer,
  Image,
  ProductCard,
  ProductInfo,
  ProductNameAndDescription,
  Rest,
  Title,
  Value,
  ViewButtonContainer,
} from "../../features/admin/AdminDashboardStyles";
import { Button } from "../../features/product-listing/listed-products/ListedProductsStyles";
import { ProductShowcase } from "../../features/product/ViewProductsStyles";
import {
  AdminGetAllListingResponseType,
  ApprovedDeniedProducts,
} from "../../types/ListingTypes";
import { Helper } from "../../utils/Helper";

interface ProductListProps {
  product: AdminGetAllListingResponseType;
  handleViewClick: (product: AdminGetAllListingResponseType) => void;
}
const ProductList: React.FC<ProductListProps> = ({
  product,
  handleViewClick,
}) => {
  const helper = new Helper();
  return (
    <>
      <ProductCard>
        <ImageContainer>
          <Image>
            <img
              height={"100%"}
              width={"100%"}
              src={product.productImages && product.productImages[0]}
            />
          </Image>
        </ImageContainer>
        <Rest>
          <ProductNameAndDescription>
            <ProductInfo>
              <Title>Name: </Title>
              <Value style={{ marginLeft: "4px" }}>{product.productName}</Value>
            </ProductInfo>
            <ProductInfo style={{ marginTop: "4px" }}>
              <Title>Price: </Title>
              <Value style={{ marginLeft: "4px" }}>{"$" + product.price}</Value>
            </ProductInfo>
            <ProductInfo style={{ marginTop: "4px" }}>
              <Title>Status: </Title>
              {helper.getListingStatus(product.approved, product.rejected) ===
              "Approved" ? (
                <Value style={{ marginLeft: "4px", color: "green" }}>
                  {helper.getListingStatus(product.approved, product.rejected)}
                </Value>
              ) : (
                <Value style={{ marginLeft: "4px", color: "red" }}>
                  {product.messageFromApprover ? (
                    <>Rejected ( {product.messageFromApprover} )</>
                  ) : (
                    <>Rejected</>
                  )}
                </Value>
              )}
            </ProductInfo>
          </ProductNameAndDescription>
          <ViewButtonContainer>
            <Button onClick={() => handleViewClick(product)}>View</Button>
          </ViewButtonContainer>
        </Rest>
      </ProductCard>
    </>
  );
};

export default ProductList;
