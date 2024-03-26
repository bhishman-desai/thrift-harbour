package tech.group15.thriftharbour.service.implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.http.HttpStatusCode;
import tech.group15.thriftharbour.dto.request.SubmitListingRequest;
import tech.group15.thriftharbour.dto.response.*;
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

  private final ImmediateSaleListingRepository immediateSaleListingRepository;

  private final ImmediateSaleImageRepository immediateSaleImageRepository;

  private final AuctionSaleListingRepository auctionSaleListingRepository;

  private final AuctionSaleImageRepository auctionSaleImageRepository;

  private final UserRepository userRepository;

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
  public ImmediateSaleListingCreationResponse createImmediateSaleListing(
      String authorizationHeader, SubmitListingRequest listingRequest, List<MultipartFile> images) {
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

    List<MultipartFile> productImages = images;
    for (int iter = 0; iter < productImages.size(); ++iter) {
      MultipartFile productImage = productImages.get(iter);
      String uniqueFileName =
          FileUtils.generateUniqueFileNameForImage(
              String.valueOf(listingRequest.getSellCategory()),
              immediateSaleListing.getImmediateSaleListingID(),
              (iter + 1),
              FileUtils.getFileExtention(productImage));
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
    immediateSaleListingRepository.save(immediateSaleListing);
    immediateSaleImageRepository.saveAll(immediateSaleImages);

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

  @Override
  public AuctionSaleListingCreationResponse createAuctionSaleListing(
      String authorizationHeader, SubmitListingRequest listingRequest, List<MultipartFile> images) {
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

    List<MultipartFile> productImages = images;
    for (int iter = 0; iter < productImages.size(); ++iter) {
      MultipartFile productImage = productImages.get(iter);
      String uniqueFileName =
          FileUtils.generateUniqueFileNameForImage(
              String.valueOf(listingRequest.getSellCategory()),
              auctionSaleListing.getAuctionSaleListingID(),
              (iter + 1),
              FileUtils.getFileExtention(productImage));
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

    auctionSaleListingRepository.save(auctionSaleListing);
    auctionSaleImageRepository.saveAll(auctionSaleImages);

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
  public List<ImmediateSaleListing> findAllImmediateSaleListingBySellerEmail(
      String authorizationHeader) {
    String sellerEmail = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);
    return immediateSaleListingRepository.findAllBySellerEmail(sellerEmail);
  }

  @Override
  public List<AuctionSaleListing> findAllAuctionSaleListingBySellerEmail(
      String authorizationHeader) {
    String sellerEmail = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);
    return auctionSaleListingRepository.findAllBySellerEmail(sellerEmail);
  }

  @Override
  public GetListingImageResponse findAllImmediateSaleListingImagesByID(String listingID) {
    List<ImmediateSaleImage> productImages =
        immediateSaleImageRepository.getAllByImmediateSaleListingID(listingID);
    return GetListingImageResponse.builder()
        .listingId(listingID)
        .imageURLs(productImages.stream().map(ImmediateSaleImage::getImageURL).toList())
        .build();
  }

  @Override
  public GetListingImageResponse findAllAuctionSaleListingImagesByID(String listingID) {
    List<AuctionSaleImage> productImages =
        auctionSaleImageRepository.findAllByAuctionSaleListingID(listingID);
    return GetListingImageResponse.builder()
        .listingId(listingID)
        .imageURLs(productImages.stream().map(AuctionSaleImage::getImageURL).toList())
        .build();
  }

  /* Get all product listing from seller id */
  @Override
  public List<ImmediateSaleListing> findUserListingById(Integer sellerID) {
    return immediateSaleListingRepository.findAllBySellerID(sellerID);
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
        immediateSaleListingRepository.findAllApprovedImmediateSaleListing();
    List<ApprovedImmediateSaleListingForAdminResponse>
        approvedImmediateSaleListingForAdminResponseList = new ArrayList<>();

    for (ImmediateSaleListing immediateSale : immediateSaleListings) {
      ApprovedImmediateSaleListingForAdminResponse approvedImmediateSaleListingForAdminResponse =
          new ApprovedImmediateSaleListingForAdminResponse();

      approvedImmediateSaleListingForAdminResponse.setImmediateSaleListingID(
          immediateSale.getImmediateSaleListingID());
      approvedImmediateSaleListingForAdminResponse.setProductName(immediateSale.getProductName());
      approvedImmediateSaleListingForAdminResponse.setProductDescription(
          immediateSale.getProductDescription());
      approvedImmediateSaleListingForAdminResponse.setPrice(immediateSale.getPrice());
      approvedImmediateSaleListingForAdminResponse.setCategory(immediateSale.getCategory());
      approvedImmediateSaleListingForAdminResponse.setSellerEmail(immediateSale.getSellerEmail());
      List<ImmediateSaleImage> productImages =
          immediateSaleImageRepository.getAllByImmediateSaleListingID(
              immediateSale.getImmediateSaleListingID());
      approvedImmediateSaleListingForAdminResponse.setImageURLs(
          productImages.stream().map(ImmediateSaleImage::getImageURL).toList());
      approvedImmediateSaleListingForAdminResponse.setActive(immediateSale.isActive());
      approvedImmediateSaleListingForAdminResponse.setApproved(immediateSale.isApproved());
      approvedImmediateSaleListingForAdminResponse.setApproverEmail(
          immediateSale.getApproverEmail());
      approvedImmediateSaleListingForAdminResponse.setMessageFromApprover(
          immediateSale.getMessageFromApprover());
      approvedImmediateSaleListingForAdminResponse.setDateOfApproval(
          immediateSale.getDateOfApproval());
      approvedImmediateSaleListingForAdminResponse.setSold(immediateSale.isSold());

      approvedImmediateSaleListingForAdminResponseList.add(
          approvedImmediateSaleListingForAdminResponse);
    }
    return approvedImmediateSaleListingForAdminResponseList;
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
        immediateSaleListingRepository.findAllDeniedImmediateSaleListing();
    List<DeniedImmediateSaleListingForAdminResponse>
        deniedImmediateSaleListingForAdminResponseList = new ArrayList<>();

    for (ImmediateSaleListing immediateSale : immediateSaleListings) {
      DeniedImmediateSaleListingForAdminResponse deniedImmediateSaleListingForAdminResponse =
          new DeniedImmediateSaleListingForAdminResponse();

      deniedImmediateSaleListingForAdminResponse.setImmediateSaleListingID(
          immediateSale.getImmediateSaleListingID());
      deniedImmediateSaleListingForAdminResponse.setProductName(immediateSale.getProductName());
      deniedImmediateSaleListingForAdminResponse.setProductDescription(
          immediateSale.getProductDescription());
      deniedImmediateSaleListingForAdminResponse.setPrice(immediateSale.getPrice());
      deniedImmediateSaleListingForAdminResponse.setCategory(immediateSale.getCategory());
      deniedImmediateSaleListingForAdminResponse.setSellerEmail(immediateSale.getSellerEmail());
      List<ImmediateSaleImage> productImages =
          immediateSaleImageRepository.getAllByImmediateSaleListingID(
              immediateSale.getImmediateSaleListingID());
      deniedImmediateSaleListingForAdminResponse.setImageURLs(
          productImages.stream().map(ImmediateSaleImage::getImageURL).toList());
      deniedImmediateSaleListingForAdminResponse.setActive(immediateSale.isActive());
      deniedImmediateSaleListingForAdminResponse.setRejected(immediateSale.isRejected());
      deniedImmediateSaleListingForAdminResponse.setApproverEmail(immediateSale.getApproverEmail());
      deniedImmediateSaleListingForAdminResponse.setMessageFromApprover(
          immediateSale.getMessageFromApprover());
      deniedImmediateSaleListingForAdminResponse.setDateOfApproval(
          immediateSale.getDateOfApproval());
      deniedImmediateSaleListingForAdminResponse.setSold(immediateSale.isSold());

      deniedImmediateSaleListingForAdminResponseList.add(
          deniedImmediateSaleListingForAdminResponse);
    }
    return deniedImmediateSaleListingForAdminResponseList;
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
        auctionSaleListingRepository.findAllApprovedAuctionSaleListing();
    List<ApprovedAuctionSaleListingForAdminResponse>
        approvedAuctionSaleListingForAdminResponseList = new ArrayList<>();

    for (AuctionSaleListing auctionSale : auctionSaleListings) {
      ApprovedAuctionSaleListingForAdminResponse approvedAuctionSaleListingForAdminResponse =
          new ApprovedAuctionSaleListingForAdminResponse();

      approvedAuctionSaleListingForAdminResponse.setAuctionSaleListingID(
          auctionSale.getAuctionSaleListingID());
      approvedAuctionSaleListingForAdminResponse.setProductName(auctionSale.getProductName());
      approvedAuctionSaleListingForAdminResponse.setProductDescription(
          auctionSale.getProductDescription());
      approvedAuctionSaleListingForAdminResponse.setCategory(auctionSale.getCategory());
      approvedAuctionSaleListingForAdminResponse.setSellerEmail(auctionSale.getSellerEmail());
      List<AuctionSaleImage> productImages =
          auctionSaleImageRepository.findAllByAuctionSaleListingID(
              auctionSale.getAuctionSaleListingID());
      approvedAuctionSaleListingForAdminResponse.setImageURLs(
          productImages.stream().map(AuctionSaleImage::getImageURL).toList());
      approvedAuctionSaleListingForAdminResponse.setActive(auctionSale.isActive());
      approvedAuctionSaleListingForAdminResponse.setApproved(auctionSale.isApproved());
      approvedAuctionSaleListingForAdminResponse.setApproverEmail(auctionSale.getApproverEmail());
      approvedAuctionSaleListingForAdminResponse.setMessageFromApprover(
          auctionSale.getMessageFromApprover());
      approvedAuctionSaleListingForAdminResponse.setDateOfApproval(auctionSale.getDateOfApproval());
      approvedAuctionSaleListingForAdminResponse.setSold(auctionSale.isSold());

      approvedAuctionSaleListingForAdminResponseList.add(
          approvedAuctionSaleListingForAdminResponse);
    }
    return approvedAuctionSaleListingForAdminResponseList;
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
        auctionSaleListingRepository.findAllDeniedAuctionSaleListing();
    List<DeniedAuctionSaleListingForAdminResponse> deniedAuctionSaleListingForAdminResponseList =
        new ArrayList<>();

    for (AuctionSaleListing auctionSale : auctionSaleListings) {
      DeniedAuctionSaleListingForAdminResponse deniedAuctionSaleListingForAdminResponse =
          new DeniedAuctionSaleListingForAdminResponse();

      deniedAuctionSaleListingForAdminResponse.setAuctionSaleListingID(
          auctionSale.getAuctionSaleListingID());
      deniedAuctionSaleListingForAdminResponse.setProductName(auctionSale.getProductName());
      deniedAuctionSaleListingForAdminResponse.setProductDescription(
          auctionSale.getProductDescription());
      deniedAuctionSaleListingForAdminResponse.setCategory(auctionSale.getCategory());
      deniedAuctionSaleListingForAdminResponse.setSellerEmail(auctionSale.getSellerEmail());
      List<AuctionSaleImage> productImages =
          auctionSaleImageRepository.findAllByAuctionSaleListingID(
              auctionSale.getAuctionSaleListingID());
      deniedAuctionSaleListingForAdminResponse.setImageURLs(
          productImages.stream().map(AuctionSaleImage::getImageURL).toList());
      deniedAuctionSaleListingForAdminResponse.setActive(auctionSale.isActive());
      deniedAuctionSaleListingForAdminResponse.setRejected(auctionSale.isRejected());
      deniedAuctionSaleListingForAdminResponse.setApproverEmail(auctionSale.getApproverEmail());
      deniedAuctionSaleListingForAdminResponse.setMessageFromApprover(
          auctionSale.getMessageFromApprover());
      deniedAuctionSaleListingForAdminResponse.setDateOfApproval(auctionSale.getDateOfApproval());
      deniedAuctionSaleListingForAdminResponse.setSold(auctionSale.isSold());

      deniedAuctionSaleListingForAdminResponseList.add(deniedAuctionSaleListingForAdminResponse);
    }
    return deniedAuctionSaleListingForAdminResponseList;
  }

  /**
   * Finds and retrieves details of an auction sale product by its listing ID.
   *
   * @param auctionSaleListingID The id of the auction sale listing.
   * @return n {@code AuctionSaleProductResponse} object containing information of the auction sale
   *     product.
   */
  @Override
  public AuctionSaleProductResponse findAuctionSaleProductDetailsById(String auctionSaleListingID) {
    AuctionSaleListing auctionSaleListing =
        auctionSaleListingRepository.findAuctionSaleProductByID(auctionSaleListingID);

    if (auctionSaleListing == null)
      throw new ListingNotFoundException(
          String.format("No listing found with provided listing id:%s", auctionSaleListingID));

    List<AuctionSaleImage> productImages =
        auctionSaleImageRepository.findAllByAuctionSaleListingID(auctionSaleListingID);

    User user =
        userRepository
            .findByEmail(auctionSaleListing.getSellerEmail())
            .orElseThrow(() -> new UsernameNotFoundException("Seller not found!"));

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
   *     immediate sale listings details.
   */
  @Override
  public List<ImmediateSaleListingCreationResponse> findAllImmediateListing(
      String authorizationHeader) {

    String sellerEmail = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);
    List<ImmediateSaleListing> immediateSaleListingList =
        immediateSaleListingRepository.findAllImmediateSaleListing(sellerEmail);
    List<ImmediateSaleListingCreationResponse> immediateSaleListingCreationResponsesList =
        new ArrayList<>();

    for (ImmediateSaleListing immediateSaleListing : immediateSaleListingList) {
      List<ImmediateSaleImage> productImages =
          immediateSaleImageRepository.getAllByImmediateSaleListingID(
              immediateSaleListing.getImmediateSaleListingID());

      User seller =
          userRepository
              .findByEmail(immediateSaleListing.getSellerEmail())
              .orElseThrow(() -> new UsernameNotFoundException("Seller not found!"));

      immediateSaleListingCreationResponsesList.add(
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
    return immediateSaleListingCreationResponsesList;
  }

  /**
   * Retrieves all auction sale listings with its seller details.
   *
   * @param authorizationHeader The authorization header containing the JWT of user.
   * @return A list of {@code AuctionSaleListingCreationResponse} objects representing all
   *     auction sale listings details.
   */
  @Override
  public List<AuctionSaleListingCreationResponse> findAllAuctionListing(
      String authorizationHeader) {

    String sellerEmail = jwtService.extractUserNameFromRequestHeaders(authorizationHeader);
    List<AuctionSaleListing> auctionSaleListingList =
        auctionSaleListingRepository.findAllAuctionSaleListing(
            sellerEmail, DateUtil.getCurrentDate());

    List<AuctionSaleListingCreationResponse> auctionSaleListingCreationResponseList =
        new ArrayList<>();

    for (AuctionSaleListing auctionSaleListing : auctionSaleListingList) {
      List<AuctionSaleImage> productImages =
          auctionSaleImageRepository.findAllByAuctionSaleListingID(
              auctionSaleListing.getAuctionSaleListingID());

      User seller =
          userRepository
              .findByEmail(auctionSaleListing.getSellerEmail())
              .orElseThrow(() -> new UsernameNotFoundException("Seller not found!"));

      User currentHighestBidUser =
              userRepository
                      .findByEmail(auctionSaleListing.getCurrentHighestBidUserMail())
                      .orElseThrow(() -> new UsernameNotFoundException("Highest bid user not found!"));

      User highestBidUserRequiredInfo = new User();
      highestBidUserRequiredInfo.setUserID(currentHighestBidUser.getUserID());
      highestBidUserRequiredInfo.setFirstName(currentHighestBidUser.getFirstName());
      highestBidUserRequiredInfo.setLastName(currentHighestBidUser.getLastName());
      highestBidUserRequiredInfo.setEmail(currentHighestBidUser.getEmail());
      highestBidUserRequiredInfo.setRole(currentHighestBidUser.getRole());
      highestBidUserRequiredInfo.setAvgBuyerRatings(currentHighestBidUser.getAvgBuyerRatings());
      highestBidUserRequiredInfo.setAvgSellerRatings(currentHighestBidUser.getAvgSellerRatings());

      auctionSaleListingCreationResponseList.add(
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
    return auctionSaleListingCreationResponseList;
  }

  /**
   * Retrieves all auction sale listings with its seller details for admin.
   *
   * @return A list of {@code AuctionSaleListingCreationResponse} objects representing all
   *     auction sale listings details.
   */
  @Override
  public List<AuctionSaleListingCreationResponse> findAllAuctionListingForAdmin() {

    List<AuctionSaleListing> auctionSaleListingList =
            auctionSaleListingRepository.findAllAuctionSaleListingForAdmin();

    List<AuctionSaleListingCreationResponse> auctionSaleListingCreationResponseList =
            new ArrayList<>();

    for (AuctionSaleListing auctionSaleListing : auctionSaleListingList) {
      List<AuctionSaleImage> productImages =
              auctionSaleImageRepository.findAllByAuctionSaleListingID(
                      auctionSaleListing.getAuctionSaleListingID());

      User seller =
              userRepository
                      .findByEmail(auctionSaleListing.getSellerEmail())
                      .orElseThrow(() -> new UsernameNotFoundException("Seller not found!"));

      auctionSaleListingCreationResponseList.add(
              AuctionSaleListingCreationResponse.builder()
                      .auctionSaleListingID(auctionSaleListing.getAuctionSaleListingID())
                      .productName(auctionSaleListing.getProductName())
                      .productDescription(auctionSaleListing.getProductDescription())
                      .category(auctionSaleListing.getCategory())
                      .sellerEmail(seller.getEmail())
                      .imageURLs(productImages.stream().map(AuctionSaleImage::getImageURL).toList())
                      .active(auctionSaleListing.isActive())
                      .createdDate(auctionSaleListing.getCreatedDate())
                      .sellerID(seller.getUserID())
                      .build());
    }
    return auctionSaleListingCreationResponseList;
  }
}
