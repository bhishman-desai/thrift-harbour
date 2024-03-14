package tech.group15.thriftharbour.service.implementation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tech.group15.thriftharbour.dto.response.AuctionSaleProductResponse;
import tech.group15.thriftharbour.dto.response.ImmediateSaleListingCreationResponse;
import tech.group15.thriftharbour.enums.RoleEnum;
import tech.group15.thriftharbour.exception.ListingNotFoundException;
import tech.group15.thriftharbour.model.*;
import tech.group15.thriftharbour.repository.*;
import tech.group15.thriftharbour.service.JWTService;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

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
    @InjectMocks
    ProductListingServiceImpl productListingServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the retrieval of auction sale product details by its listing ID.
     */
    @Test
    void testFindAuctionSaleProductDetailsById(){
        when(auctionSaleListingRepository.findAuctionSaleProductByID(anyString())).thenReturn(new AuctionSaleListing("auctionSaleListingID", "productName", "productDescription", 0d, 0d, "currentHighestBidUserMail", "category", "sellerEmail", new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime(), true, true, true, "approverEmail", "messageFromApprover", true, new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime()));
        when(auctionSaleImageRepository.findAllByAuctionSaleListingID(anyString())).thenReturn(List.of(new AuctionSaleImage(0, "auctionSaleListingID", "imageURL", new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime())));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User(1, "firstName", "lastName", "email", "password", RoleEnum.USER, 4.0, 5.0)));

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
        when(auctionSaleImageRepository.findAllByAuctionSaleListingID(anyString())).thenReturn(List.of(new AuctionSaleImage(0, "auctionSaleListingID", "imageURL", new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime())));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User(1, "firstName", "lastName", "email", "password", RoleEnum.USER, 4.0, 5.0)));

        assertThrows(ListingNotFoundException.class, () -> productListingServiceImpl.findAuctionSaleProductDetailsById("auctionSaleListingID"));
    }

    /**
     * Tests the behavior of retrieval of auction sale product details when the specified seller is not found.
     */
    @Test
    void testFindAuctionSaleProductDetailsById_SellerNotFound() {
        when(auctionSaleListingRepository.findAuctionSaleProductByID(anyString())).thenReturn(new AuctionSaleListing("auctionSaleListingID", "productName", "productDescription", 0d, 0d, "currentHighestBidUserMail", "category", "sellerEmail", new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime(), true, true, true, "approverEmail", "messageFromApprover", true, new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime()));
        when(auctionSaleImageRepository.findAllByAuctionSaleListingID(anyString())).thenReturn(List.of(new AuctionSaleImage(0, "auctionSaleListingID", "imageURL", new GregorianCalendar(2024, Calendar.MARCH, 13, 12, 44).getTime())));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> productListingServiceImpl.findAuctionSaleProductDetailsById("auctionSaleListingID"));
    }

    /**
     * Tests the retrieval of all immediate sale product details list for authorized user.
     */
    @Test
    void testFindAllImmediateListing(){
        when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn("sellerEmail");
        when(immediateSaleListingRepository.findAllImmediateSaleListing(anyString())).thenReturn(List.of(new ImmediateSaleListing("immediateSaleListingID", "productName", "productDescription", 0d, "category", "sellerEmail", new User(0, "firstName", "lastName", "email", "password", RoleEnum.USER, 0d, 0d), true, true, true, "approverEmail", "messageFromApprover", true, new GregorianCalendar(2024, Calendar.MARCH, 14, 12, 35).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 14, 12, 35).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 14, 12, 35).getTime())));
        when(immediateSaleImageRepository.getAllByImmediateSaleListingID(anyString())).thenReturn(List.of(new ImmediateSaleImage(0, "immediateSaleListingID", "imageURL", new GregorianCalendar(2024, Calendar.MARCH, 14, 12, 35).getTime())));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User(1, "firstName", "lastName", "email", "password", RoleEnum.USER, 4.0, 5.0)));

        List<ImmediateSaleListingCreationResponse> result = productListingServiceImpl.findAllImmediateListing("authorizationHeader");
        Assertions.assertNotNull(result);

        verify(jwtService).extractUserNameFromRequestHeaders("authorizationHeader");
        verify(immediateSaleListingRepository).findAllImmediateSaleListing("sellerEmail");
        immediateSaleListingRepository.findAllImmediateSaleListing("sellerEmail").forEach(listing ->
                verify(immediateSaleImageRepository).getAllByImmediateSaleListingID(listing.getImmediateSaleListingID()));
        verify(userRepository).findByEmail("sellerEmail");
    }

    /**
     * Tests the behavior of all immediate sale product details list when the specified seller is not found.
     */
    @Test
    void testFindAllImmediateListing_SellerNotFound() {
        when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn("sellerEmail");
        when(immediateSaleListingRepository.findAllImmediateSaleListing(anyString())).thenReturn(List.of(new ImmediateSaleListing("immediateSaleListingID", "productName", "productDescription", 0d, "category", "sellerEmail", new User(0, "firstName", "lastName", "email", "password", RoleEnum.USER, 0d, 0d), true, true, true, "approverEmail", "messageFromApprover", true, new GregorianCalendar(2024, Calendar.MARCH, 14, 12, 35).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 14, 12, 35).getTime(), new GregorianCalendar(2024, Calendar.MARCH, 14, 12, 35).getTime())));
        when(immediateSaleImageRepository.getAllByImmediateSaleListingID(anyString())).thenReturn(List.of(new ImmediateSaleImage(0, "immediateSaleListingID", "imageURL", new GregorianCalendar(2024, Calendar.MARCH, 14, 12, 35).getTime())));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> productListingServiceImpl.findAllImmediateListing("authorizationHeader"));
    }
}
