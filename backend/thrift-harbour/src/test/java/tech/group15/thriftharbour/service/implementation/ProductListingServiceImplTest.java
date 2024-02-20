package tech.group15.thriftharbour.service.implementation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.group15.thriftharbour.dto.AuctionSaleListingCreationResponse;
import tech.group15.thriftharbour.dto.ImmediateSaleListingCreationResponse;
import tech.group15.thriftharbour.dto.SubmitListingRequest;
import tech.group15.thriftharbour.repository.AuctionSaleImageRepository;
import tech.group15.thriftharbour.repository.AuctionSaleListingRepository;
import tech.group15.thriftharbour.repository.ImmediateSaleImageRepository;
import tech.group15.thriftharbour.repository.ImmediateSaleListingRepository;
import tech.group15.thriftharbour.service.AwsS3Service;
import tech.group15.thriftharbour.service.JWTService;

import java.util.List;

import static org.mockito.Mockito.*;

class ProductListingServiceImplTest {
    @Mock
    JWTService jwtService;
    @Mock
    AwsS3Service awsS3Service;
    @Mock
    ImmediateSaleListingRepository immediateSaleListingRepository;
    @Mock
    ImmediateSaleImageRepository immediateSaleImageRepository;
    @Mock
    AuctionSaleListingRepository auctionSaleListingRepository;
    @Mock
    AuctionSaleImageRepository auctionSaleImageRepository;
    @InjectMocks
    ProductListingServiceImpl productListingServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateImmediateSaleListing() {
        when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn("extractUserNameFromRequestHeadersResponse");
        when(awsS3Service.uploadImageToBucket(anyString(), any())).thenReturn(0);
        when(immediateSaleListingRepository.saveAll(any())).thenReturn(List.of(new S()));
        when(immediateSaleListingRepository.save(any())).thenReturn(new S());
        when(immediateSaleImageRepository.saveAll(any())).thenReturn(List.of(new S()));
        when(immediateSaleImageRepository.save(any())).thenReturn(new S());
        when(auctionSaleListingRepository.saveAll(any())).thenReturn(List.of(new S()));
        when(auctionSaleListingRepository.save(any())).thenReturn(new S());
        when(auctionSaleImageRepository.saveAll(any())).thenReturn(List.of(new S()));
        when(auctionSaleImageRepository.save(any())).thenReturn(new S());

        ImmediateSaleListingCreationResponse result = productListingServiceImpl.CreateImmediateSaleListing("authorizationHeader", new SubmitListingRequest());
        Assertions.assertEquals(null, result);
    }

    @Test
    void testCreateAuctionSaleListing() {
        when(jwtService.extractUserNameFromRequestHeaders(anyString())).thenReturn("extractUserNameFromRequestHeadersResponse");
        when(awsS3Service.uploadImageToBucket(anyString(), any())).thenReturn(0);
        when(immediateSaleListingRepository.saveAll(any())).thenReturn(List.of(new S()));
        when(immediateSaleListingRepository.save(any())).thenReturn(new S());
        when(immediateSaleImageRepository.saveAll(any())).thenReturn(List.of(new S()));
        when(immediateSaleImageRepository.save(any())).thenReturn(new S());
        when(auctionSaleListingRepository.saveAll(any())).thenReturn(List.of(new S()));
        when(auctionSaleListingRepository.save(any())).thenReturn(new S());
        when(auctionSaleImageRepository.saveAll(any())).thenReturn(List.of(new S()));
        when(auctionSaleImageRepository.save(any())).thenReturn(new S());

        AuctionSaleListingCreationResponse result = productListingServiceImpl.CreateAuctionSaleListing("authorizationHeader", new SubmitListingRequest());
        Assertions.assertEquals(null, result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme