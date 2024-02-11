package tech.group15.thriftharbour.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.http.HttpStatusCode;
import tech.group15.thriftharbour.dto.SubmitListingRequest;
import tech.group15.thriftharbour.exception.ImageUploadException;
import tech.group15.thriftharbour.model.ImmediateSaleImage;
import tech.group15.thriftharbour.model.ImmediateSaleListing;
import tech.group15.thriftharbour.repository.ImmediateSaleImageRepository;
import tech.group15.thriftharbour.repository.ImmediateSaleListingRepository;
import tech.group15.thriftharbour.service.AwsS3Service;
import tech.group15.thriftharbour.service.JWTService;
import tech.group15.thriftharbour.service.ProductlistingService;
import tech.group15.thriftharbour.utils.FileUtils;
import tech.group15.thriftharbour.utils.UUIDUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductListingServiceImpl implements ProductlistingService {

    @Autowired
    JWTService jwtService;

    @Autowired
    AwsS3Service awsS3Service;

    @Autowired
    ImmediateSaleListingRepository immediateSaleListingRepository;

    @Autowired
    ImmediateSaleImageRepository immediateSaleImageRepository;

    @Value("${aws.bucketName}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    @Override
    public void CreateImmediateSaleListing(String authorizationHeader, SubmitListingRequest listingRequest) {
        String userName = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);

        Date createdDate = new Date();

        ImmediateSaleListing immediateSaleListing = ImmediateSaleListing
                .builder()
                .immediateSaleListingID(UUIDUtil.generateUUID())
                .productName(listingRequest.getProductName())
                .productDescription(listingRequest.getProductDescription())
                .price(listingRequest.getProductPrice())
                .SellerEmail(userName)
                .category(listingRequest.getProductCategory())
                .createdDate(createdDate)
                .updatedDate(createdDate)
                .build();

        List<ImmediateSaleImage> immediateSaleImages = new ArrayList<ImmediateSaleImage>();

        List<MultipartFile> productImages = listingRequest.getProductImages();
        for(int iter = 0; iter < productImages.size(); ++iter){
            MultipartFile productImage = productImages.get(iter);
            if(FileUtils.isImageFile(productImage)){
                String uniqueFileName = FileUtils.generateUniqueFileNameForImage(String.valueOf(listingRequest.getSellCategory()),
                        immediateSaleListing.getImmediateSaleListingID(), (iter+1), FileUtils.getFileExtention(productImage));
                int returnCode = awsS3Service.uploadImageToBucket(uniqueFileName, productImage);
                if (returnCode == HttpStatusCode.OK){
                    String imageURL = FileUtils.generateImageURL(bucketName, region, uniqueFileName);
                    ImmediateSaleImage immediateSaleImage = ImmediateSaleImage
                            .builder()
                            .immediateSaleListingID(immediateSaleListing.getImmediateSaleListingID())
                            .imageURL(imageURL)
                            .createdDate(createdDate)
                            .build();
                    immediateSaleImages.add(immediateSaleImage);
                }
                else {
                    throw new ImageUploadException("Error while Uploading image, Please try again later");
                }

            }
        }

        immediateSaleListingRepository.save(immediateSaleListing);
        immediateSaleImageRepository.saveAll(immediateSaleImages);

    }

    @Override
    public void CreateAuctionSaleListing(String authToken, SubmitListingRequest listingRequest) {

    }
}
