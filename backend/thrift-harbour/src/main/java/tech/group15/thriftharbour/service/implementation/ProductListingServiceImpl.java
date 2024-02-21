package tech.group15.thriftharbour.service.implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.http.HttpStatusCode;
import tech.group15.thriftharbour.dto.*;
import tech.group15.thriftharbour.exception.ImageUploadException;
import tech.group15.thriftharbour.exception.ListingNotFoundException;
import tech.group15.thriftharbour.mapper.ProductMapper;
import tech.group15.thriftharbour.model.AuctionSaleImage;
import tech.group15.thriftharbour.model.AuctionSaleListing;
import tech.group15.thriftharbour.model.ImmediateSaleImage;
import tech.group15.thriftharbour.model.ImmediateSaleListing;
import tech.group15.thriftharbour.repository.AuctionSaleImageRepository;
import tech.group15.thriftharbour.repository.AuctionSaleListingRepository;
import tech.group15.thriftharbour.repository.ImmediateSaleImageRepository;
import tech.group15.thriftharbour.repository.ImmediateSaleListingRepository;
import tech.group15.thriftharbour.service.AwsS3Service;
import tech.group15.thriftharbour.service.JWTService;
import tech.group15.thriftharbour.service.ProductListingService;
import tech.group15.thriftharbour.utils.DateUtil;
import tech.group15.thriftharbour.utils.FileUtils;
import tech.group15.thriftharbour.utils.UUIDUtil;

@Service
@RequiredArgsConstructor
public class ProductListingServiceImpl implements ProductListingService {

    private final JWTService jwtService;

    private final AwsS3Service awsS3Service;

    private final ImmediateSaleListingRepository immediateSaleListingRepository;

    private final ImmediateSaleImageRepository immediateSaleImageRepository;

    private final AuctionSaleListingRepository auctionSaleListingRepository;

    private final AuctionSaleImageRepository auctionSaleImageRepository;

    @Value("${aws.bucketName}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;


    // Image file and filename as input and the image is uploaded to s3 bucket
    private int uploadSingleImage(MultipartFile productImage, String uniqueFileName) {
        if (FileUtils.isImageFile(productImage)) {
            return awsS3Service.uploadImageToBucket(uniqueFileName, productImage);
        } else {
            throw new ImageUploadException("Error while Uploading image, Please try again later");
        }

    }


    // Method to create an immediate sale listing
    @Override
    public ImmediateSaleListingCreationResponse createImmediateSaleListing(String authorizationHeader, SubmitListingRequest listingRequest) {
        String userName = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);

        Date createdDate = DateUtil.getCurrentDate();

        ImmediateSaleListing immediateSaleListing = ImmediateSaleListing
                .builder()
                .immediateSaleListingID(UUIDUtil.generateUUID())
                .productName(listingRequest.getProductName())
                .productDescription(listingRequest.getProductDescription())
                .price(listingRequest.getProductPrice())
                .sellerEmail(userName)
                .category(listingRequest.getProductCategory())
                .createdDate(createdDate)
                .updatedDate(createdDate)
                .build();

        List<ImmediateSaleImage> immediateSaleImages = new ArrayList<>();
        List<String> imageURLs = new ArrayList<>();


        List<MultipartFile> productImages = listingRequest.getProductImages();
        for (int iter = 0; iter < productImages.size(); ++iter) {
            MultipartFile productImage = productImages.get(iter);
            String uniqueFileName = FileUtils.generateUniqueFileNameForImage(String.valueOf(listingRequest.getSellCategory()),
                    immediateSaleListing.getImmediateSaleListingID(), (iter + 1), FileUtils.getFileExtention(productImage));
            int returnCode = uploadSingleImage(productImage, uniqueFileName); // image is uploaded to s3 bucket and returns an upload status code
            if (returnCode == HttpStatusCode.OK) {
                String imageURL = FileUtils.generateImageURL(bucketName, region, uniqueFileName);

                // Build Immediate sale Image
                ImmediateSaleImage immediateSaleImage = ImmediateSaleImage
                        .builder()
                        .immediateSaleListingID(immediateSaleListing.getImmediateSaleListingID())
                        .imageURL(imageURL)
                        .createdDate(createdDate)
                        .build();
                immediateSaleImages.add(immediateSaleImage);
                imageURLs.add(imageURL);
            }
        }

        // Save Listing and image to database
        immediateSaleListingRepository.save(immediateSaleListing);
        immediateSaleImageRepository.saveAll(immediateSaleImages);

        return ImmediateSaleListingCreationResponse
                .builder()
                .immediateSaleListingID(immediateSaleListing.getImmediateSaleListingID())
                .productName(immediateSaleListing.getProductName())
                .productDescription(immediateSaleListing.getProductDescription())
                .price(immediateSaleListing.getPrice())
                .category(immediateSaleListing.getCategory())
                .sellerEmail(immediateSaleListing.getSellerEmail())
                .imageURLs(imageURLs)
                .active(immediateSaleListing.isActive())
                .isApproved(immediateSaleListing.isApproved())
                .isRejected(immediateSaleListing.isRejected())
                .createdDate(immediateSaleListing.getCreatedDate())
                .build();

    }

    @Override
    public AuctionSaleListingCreationResponse createAuctionSaleListing(String authorizationHeader, SubmitListingRequest listingRequest) {
        String userName = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);

        Date createdDate = DateUtil.getCurrentDate();
        Date auctionSlot = DateUtil.getDateFromString(listingRequest.getAuctionSlot());

        AuctionSaleListing auctionSaleListing = AuctionSaleListing
                .builder()
                .auctionSaleListingID(UUIDUtil.generateUUID())
                .productName(listingRequest.getProductName())
                .productDescription(listingRequest.getProductDescription())
                .startingBid(listingRequest.getProductPrice())
                .sellerEmail(userName)
                .category(listingRequest.getProductCategory())
                .auctionSlot(auctionSlot)
                .createdDate(createdDate)
                .updatedDate(createdDate)
                .build();

        List<AuctionSaleImage> auctionSaleImages = new ArrayList<>();
        List<String> imageURLs = new ArrayList<>();

        List<MultipartFile> productImages = listingRequest.getProductImages();
        for (int iter = 0; iter < productImages.size(); ++iter) {
            MultipartFile productImage = productImages.get(iter);
            String uniqueFileName = FileUtils.generateUniqueFileNameForImage(String.valueOf(listingRequest.getSellCategory()),
                    auctionSaleListing.getAuctionSaleListingID(), (iter + 1), FileUtils.getFileExtention(productImage));
            int returnCode = uploadSingleImage(productImage, uniqueFileName);
            if (returnCode == HttpStatusCode.OK) {
                String imageURL = FileUtils.generateImageURL(bucketName, region, uniqueFileName);

                AuctionSaleImage auctionSaleImage = AuctionSaleImage
                        .builder()
                        .auctionSaleListingID(auctionSaleListing.getAuctionSaleListingID())
                        .imageURL(imageURL)
                        .createdDate(createdDate)
                        .build();
                auctionSaleImages.add(auctionSaleImage);
                imageURLs.add(imageURL);
            } else {
                throw new ImageUploadException("Error while Uploading image, Please try again later");
            }
        }


        auctionSaleListingRepository.save(auctionSaleListing);
        auctionSaleImageRepository.saveAll(auctionSaleImages);

        return AuctionSaleListingCreationResponse
                .builder()
                .auctionSaleListingID(auctionSaleListing.getAuctionSaleListingID())
                .productName(auctionSaleListing.getProductName())
                .productDescription(auctionSaleListing.getProductDescription())
                .startingBid(auctionSaleListing.getStartingBid())
                .category(auctionSaleListing.getCategory())
                .sellerEmail(auctionSaleListing.getSellerEmail())
                .auctionSlot(auctionSaleListing.getAuctionSlot())
                .imageURLs(imageURLs)
                .active(auctionSaleListing.isActive())
                .isApproved(auctionSaleListing.isApproved())
                .isRejected(auctionSaleListing.isRejected())
                .createdDate(auctionSaleListing.getCreatedDate())
                .build();


    }

    /* Get single product for view */
    @Override
    public ImmediateSaleListing findImmediateSaleListingByID(String immediateSaleListingID) {
    return immediateSaleListingRepository
        .findById(immediateSaleListingID)
        .orElseThrow(() -> new ListingNotFoundException("Product not found!"));
    }

    /* Get all product listing for admin */
    @Override
    public List<ImmediateSaleMinifiedResponse> findAllImmediateSaleListing() {
        return ProductMapper.minifiedProductResponse(immediateSaleListingRepository.findAll());
    }

    @Override
    public List<ImmediateSaleListing> findAllImmediateSaleListingBySellerEmail(String authorizationHeader) {
        String sellerEmail = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);
        return immediateSaleListingRepository.findAllBySellerEmail(sellerEmail);
    }

    @Override
    public List<AuctionSaleListing> findAllAuctionSaleListingBySellerEmail(String authorizationHeader) {
        String sellerEmail = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);
        return auctionSaleListingRepository.findAllBySellerEmail(sellerEmail);
    }

    @Override
    public GetListingImageResponse findAllImmediateSaleListingImagesByID(String listingID) {
        List<ImmediateSaleImage> productImages = immediateSaleImageRepository
                .getAllByImmediateSaleListingID(listingID);
        return GetListingImageResponse
                .builder()
                .ListingId(listingID)
                .imageURLs(productImages.stream()
                        .map(ImmediateSaleImage::getImageURL)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public GetListingImageResponse findAllAuctionSaleListingImagesByID(String listingID){
        List<AuctionSaleImage> productImages = auctionSaleImageRepository
                .findAllByAuctionSaleListingID(listingID);
        return GetListingImageResponse
                .builder()
                .ListingId(listingID)
                .imageURLs(productImages.stream()
                        .map(AuctionSaleImage::getImageURL)
                        .collect(Collectors.toList()))
                .build();
    }


}
