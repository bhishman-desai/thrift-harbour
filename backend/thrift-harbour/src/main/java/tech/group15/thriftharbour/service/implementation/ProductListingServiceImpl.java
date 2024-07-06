package tech.group15.thriftharbour.service.implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.http.HttpStatusCode;
import tech.group15.thriftharbour.constant.ErrorConstant;
import tech.group15.thriftharbour.dto.request.SubmitListingRequest;
import tech.group15.thriftharbour.dto.response.*;
import tech.group15.thriftharbour.enums.RoleEnum;
import tech.group15.thriftharbour.exception.ImageUploadException;
import tech.group15.thriftharbour.exception.ListingNotFoundException;
import tech.group15.thriftharbour.mapper.ProductMapper;
import tech.group15.thriftharbour.model.*;
import tech.group15.thriftharbour.repository.*;
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

    private final ImmediateSaleListingRepository immediateListingRepository;

    private final ImmediateSaleImageRepository immediateImageRepository;

    private final AuctionSaleListingRepository auctionListingRepository;

    private final AuctionSaleImageRepository auctionImageRepository;

    private final UserRepository userRepository;

    @Value("${aws.bucketName}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    /**
     * create a immediate sale listing.
     *
     * @param authorizationHeader The authorization header containing the JWT of user.
     * @param listingRequest has the listing details
     * @param productImages has the byte data of uploaded images
     * @return A list of {@code ImmediateSaleListingCreationResponse}  containing the
     * details of listed product
     */
    @Override
    public ImmediateSaleListingCreationResponse createImmediateSaleListing(
            String authorizationHeader, SubmitListingRequest listingRequest, List<MultipartFile> productImages) {
        String userName = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);

        /* Fetch the User entity based on the seller's email */
        User seller =
                userRepository
                        .findByEmail(userName)
                        .orElseThrow(() -> new UsernameNotFoundException("Seller not found!"));

        Date createdDate = DateUtil.getCurrentDate();

        ImmediateSaleListing immediateSaleListing =
                ImmediateSaleListing.builder()
                        .immediateSaleListingID(UUIDUtil.generateUUID())
                        .productName(listingRequest.getProductName())
                        .productDescription(listingRequest.getProductDescription())
                        .price(listingRequest.getProductPrice())
                        .seller(seller)
                        .sellerEmail(userName)
                        .category(listingRequest.getProductCategory())
                        .createdDate(createdDate)
                        .updatedDate(createdDate)
                        .build();

        List<ImmediateSaleImage> immediateSaleImages = new ArrayList<>();
        List<String> imageURLs = new ArrayList<>();


        for (int iter = 0; iter < productImages.size(); ++iter) {
            MultipartFile productImage = productImages.get(iter);
            String sellCategory = String.valueOf(listingRequest.getSellCategory());
            String listingID = immediateSaleListing.getImmediateSaleListingID();
            String fileExtension = FileUtils.getFileExtention(productImage);
            String uniqueFileName =
                    FileUtils.generateUniqueFileNameForImage(
                            sellCategory,
                            listingID,
                            (iter + 1),
                            fileExtension);
            int returnCode =
                    uploadSingleImage(
                            productImage,
                            uniqueFileName); // image is uploaded to s3 bucket and returns an upload status code
            if (returnCode == HttpStatusCode.OK) {
                String imageURL = FileUtils.generateImageURL(bucketName, region, uniqueFileName);

                // Build Immediate sale Image
                ImmediateSaleImage immediateSaleImage =
                        ImmediateSaleImage.builder()
                                .immediateSaleListingID(immediateSaleListing.getImmediateSaleListingID())
                                .imageURL(imageURL)
                                .createdDate(createdDate)
                                .build();
                immediateSaleImages.add(immediateSaleImage);
                imageURLs.add(imageURL);
            }
        }

        // Save Listing and image to database
        immediateListingRepository.save(immediateSaleListing);
        immediateImageRepository.saveAll(immediateSaleImages);

        return ImmediateSaleListingCreationResponse.builder()
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


    /**
     * Post request to create a immediate sale listing.
     *
     * @param authorizationHeader The authorization header containing the JWT of user.
     * @param listingRequest has the listing details
     * @param productImages has the byte data of uploaded images
     * @return A list of {@code AuctionSaleListingCreationResponse}  containing the
     * details of listed product
     */
    @Override
    public AuctionSaleListingCreationResponse createAuctionSaleListing(
            String authorizationHeader, SubmitListingRequest listingRequest, List<MultipartFile> productImages) {
        String userName = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);

        Date createdDate = DateUtil.getCurrentDate();
        Date auctionSlot = DateUtil.getDateFromString(listingRequest.getAuctionSlot());

        AuctionSaleListing auctionSaleListing =
                AuctionSaleListing.builder()
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


        for (int iter = 0; iter < productImages.size(); ++iter) {
            MultipartFile productImage = productImages.get(iter);
            String sellCategory = String.valueOf(listingRequest.getSellCategory());
            String listingID = auctionSaleListing.getAuctionSaleListingID();
            String fileExtension = FileUtils.getFileExtention(productImage);
            String uniqueFileName =
                    FileUtils.generateUniqueFileNameForImage(
                            sellCategory,
                            listingID,
                            (iter + 1),
                            fileExtension);
            int returnCode = uploadSingleImage(productImage, uniqueFileName);
            if (returnCode == HttpStatusCode.OK) {
                String imageURL = FileUtils.generateImageURL(bucketName, region, uniqueFileName);

                AuctionSaleImage auctionSaleImage =
                        AuctionSaleImage.builder()
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

        auctionListingRepository.save(auctionSaleListing);
        auctionImageRepository.saveAll(auctionSaleImages);

        return AuctionSaleListingCreationResponse.builder()
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
        return findProductByID(immediateSaleListingID);
    }


    /* Get all product listing for admin */
    @Override
    public List<ImmediateSaleMinifiedResponse> findAllImmediateSaleListing() {
        return ProductMapper.minifiedProductResponse(immediateListingRepository.findAll());
    }

    @Override
    public List<ImmediateSaleListing> findAllImmediateSaleListingBySellerEmail(
            String authorizationHeader) {
        String sellerEmail = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);
        return immediateListingRepository.findAllBySellerEmail(sellerEmail);
    }

    @Override
    public List<AuctionSaleListing> findAllAuctionSaleListingBySellerEmail(
            String authorizationHeader) {
        String sellerEmail = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);
        return auctionListingRepository.findAllBySellerEmail(sellerEmail);
    }

    @Override
    public GetListingImageResponse findAllImmediateSaleListingImagesByID(String listingID) {
        List<ImmediateSaleImage> productImages =
                immediateImageRepository.getAllByImmediateSaleListingID(listingID);
        return GetListingImageResponse.builder()
                .listingId(listingID)
                .imageURLs(productImages.stream().map(ImmediateSaleImage::getImageURL).toList())
                .build();
    }

    @Override
    public GetListingImageResponse findAllAuctionSaleListingImagesByID(String listingID) {
        List<AuctionSaleImage> productImages =
                auctionImageRepository.findAllByAuctionSaleListingID(listingID);
        return GetListingImageResponse.builder()
                .listingId(listingID)
                .imageURLs(productImages.stream().map(AuctionSaleImage::getImageURL).toList())
                .build();
    }

    /* Get all product listing from seller id */
    @Override
    public List<ImmediateSaleListing> findUserListingById(Integer sellerID) {
        return immediateListingRepository.findAllBySellerID(sellerID);
    }

  /**
   * Fetches all listings that are marked as approved for immediate sale.
   *
   * @return A List of {@code ApprovedImmediateSaleListingForAdminResponse} objects, each
   *     representing an approved immediate sale listing.
   */
    @Override
    public List<ApprovedImmediateSaleListingForAdminResponse> findAllApprovedImmediateSaleListing() {

        List<ImmediateSaleListing> immediateSaleListings =
                immediateListingRepository.findAllApprovedImmediateSaleListing();
        List<ApprovedImmediateSaleListingForAdminResponse>
                adminResponseList = new ArrayList<>();

        for (ImmediateSaleListing immediateSale : immediateSaleListings) {
            ApprovedImmediateSaleListingForAdminResponse response =
                    new ApprovedImmediateSaleListingForAdminResponse();

            response.setImmediateSaleListingID(
                    immediateSale.getImmediateSaleListingID());
            response.setProductName(immediateSale.getProductName());
            response.setProductDescription(
                    immediateSale.getProductDescription());
            response.setPrice(immediateSale.getPrice());
            response.setCategory(immediateSale.getCategory());
            response.setSellerEmail(immediateSale.getSellerEmail());
            String listingID = immediateSale.getImmediateSaleListingID();
            List<ImmediateSaleImage> productImages =
                    immediateImageRepository.getAllByImmediateSaleListingID(listingID);
            response.setImageURLs(
                    productImages.stream().map(ImmediateSaleImage::getImageURL).toList());
            response.setActive(immediateSale.isActive());
            response.setApproved(immediateSale.isApproved());
            response.setApproverEmail(
                    immediateSale.getApproverEmail());
            response.setMessageFromApprover(
                    immediateSale.getMessageFromApprover());
            response.setDateOfApproval(
                    immediateSale.getDateOfApproval());
            response.setSold(immediateSale.isSold());

            adminResponseList.add(
                    response);
        }
        return adminResponseList;
    }

  /**
   * Fetches all listings that are marked as rejected for immediate sale.
   *
   * @return A List of {@code DeniedImmediateSaleListingForAdminResponse} objects, each representing
   *     a rejected immediate sale listing.
   */
    @Override
    public List<DeniedImmediateSaleListingForAdminResponse> findAllDeniedImmediateSaleListing() {

        List<ImmediateSaleListing> immediateSaleListings =
                immediateListingRepository.findAllDeniedImmediateSaleListing();
        List<DeniedImmediateSaleListingForAdminResponse>
                adminResponseList = new ArrayList<>();

        for (ImmediateSaleListing immediateSale : immediateSaleListings) {
            DeniedImmediateSaleListingForAdminResponse response =
                    new DeniedImmediateSaleListingForAdminResponse();

            response.setImmediateSaleListingID(
                    immediateSale.getImmediateSaleListingID());
            response.setProductName(immediateSale.getProductName());
            response.setProductDescription(
                    immediateSale.getProductDescription());
            response.setPrice(immediateSale.getPrice());
            response.setCategory(immediateSale.getCategory());
            response.setSellerEmail(immediateSale.getSellerEmail());
            String listingID = immediateSale.getImmediateSaleListingID();
            List<ImmediateSaleImage> productImages =
                    immediateImageRepository.getAllByImmediateSaleListingID(listingID);
            response.setImageURLs(
                    productImages.stream().map(ImmediateSaleImage::getImageURL).toList());
            response.setActive(immediateSale.isActive());
            response.setRejected(immediateSale.isRejected());
            response.setApproverEmail(immediateSale.getApproverEmail());
            response.setMessageFromApprover(
                    immediateSale.getMessageFromApprover());
            response.setDateOfApproval(
                    immediateSale.getDateOfApproval());
            response.setSold(immediateSale.isSold());

            adminResponseList.add(
                    response);
        }
        return adminResponseList;
    }

  /**
   * Fetches all listings that are marked as approved for auction sale.
   *
   * @return A List of {@code ApprovedAuctionSaleListingForAdminResponse} objects, each representing
   *     an approved auction sale listing.
   */
    @Override
    public List<ApprovedAuctionSaleListingForAdminResponse> findAllApprovedAuctionSaleListing() {

        List<AuctionSaleListing> auctionSaleListings =
                auctionListingRepository.findAllApprovedAuctionSaleListing();
        List<ApprovedAuctionSaleListingForAdminResponse>
                adminResponseList = new ArrayList<>();

        for (AuctionSaleListing auctionSale : auctionSaleListings) {
            ApprovedAuctionSaleListingForAdminResponse response =
                    new ApprovedAuctionSaleListingForAdminResponse();

            response.setAuctionSaleListingID(
                    auctionSale.getAuctionSaleListingID());
            response.setProductName(auctionSale.getProductName());
            response.setProductDescription(
                    auctionSale.getProductDescription());
            response.setStartingBid(auctionSale.getStartingBid());
            response.setCategory(auctionSale.getCategory());
            response.setSellerEmail(auctionSale.getSellerEmail());
            String listingID = auctionSale.getAuctionSaleListingID();
            List<AuctionSaleImage> productImages =
                    auctionImageRepository.findAllByAuctionSaleListingID(listingID);
            response.setImageURLs(
                    productImages.stream().map(AuctionSaleImage::getImageURL).toList());
            response.setActive(auctionSale.isActive());
            response.setApproved(auctionSale.isApproved());
            response.setApproverEmail(auctionSale.getApproverEmail());
            response.setMessageFromApprover(
                    auctionSale.getMessageFromApprover());
            response.setDateOfApproval(auctionSale.getDateOfApproval());
            response.setSold(auctionSale.isSold());

            adminResponseList.add(
                    response);
        }
        return adminResponseList;
    }

  /**
   * Fetches all listings that are marked as rejected for auction sale.
   *
   * @return A List of {@code DeniedAuctionSaleListingForAdminResponse} objects, each representing a
   *     rejected auction sale listing.
   */
    @Override
    public List<DeniedAuctionSaleListingForAdminResponse> findAllDeniedAuctionSaleListing() {

        List<AuctionSaleListing> auctionSaleListings =
                auctionListingRepository.findAllDeniedAuctionSaleListing();
        List<DeniedAuctionSaleListingForAdminResponse> adminResponseList =
                new ArrayList<>();

        for (AuctionSaleListing auctionSale : auctionSaleListings) {
            DeniedAuctionSaleListingForAdminResponse response =
                    new DeniedAuctionSaleListingForAdminResponse();

            response.setAuctionSaleListingID(
                    auctionSale.getAuctionSaleListingID());
            response.setProductName(auctionSale.getProductName());
            response.setProductDescription(
                    auctionSale.getProductDescription());
            response.setStartingBid(auctionSale.getStartingBid());
            response.setCategory(auctionSale.getCategory());
            response.setSellerEmail(auctionSale.getSellerEmail());
            String listingID = auctionSale.getAuctionSaleListingID();
            List<AuctionSaleImage> productImages =
                    auctionImageRepository.findAllByAuctionSaleListingID(listingID);
            response.setImageURLs(
                    productImages.stream().map(AuctionSaleImage::getImageURL).toList());
            response.setActive(auctionSale.isActive());
            response.setRejected(auctionSale.isRejected());
            response.setApproverEmail(auctionSale.getApproverEmail());
            response.setMessageFromApprover(
                    auctionSale.getMessageFromApprover());
            response.setDateOfApproval(auctionSale.getDateOfApproval());
            response.setSold(auctionSale.isSold());

            adminResponseList.add(response);
        }
        return adminResponseList;
    }

    /**
     * Finds and retrieves details of an auction sale product by its listing ID.
     *
     * @param auctionSaleListingID The id of the auction sale listing.
     * @return n {@code AuctionSaleProductResponse} object containing information of the auction sale
     * product.
     */
    @Override
    public AuctionSaleProductResponse findAuctionSaleProductDetailsById(String auctionSaleListingID) {
        AuctionSaleListing auctionSaleListing =
                auctionListingRepository.findAuctionSaleProductByID(auctionSaleListingID);

        if (auctionSaleListing == null) {
            String error = String.format("No listing with id:%s", auctionSaleListingID);
            throw new ListingNotFoundException(error);
        }

        List<AuctionSaleImage> productImages =
                auctionImageRepository.findAllByAuctionSaleListingID(auctionSaleListingID);

        String userMail = auctionSaleListing.getSellerEmail();
        User user = findUserByMail(userMail);

        User sellerRequiredInfo = new User();
        sellerRequiredInfo.setUserID(user.getUserID());
        sellerRequiredInfo.setFirstName(user.getFirstName());
        sellerRequiredInfo.setLastName(user.getLastName());
        sellerRequiredInfo.setEmail(user.getEmail());
        sellerRequiredInfo.setRole(user.getRole());
        sellerRequiredInfo.setAvgBuyerRatings(user.getAvgBuyerRatings());
        sellerRequiredInfo.setAvgSellerRatings(user.getAvgSellerRatings());

        return AuctionSaleProductResponse.builder()
                .auctionSaleListingID(auctionSaleListing.getAuctionSaleListingID())
                .productName(auctionSaleListing.getProductName())
                .productDescription(auctionSaleListing.getProductDescription())
                .startingBid(auctionSaleListing.getStartingBid())
                .highestBid(auctionSaleListing.getHighestBid())
                .imageURLs(productImages.stream().map(AuctionSaleImage::getImageURL).toList())
                .seller(sellerRequiredInfo)
                .build();
    }

    /**
     * Retrieves all immediate sale listings with its seller details.
     *
     * @param authorizationHeader The authorization header containing the JWT of user.
     * @return A list of {@code ImmediateSaleListingCreationResponse} objects representing all
     * immediate sale listings details.
     */
    @Override
    public List<ImmediateSaleListingCreationResponse> findAllImmediateListing(
            String authorizationHeader) {

        String sellerEmail = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);
        List<ImmediateSaleListing> listings =
                immediateListingRepository.findAllImmediateSaleListing(sellerEmail);
        List<ImmediateSaleListingCreationResponse> responseList =
                new ArrayList<>();

        for (ImmediateSaleListing immediateSaleListing : listings) {
            String listingID = immediateSaleListing.getImmediateSaleListingID();
            List<ImmediateSaleImage> productImages =
                    immediateImageRepository.getAllByImmediateSaleListingID(listingID);

            String userMail = immediateSaleListing.getSellerEmail();
            User seller = findUserByMail(userMail);

            responseList.add(
                    ImmediateSaleListingCreationResponse.builder()
                            .immediateSaleListingID(immediateSaleListing.getImmediateSaleListingID())
                            .productName(immediateSaleListing.getProductName())
                            .productDescription(immediateSaleListing.getProductDescription())
                            .price(immediateSaleListing.getPrice())
                            .category(immediateSaleListing.getCategory())
                            .sellerEmail(seller.getEmail())
                            .imageURLs(productImages.stream().map(ImmediateSaleImage::getImageURL).toList())
                            .active(immediateSaleListing.isActive())
                            .isApproved(immediateSaleListing.isApproved())
                            .isRejected(immediateSaleListing.isRejected())
                            .createdDate(immediateSaleListing.getCreatedDate())
                            .sellerID(seller.getUserID())
                            .build());
        }
        return responseList;
    }

    /**
     * Retrieves all auction sale listings with its seller details.
     *
     * @param authorizationHeader The authorization header containing the JWT of user.
     * @return A list of {@code AuctionSaleListingCreationResponse} objects representing all
     * auction sale listings details.
     */
    @Override
    public List<AuctionSaleListingCreationResponse> findAllAuctionListing(
            String authorizationHeader) {

        String sellerEmail = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);
        Date currentDate = DateUtil.getCurrentDate();
        List<AuctionSaleListing> listings =
                auctionListingRepository.findAllAuctionSaleListing(sellerEmail, currentDate);

        List<AuctionSaleListingCreationResponse> responseList =
                new ArrayList<>();

        for (AuctionSaleListing auctionSaleListing : listings) {
            String listingID = auctionSaleListing.getAuctionSaleListingID();
            List<AuctionSaleImage> productImages =
                    auctionImageRepository.findAllByAuctionSaleListingID(listingID);

            String userMail = auctionSaleListing.getSellerEmail();
            User seller = findUserByMail(userMail);

            Optional<User> currentHighestBidUser;
            User highestBidUserRequiredInfo = new User();
            highestBidUserRequiredInfo.setRole(RoleEnum.USER);
            if(auctionSaleListing.getCurrentHighestBidUserMail() != null && !auctionSaleListing.getCurrentHighestBidUserMail().isEmpty())
            {
                currentHighestBidUser = userRepository.findByEmail(auctionSaleListing.getCurrentHighestBidUserMail());
                if(currentHighestBidUser.isPresent())
                {
                    highestBidUserRequiredInfo.setUserID(currentHighestBidUser.get().getUserID());
                    highestBidUserRequiredInfo.setFirstName(currentHighestBidUser.get().getFirstName());
                    highestBidUserRequiredInfo.setLastName(currentHighestBidUser.get().getLastName());
                    highestBidUserRequiredInfo.setEmail(currentHighestBidUser.get().getEmail());
                    highestBidUserRequiredInfo.setRole(currentHighestBidUser.get().getRole());
                    highestBidUserRequiredInfo.setAvgBuyerRatings(currentHighestBidUser.get().getAvgBuyerRatings());
                    highestBidUserRequiredInfo.setAvgSellerRatings(currentHighestBidUser.get().getAvgSellerRatings());
                }
            }

            responseList.add(
                    AuctionSaleListingCreationResponse.builder()
                            .auctionSaleListingID(auctionSaleListing.getAuctionSaleListingID())
                            .productName(auctionSaleListing.getProductName())
                            .productDescription(auctionSaleListing.getProductDescription())
                            .startingBid(auctionSaleListing.getStartingBid())
                            .category(auctionSaleListing.getCategory())
                            .sellerEmail(seller.getEmail())
                            .imageURLs(productImages.stream().map(AuctionSaleImage::getImageURL).toList())
                            .active(auctionSaleListing.isActive())
                            .isApproved(auctionSaleListing.isApproved())
                            .isRejected(auctionSaleListing.isRejected())
                            .createdDate(auctionSaleListing.getCreatedDate())
                            .sellerID(seller.getUserID())
                            .highestBid(auctionSaleListing.getHighestBid())
                            .currentHighestBidUserMail(auctionSaleListing.getCurrentHighestBidUserMail())
                            .highestBidUser(highestBidUserRequiredInfo)
                            .build());
        }
        return responseList;
    }

    /**
     * Retrieves all auction sale listings with its seller details for admin.
     *
     * @return A list of {@code AuctionSaleListingCreationResponse} objects representing all
     * auction sale listings details.
     */
    @Override
    public List<AuctionSaleListingCreationResponse> findAllAuctionListingForAdmin() {

        List<AuctionSaleListing> auctionSaleListingList =
                auctionListingRepository.findAllAuctionSaleListingForAdmin();

        List<AuctionSaleListingCreationResponse> responseList =
                new ArrayList<>();

        for (AuctionSaleListing auctionSaleListing : auctionSaleListingList) {
            String listingID = auctionSaleListing.getAuctionSaleListingID();
            List<AuctionSaleImage> productImages =
                    auctionImageRepository.findAllByAuctionSaleListingID(listingID);

            String userMail = auctionSaleListing.getSellerEmail();
            User seller = findUserByMail(userMail);

            responseList.add(
                    AuctionSaleListingCreationResponse.builder()
                            .auctionSaleListingID(auctionSaleListing.getAuctionSaleListingID())
                            .productName(auctionSaleListing.getProductName())
                            .productDescription(auctionSaleListing.getProductDescription())
                            .startingBid(auctionSaleListing.getStartingBid())
                            .category(auctionSaleListing.getCategory())
                            .sellerEmail(seller.getEmail())
                            .imageURLs(productImages.stream().map(AuctionSaleImage::getImageURL).toList())
                            .active(auctionSaleListing.isActive())
                            .isApproved(auctionSaleListing.isApproved())
                            .isRejected(auctionSaleListing.isRejected())
                            .createdDate(auctionSaleListing.getCreatedDate())
                            .sellerID(seller.getUserID())
                            .build());
        }
        return responseList;
    }

    /**
     * Retrieves Auction sale listing by auctionSaleListingID.
     *
     * @return A list of {@code AuctionSaleListing} object representing auction sale listing details.
     */
    @Override
    public AuctionSaleProductResponse findAuctionListingByID(String auctionSaleListingID) {
        AuctionSaleListing listing =
            auctionListingRepository.findByAuctionSaleListingID(auctionSaleListingID);

        if (listing == null) {
          throw new ListingNotFoundException(ErrorConstant.PRODUCT_NOT_FOUND);
        }

        List<AuctionSaleImage> productImages =
                auctionImageRepository.findAllByAuctionSaleListingID(auctionSaleListingID);

        return AuctionSaleProductResponse.builder()
                .auctionSaleListingID(listing.getAuctionSaleListingID())
                .productName(listing.getProductName())
                .productDescription(listing.getProductDescription())
                .startingBid(listing.getStartingBid())
                .active(listing.isActive())
                .isApproved(listing.isApproved())
                .isRejected(listing.isRejected())
                .highestBid(listing.getHighestBid())
                .imageURLs(productImages.stream().map(AuctionSaleImage::getImageURL).toList())
                .seller(findUserByMail(listing.getSellerEmail()))
                .build();
    }

    // Find the product details for provided product id or throw Product not found exception
    private ImmediateSaleListing findProductByID(String id) {
        String error = ErrorConstant.PRODUCT_NOT_FOUND;
        return immediateListingRepository
                .findById(id)
                .orElseThrow(() -> new ListingNotFoundException(error));
    }

    // Find the user details for provided mail id or throw user not found exception
    private User findUserByMail(String mail) {
        return userRepository
                .findByEmail(mail)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorConstant.USER_NOT_FOUND));
    }

    // Image file and filename as input and the image is uploaded to s3 bucket
    private int uploadSingleImage(MultipartFile productImage, String uniqueFileName) {
        if (FileUtils.isImageFile(productImage)) {
            return awsS3Service.uploadImageToBucket(uniqueFileName, productImage);
        } else {
            throw new ImageUploadException("Error while Uploading image, Please try again later");
        }
    }
}
