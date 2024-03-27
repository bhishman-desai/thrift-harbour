package tech.group15.thriftharbour.service.implementation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.http.HttpStatusCode;
import tech.group15.thriftharbour.dto.request.SubmitListingRequest;
import tech.group15.thriftharbour.dto.response.*;
import tech.group15.thriftharbour.enums.RoleEnum;
import tech.group15.thriftharbour.enums.SellCategoryEnum;
import tech.group15.thriftharbour.exception.ListingNotFoundException;
import tech.group15.thriftharbour.model.*;
import tech.group15.thriftharbour.repository.*;
import tech.group15.thriftharbour.service.AwsS3Service;
import tech.group15.thriftharbour.service.JWTService;
import tech.group15.thriftharbour.utils.DateUtil;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
class ProductListingServiceImplTest {
    @Mock
    AuctionSaleListingRepository auctionSaleListingRepository;
    @Mock
    AuctionSaleImageRepository auctionSaleImageRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    JWTService jwtService;
    @Mock
    ImmediateSaleListingRepository immediateSaleListingRepository;
    @Mock
    ImmediateSaleImageRepository immediateSaleImageRepository;
    @Mock
    AwsS3Service awsS3Service;
    @InjectMocks
    ProductListingServiceImpl productListingServiceImpl;
    User user;
    AuctionSaleListing auctionSaleListing;
    AuctionSaleImage auctionSaleImage;
    ImmediateSaleListing immediateSaleListing;
    ImmediateSaleImage immediateSaleImage;
    String sellerEmail;
    String authorizationHeader;

    @BeforeEach
    void setUp() {
        user = new User(1, "firstName", "lastName", "email", "password", RoleEnum.USER, 4.0, 5.0);
        auctionSaleListing = new AuctionSaleListing("auctionSaleListingID", "productName", "productDescription", 0d, 0d, "currentHighestBidUserMail", "category", "sellerEmail", new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime(), true, true, true, "approverEmail", "messageFromApprover", true, new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime());
        auctionSaleImage = new AuctionSaleImage(0, "auctionSaleListingID", "imageURL", new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime());
        immediateSaleListing = new ImmediateSaleListing("immediateSaleListingID", "productName", "productDescription", 0d, "category", "sellerEmail", new User(0, "firstName", "lastName", "email", "password", RoleEnum.USER, 0d, 0d), true, true, true, "approverEmail", "messageFromApprover", true, new GregorianCalendar(2024, Calendar.MARCH, 14, 12, 35).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 14, 12, 35).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 14, 12, 35).getTime());
        immediateSaleImage = new ImmediateSaleImage(0, "immediateSaleListingID", "imageURL", new GregorianCalendar(2024, Calendar.MARCH, 14, 12, 35).getTime());
        sellerEmail = "sellerEmail";
        authorizationHeader = "authorizationHeader";
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the creation of Immediate sale listing by authorized user.
     */
    @Test
    void testCreateImmediateSaleListing(){
        MockMultipartFile mockFile1 = new MockMultipartFile("image1", "image1.png", "image/png", "test image content".getBytes());
        MockMultipartFile mockFile2 = new MockMultipartFile("image2", "image2.png", "image/png", "another test image content".getBytes());
        List<MultipartFile> productImages = Arrays.asList(mockFile1, mockFile2);

        when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn(sellerEmail);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(awsS3Service.uploadImageToBucket(anyString(), any())).thenReturn(2);
        when(awsS3Service.uploadImageToBucket(anyString(), any(MultipartFile.class))).thenReturn(HttpStatusCode.OK);

        ImmediateSaleListingCreationResponse result = productListingServiceImpl.createImmediateSaleListing(authorizationHeader, new SubmitListingRequest("productName", "productDescription", 0d, "category", SellCategoryEnum.DIRECT, "auctionSlot"), productImages);

        Assertions.assertNotNull(result);
    }

    /**
     * Tests the behaviour of creation of Immediate sale listing when seller not exists.
     */
    @Test
    void testCreateImmediateSaleListing_SellerNotFound() {
        when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn(sellerEmail);
        when(awsS3Service.uploadImageToBucket(anyString(), any())).thenReturn(2);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> productListingServiceImpl.createImmediateSaleListing(authorizationHeader, new SubmitListingRequest("productName", "productDescription", 0d, "category", SellCategoryEnum.DIRECT, "auctionSlot"), Collections.emptyList()));
    }

    /**
     * Tests the creation of Auction sale listing by authorized user.
     */
    @Test
    void testCreateAuctionSaleListing(){
        MockMultipartFile mockFile1 = new MockMultipartFile("image1", "image1.png", "image/png", "test image content".getBytes());
        MockMultipartFile mockFile2 = new MockMultipartFile("image2", "image2.png", "image/png", "another test image content".getBytes());
        List<MultipartFile> productImages = Arrays.asList(mockFile1, mockFile2);

        when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn(sellerEmail);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(awsS3Service.uploadImageToBucket(anyString(), any())).thenReturn(2);
        when(awsS3Service.uploadImageToBucket(anyString(), any(MultipartFile.class))).thenReturn(HttpStatusCode.OK);

        AuctionSaleListingCreationResponse result = productListingServiceImpl.createAuctionSaleListing(authorizationHeader, new SubmitListingRequest("productName", "productDescription", 0d, "category", SellCategoryEnum.AUCTION, "2024-03-10"), productImages);

        Assertions.assertNotNull(result);
    }

    /**
     * Tests the retrieval of Immediate sale listing by its listing id.
     */
    @Test
    void testFindImmediateSaleListingByID(){
        when(immediateSaleListingRepository.findById(any())).thenReturn(Optional.of(immediateSaleListing));

        ImmediateSaleListing result = productListingServiceImpl.findImmediateSaleListingByID("immediateSaleListingID");
        Assertions.assertEquals(immediateSaleListing, result);
        verify(immediateSaleListingRepository).findById("immediateSaleListingID");
    }

    /**
     * Tests the behaviour of retrieval of Immediate sale listing by its listing id when listing not found.
     */
    @Test
    void testFindImmediateSaleListingByID_ProductNotFound(){
        when(immediateSaleListingRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ListingNotFoundException.class, () -> productListingServiceImpl.findImmediateSaleListingByID("immediateSaleListingID"));
    }

    /**
     * Tests the retrieval of all Immediate sale listing.
     */
    @Test
    void testFindAllImmediateSaleListing(){
        when(immediateSaleListingRepository.findAll()).thenReturn(List.of(immediateSaleListing));

        List<ImmediateSaleMinifiedResponse> result = productListingServiceImpl.findAllImmediateSaleListing();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(List.of(immediateSaleListing).size(), result.size());
        verify(immediateSaleListingRepository).findAll();
    }

    /**
     * Tests the retrieval of all Immediate sale listing of a seller by its seller email.
     */
    @Test
    void testFindAllImmediateSaleListingBySellerEmail(){
        when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn(sellerEmail);
        when(immediateSaleListingRepository.findAllBySellerEmail(anyString())).thenReturn(List.of(immediateSaleListing));

        List<ImmediateSaleListing> result = productListingServiceImpl.findAllImmediateSaleListingBySellerEmail(authorizationHeader);
        Assertions.assertNotNull(result);
        verify(immediateSaleListingRepository).findAllBySellerEmail(sellerEmail);
    }

    /**
     * Tests the retrieval of all Auction sale listing of a seller by its seller email.
     */
    @Test
    void testFindAllAuctionSaleListingBySellerEmail(){
        when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn(sellerEmail);
        when(auctionSaleListingRepository.findAllBySellerEmail(anyString())).thenReturn(List.of(auctionSaleListing));

        List<AuctionSaleListing> result = productListingServiceImpl.findAllAuctionSaleListingBySellerEmail(authorizationHeader);
        Assertions.assertNotNull(result);
        verify(auctionSaleListingRepository).findAllBySellerEmail(sellerEmail);
    }

    /**
     * Tests the retrieval of all Immediate sale listing images by immediateListingId.
     */
    @Test
    void testFindAllImmediateSaleListingImagesByID(){
        when(immediateSaleImageRepository.getAllByImmediateSaleListingID(anyString())).thenReturn(List.of(immediateSaleImage));

        GetListingImageResponse result = productListingServiceImpl.findAllImmediateSaleListingImagesByID("immediateSaleListingID");
        Assertions.assertNotNull(result);
        verify(immediateSaleImageRepository).getAllByImmediateSaleListingID("immediateSaleListingID");
    }

    /**
     * Tests the retrieval of all Auction sale listing images by auctionListingId.
     */
    @Test
    void testFindAllAuctionSaleListingImagesByID(){
        when(auctionSaleImageRepository.findAllByAuctionSaleListingID(anyString())).thenReturn(List.of(auctionSaleImage));

        GetListingImageResponse result = productListingServiceImpl.findAllAuctionSaleListingImagesByID("auctionSaleListingID");
        Assertions.assertNotNull(result);
        verify(auctionSaleImageRepository).findAllByAuctionSaleListingID("auctionSaleListingID");
    }

    /**
     * Tests the retrieval of user listing details by userId.
     */
    @Test
    void testFindUserListingById(){
        when(immediateSaleListingRepository.findAllBySellerID(anyInt())).thenReturn(List.of(immediateSaleListing));

        List<ImmediateSaleListing> result = productListingServiceImpl.findUserListingById(user.getUserID());
        Assertions.assertEquals(List.of(immediateSaleListing), result);
        verify(immediateSaleListingRepository).findAllBySellerID(user.getUserID());
    }

    /**
     * Tests the retrieval of all approved Immediate listing.
     */
    @Test
    void testFindAllApprovedImmediateSaleListing(){
        when(immediateSaleListingRepository.findAllApprovedImmediateSaleListing()).thenReturn(List.of(new ImmediateSaleListing("immediateSaleListingID", "productName", "productDescription", 0d, "category", "sellerEmail", new User(0, "firstName", "lastName", "email", "password", RoleEnum.USER, 0d, 0d), true, true, false, "approverEmail", "messageFromApprover", false, new GregorianCalendar(2024, Calendar.MARCH, 21, 18, 43).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 21, 18, 43).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 21, 18, 43).getTime())));
        when(immediateSaleImageRepository.getAllByImmediateSaleListingID(anyString())).thenReturn(List.of(immediateSaleImage));

        List<ApprovedImmediateSaleListingForAdminResponse> result = productListingServiceImpl.findAllApprovedImmediateSaleListing();
        Assertions.assertNotNull(result);
        verify(immediateSaleListingRepository).findAllApprovedImmediateSaleListing();
        verify(immediateSaleImageRepository).getAllByImmediateSaleListingID("immediateSaleListingID");
    }

    /**
     * Tests the retrieval of all denied Immediate listing.
     */
    @Test
    void testFindAllDeniedImmediateSaleListing(){
        when(immediateSaleListingRepository.findAllDeniedImmediateSaleListing()).thenReturn(List.of(new ImmediateSaleListing("immediateSaleListingID", "productName", "productDescription", 0d, "category", "sellerEmail", new User(0, "firstName", "lastName", "email", "password", RoleEnum.USER, 0d, 0d), true, false, true, "approverEmail", "messageFromApprover", false, new GregorianCalendar(2024, Calendar.MARCH, 21, 18, 43).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 21, 18, 43).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 21, 18, 43).getTime())));
        when(immediateSaleImageRepository.getAllByImmediateSaleListingID(anyString())).thenReturn(List.of(immediateSaleImage));

        List<DeniedImmediateSaleListingForAdminResponse> result = productListingServiceImpl.findAllDeniedImmediateSaleListing();
        Assertions.assertNotNull(result);
        verify(immediateSaleListingRepository).findAllDeniedImmediateSaleListing();
        verify(immediateSaleImageRepository).getAllByImmediateSaleListingID("immediateSaleListingID");
    }

    /**
     * Tests the retrieval of all approved Auction listing.
     */
    @Test
    void testFindAllApprovedAuctionSaleListing(){
        when(auctionSaleListingRepository.findAllApprovedAuctionSaleListing()).thenReturn(List.of(new AuctionSaleListing("auctionSaleListingID", "productName", "productDescription", 0d, 0d, "currentHighestBidUserMail", "category", "sellerEmail", new GregorianCalendar(2024, Calendar.MARCH, 21, 18, 43).getTime(), true, true, false, "approverEmail", "messageFromApprover", false, new GregorianCalendar(2024, Calendar.MARCH, 21, 18, 43).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 21, 18, 43).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 21, 18, 43).getTime())));
        when(auctionSaleImageRepository.findAllByAuctionSaleListingID(anyString())).thenReturn(List.of(auctionSaleImage));

        List<ApprovedAuctionSaleListingForAdminResponse> result = productListingServiceImpl.findAllApprovedAuctionSaleListing();
        Assertions.assertNotNull(result);
        verify(auctionSaleListingRepository).findAllApprovedAuctionSaleListing();
        verify(auctionSaleImageRepository).findAllByAuctionSaleListingID("auctionSaleListingID");
    }

    /**
     * Tests the retrieval of all denied Auction listing.
     */
    @Test
    void testFindAllDeniedAuctionSaleListing(){
        when(auctionSaleListingRepository.findAllDeniedAuctionSaleListing()).thenReturn(List.of(new AuctionSaleListing("auctionSaleListingID", "productName", "productDescription", 0d, 0d, "currentHighestBidUserMail", "category", "sellerEmail", new GregorianCalendar(2024, Calendar.MARCH, 21, 18, 43).getTime(), true, false, true, "approverEmail", "messageFromApprover", false, new GregorianCalendar(2024, Calendar.MARCH, 21, 18, 43).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 21, 18, 43).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 21, 18, 43).getTime())));
        when(auctionSaleImageRepository.findAllByAuctionSaleListingID(anyString())).thenReturn(List.of(new AuctionSaleImage(0, "auctionSaleListingID", "imageURL", new GregorianCalendar(2024, Calendar.MARCH, 21, 18, 43).getTime())));

        List<DeniedAuctionSaleListingForAdminResponse> result = productListingServiceImpl.findAllDeniedAuctionSaleListing();
        Assertions.assertNotNull(result);
        verify(auctionSaleListingRepository).findAllDeniedAuctionSaleListing();
        verify(auctionSaleImageRepository).findAllByAuctionSaleListingID("auctionSaleListingID");
    }

    /**
     * Tests the retrieval of auction sale product details by its listing ID.
     */
    @Test
    void testFindAuctionSaleProductDetailsById(){
        when(auctionSaleListingRepository.findAuctionSaleProductByID(anyString())).thenReturn(auctionSaleListing);
        when(auctionSaleImageRepository.findAllByAuctionSaleListingID(anyString())).thenReturn(List.of(auctionSaleImage));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        AuctionSaleProductResponse result = productListingServiceImpl.findAuctionSaleProductDetailsById("auctionSaleListingID");
        Assertions.assertNotNull(result);

        verify(auctionSaleListingRepository, times(1)).findAuctionSaleProductByID("auctionSaleListingID");
        verify(auctionSaleImageRepository, times(1)).findAllByAuctionSaleListingID("auctionSaleListingID");
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    /**
     * Tests the behavior of retrieval of auction sale product details when the specified product listing is not found.
     */
    @Test
    void testFindAuctionSaleProductDetailsById_ProductNotFound() {
        when(auctionSaleListingRepository.findAuctionSaleProductByID(anyString())).thenReturn(null);
        when(auctionSaleImageRepository.findAllByAuctionSaleListingID(anyString())).thenReturn(List.of(auctionSaleImage));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(ListingNotFoundException.class, () -> productListingServiceImpl.findAuctionSaleProductDetailsById("auctionSaleListingID"));
    }

    /**
     * Tests the behavior of retrieval of auction sale product details when the specified seller is not found.
     */
    @Test
    void testFindAuctionSaleProductDetailsById_SellerNotFound() {
        when(auctionSaleListingRepository.findAuctionSaleProductByID(anyString())).thenReturn(auctionSaleListing);
        when(auctionSaleImageRepository.findAllByAuctionSaleListingID(anyString())).thenReturn(List.of(auctionSaleImage));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> productListingServiceImpl.findAuctionSaleProductDetailsById("auctionSaleListingID"));
    }

    /**
     * Tests the retrieval of all immediate sale product details list for authorized user.
     */
    @Test
    void testFindAllImmediateListing(){
        when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn(sellerEmail);
        when(immediateSaleListingRepository.findAllImmediateSaleListing(anyString())).thenReturn(List.of(immediateSaleListing));
        when(immediateSaleImageRepository.getAllByImmediateSaleListingID(anyString())).thenReturn(List.of(immediateSaleImage));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        List<ImmediateSaleListingCreationResponse> result = productListingServiceImpl.findAllImmediateListing(authorizationHeader);
        Assertions.assertNotNull(result);

        verify(jwtService).extractUserNameFromRequestHeaders(authorizationHeader);
        verify(immediateSaleListingRepository).findAllImmediateSaleListing(sellerEmail);
        immediateSaleListingRepository.findAllImmediateSaleListing(sellerEmail).forEach(listing ->
                verify(immediateSaleImageRepository).getAllByImmediateSaleListingID(listing.getImmediateSaleListingID()));
        verify(userRepository).findByEmail(sellerEmail);
    }

    /**
     * Tests the behavior of all immediate sale product details list when the specified seller is not found.
     */
    @Test
    void testFindAllImmediateListing_SellerNotFound() {
        when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn(sellerEmail);
        when(immediateSaleListingRepository.findAllImmediateSaleListing(anyString())).thenReturn(List.of(immediateSaleListing));
        when(immediateSaleImageRepository.getAllByImmediateSaleListingID(anyString())).thenReturn(List.of(immediateSaleImage));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> productListingServiceImpl.findAllImmediateListing(authorizationHeader));
    }

    /**
     * Tests the retrieval of all auction sale product details list for authorized user.
     */
    @Test
    void testFindAllAuctionListing(){
        when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn(sellerEmail);
        when(auctionSaleListingRepository.findAllAuctionSaleListing(anyString(), any())).thenReturn(List.of(auctionSaleListing));
        when(auctionSaleImageRepository.findAllByAuctionSaleListingID(anyString())).thenReturn(List.of(auctionSaleImage));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        List<AuctionSaleListingCreationResponse> result = productListingServiceImpl.findAllAuctionListing(authorizationHeader);
        Assertions.assertNotNull(result);

        verify(jwtService).extractUserNameFromRequestHeaders(authorizationHeader);
        auctionSaleListingRepository.findAllAuctionSaleListing(sellerEmail, DateUtil.getCurrentDate()).forEach(listing ->
                verify(auctionSaleImageRepository).findAllByAuctionSaleListingID(listing.getAuctionSaleListingID()));
        verify(userRepository).findByEmail(sellerEmail);
    }

    /**
     * Tests the retrieval of all auction sale product details list for admin.
     */
    @Test
    void testFindAllAuctionListingForAdmin(){
        when(auctionSaleListingRepository.findAllAuctionSaleListingForAdmin()).thenReturn(List.of(auctionSaleListing));
        when(auctionSaleImageRepository.findAllByAuctionSaleListingID(anyString())).thenReturn(List.of(auctionSaleImage));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        List<AuctionSaleListingCreationResponse> result = productListingServiceImpl.findAllAuctionListingForAdmin();
        Assertions.assertNotNull(result);

        auctionSaleListingRepository.findAllAuctionSaleListingForAdmin().forEach(listing ->
                verify(auctionSaleImageRepository).findAllByAuctionSaleListingID(listing.getAuctionSaleListingID()));
    }

    @Test
    void testFindAuctionListingByID(){
        when(auctionSaleListingRepository.findByAuctionSaleListingID(anyString())).thenReturn(auctionSaleListing);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        AuctionSaleProductResponse result = productListingServiceImpl.findAuctionListingByID("auctionSaleListingID");
        Assertions.assertNotNull(result);
    }

    @Test
    void testFindAuctionListingByIDProductNotFound(){
        when(auctionSaleListingRepository.findByAuctionSaleListingID(anyString())).thenReturn(null);

        assertThrows(ListingNotFoundException.class, () -> productListingServiceImpl.findAuctionListingByID("auctionSaleListingID"));
    }
}
