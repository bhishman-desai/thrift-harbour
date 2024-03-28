package tech.group15.thriftharbour.service.implementation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.group15.thriftharbour.dto.request.ListingReviewRequest;
import tech.group15.thriftharbour.dto.response.ListingReviewResponse;
import tech.group15.thriftharbour.enums.ListingStatus;
import tech.group15.thriftharbour.enums.SellCategoryEnum;
import tech.group15.thriftharbour.exception.ListingNotActiveException;
import tech.group15.thriftharbour.exception.ListingNotFoundException;
import tech.group15.thriftharbour.model.AuctionSaleListing;
import tech.group15.thriftharbour.model.ImmediateSaleListing;
import tech.group15.thriftharbour.repository.AuctionSaleListingRepository;
import tech.group15.thriftharbour.repository.ImmediateSaleListingRepository;
import tech.group15.thriftharbour.service.JWTService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class AdminServiceImplTest {

    @Mock
    private JWTService jwtService;

    @Mock
    private ImmediateSaleListingRepository immediateListingRepository;

    @Mock
    private AuctionSaleListingRepository auctionListingRepository;

    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testReviewListingForImmediateSaleListingApproved() {
        // Mock input
        String authorizationHeader = "Bearer token";
        ListingReviewRequest reviewRequest = new ListingReviewRequest();
        reviewRequest.setListingId("1");
        reviewRequest.setSellCategory(SellCategoryEnum.DIRECT);
        reviewRequest.setStatus(ListingStatus.APPROVED);
        reviewRequest.setMessage("Approved");

        // Mock JWT service
        when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn("admin@example.com");

        // Mock repository
        ImmediateSaleListing immediateSaleListing = new ImmediateSaleListing();
        immediateSaleListing.setImmediateSaleListingID("1");
        when(immediateListingRepository.findByImmediateSaleListingID("1")).thenReturn(immediateSaleListing);
        when(immediateListingRepository.save(any(ImmediateSaleListing.class))).thenReturn(immediateSaleListing);

        // Perform test
        ListingReviewResponse response = adminService.reviewListing(authorizationHeader, reviewRequest);

        // Verify
        Assertions.assertEquals("1", response.getListingId());
        Assertions.assertEquals(ListingStatus.APPROVED.toString(), response.getStatus());
    }

    @Test
    public void testReviewListingForAuctionSaleListingRejected() {
        // Mock input
        String authorizationHeader = "Bearer token";
        ListingReviewRequest reviewRequest = new ListingReviewRequest();
        reviewRequest.setListingId("2");
        reviewRequest.setSellCategory(SellCategoryEnum.AUCTION);
        reviewRequest.setStatus(ListingStatus.REJECTED);
        reviewRequest.setMessage("Rejected");

        // Mock JWT service
        when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn("admin@example.com");

        // Mock repository
        AuctionSaleListing auctionSaleListing = new AuctionSaleListing();
        auctionSaleListing.setAuctionSaleListingID("2");
        when(auctionListingRepository.findByAuctionSaleListingID("2")).thenReturn(auctionSaleListing);
        when(auctionListingRepository.save(any(AuctionSaleListing.class))).thenReturn(auctionSaleListing);

        // Perform test
        ListingReviewResponse response = adminService.reviewListing(authorizationHeader, reviewRequest);

        // Verify
        Assertions.assertEquals("2", response.getListingId());
        Assertions.assertEquals(ListingStatus.REJECTED.toString(), response.getStatus());
    }

    @Test
    public void testReviewListingForNonExistingListing() {
        // Mock input
        String authorizationHeader = "Bearer token";
        ListingReviewRequest reviewRequest = new ListingReviewRequest();
        reviewRequest.setListingId("3");
        reviewRequest.setSellCategory(SellCategoryEnum.DIRECT);
        reviewRequest.setStatus(ListingStatus.APPROVED);
        reviewRequest.setMessage("Approved");

        // Mock JWT service
        when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn("admin@example.com");

        // Mock repository
        when(immediateListingRepository.findByImmediateSaleListingID("3")).thenReturn(null);

        // Perform test and verify exception
        Assertions.assertThrows(ListingNotFoundException.class, () -> {
            adminService.reviewListing(authorizationHeader, reviewRequest);
        });
    }

    @Test
    public void testReviewListingForInactiveListing() {
        // Mock input
        String authorizationHeader = "Bearer token";
        ListingReviewRequest reviewRequest = new ListingReviewRequest();
        reviewRequest.setListingId("4");
        reviewRequest.setSellCategory(SellCategoryEnum.AUCTION);
        reviewRequest.setStatus(ListingStatus.APPROVED);
        reviewRequest.setMessage("Approved");

        // Mock JWT service
        when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn("admin@example.com");

        // Mock repository
        AuctionSaleListing auctionSaleListing = new AuctionSaleListing();
        auctionSaleListing.setAuctionSaleListingID("4");
        auctionSaleListing.setActive(false);
        when(auctionListingRepository.findByAuctionSaleListingID("4")).thenReturn(auctionSaleListing);

        // Perform test and verify exception
        Assertions.assertThrows(ListingNotActiveException.class, () -> {
            adminService.reviewListing(authorizationHeader, reviewRequest);
        });
    }

    @Test
    public void testReviewListingForImmediateSaleListingRejected() {
        // Mock input
        String authorizationHeader = "Bearer token";
        ListingReviewRequest reviewRequest = new ListingReviewRequest();
        reviewRequest.setListingId("5");
        reviewRequest.setSellCategory(SellCategoryEnum.DIRECT);
        reviewRequest.setStatus(ListingStatus.REJECTED);
        reviewRequest.setMessage("Rejected");

        // Mock JWT service
        when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn("admin@example.com");

        // Mock repository
        ImmediateSaleListing immediateSaleListing = new ImmediateSaleListing();
        immediateSaleListing.setImmediateSaleListingID("5");
        when(immediateListingRepository.findByImmediateSaleListingID("5")).thenReturn(immediateSaleListing);
        when(immediateListingRepository.save(any(ImmediateSaleListing.class))).thenReturn(immediateSaleListing);

        // Perform test
        ListingReviewResponse response = adminService.reviewListing(authorizationHeader, reviewRequest);

        // Verify
        Assertions.assertEquals("5", response.getListingId());
        Assertions.assertEquals(ListingStatus.REJECTED.toString(), response.getStatus());
    }

    @Test
    public void testReviewListingForAuctionSaleListingApproved() {
        // Mock input
        String authorizationHeader = "Bearer token";
        ListingReviewRequest reviewRequest = new ListingReviewRequest();
        reviewRequest.setListingId("6");
        reviewRequest.setSellCategory(SellCategoryEnum.AUCTION);
        reviewRequest.setStatus(ListingStatus.APPROVED);
        reviewRequest.setMessage("Approved");

        // Mock JWT service
        when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn("admin@example.com");

        // Mock repository
        AuctionSaleListing auctionSaleListing = new AuctionSaleListing();
        auctionSaleListing.setAuctionSaleListingID("6");
        when(auctionListingRepository.findByAuctionSaleListingID("6")).thenReturn(auctionSaleListing);
        when(auctionListingRepository.save(any(AuctionSaleListing.class))).thenReturn(auctionSaleListing);

        // Perform test
        ListingReviewResponse response = adminService.reviewListing(authorizationHeader, reviewRequest);

        // Verify
        Assertions.assertEquals("6", response.getListingId());
        Assertions.assertEquals(ListingStatus.APPROVED.toString(), response.getStatus());
    }
}
