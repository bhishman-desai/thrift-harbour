package tech.group15.thriftharbour.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import tech.group15.thriftharbour.dto.request.SignInRequest;
import tech.group15.thriftharbour.dto.request.SignUpRequest;
import tech.group15.thriftharbour.dto.response.GetListingImageResponse;
import tech.group15.thriftharbour.dto.response.SignInResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:testenv.properties")
public class ListingControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void testGetImmediateSaleListingImage() {

        SignUpRequest request = new SignUpRequest();
        request.setEmail("mock@dal.ca");
        request.setFirstName("Mock");
        request.setLastName("Test");
        request.setPassword("Mock@Test.com");

        restTemplate.postForEntity("/api/v1/auth/signup", request, Void.class);

        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmail("mock@dal.ca");
        signInRequest.setPassword("Mock@Test.com");

        ResponseEntity<SignInResponse> msg = restTemplate.postForEntity("/api/v1/auth/signin", signInRequest, SignInResponse.class);

        String token = msg.getBody().getToken();
        System.out.println("Token set" + token);

        // Set the authentication token in the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        System.out.println(token);
        // Create the request entity with headers
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        this.jdbcTemplate.execute("insert into immediate_sale_image values (1000, '1998-10-15','url', '789')");

        // Send a GET request to the endpoint
        ResponseEntity<GetListingImageResponse> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/users/listing/get-immediatesale-images/{id}",
                HttpMethod.GET,
                requestEntity,
                GetListingImageResponse.class,
                "1000");

        // Assert the response status code
        assertEquals(HttpStatus.OK, response.getStatusCode());

        this.jdbcTemplate.execute("DELETE FROM user WHERE email= 'mock@dal.ca'");
        this.jdbcTemplate.execute("DELETE FROM immediate_sale_image WHERE imageurl = 'url'");

    }

    @Test
    public void testGetAuctionSaleListingImage() {

        SignUpRequest request = new SignUpRequest();
        request.setEmail("mock@dal.ca");
        request.setFirstName("Mock");
        request.setLastName("Test");
        request.setPassword("Mock@Test.com");

        restTemplate.postForEntity("/api/v1/auth/signup", request, Void.class);

        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmail("mock@dal.ca");
        signInRequest.setPassword("Mock@Test.com");

        ResponseEntity<SignInResponse> msg = restTemplate.postForEntity("/api/v1/auth/signin", signInRequest, SignInResponse.class);

        String token = msg.getBody().getToken();
        System.out.println("Token set" + token);

        // Set the authentication token in the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        System.out.println(token);
        // Create the request entity with headers
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        this.jdbcTemplate.execute("insert into auction_sale_image values (1000, '1998-10-15', '789', 'url')");

        // Send a GET request to the endpoint
        ResponseEntity<GetListingImageResponse> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/users/listing/get-auctionsale-images/{id}",
                HttpMethod.GET,
                requestEntity,
                GetListingImageResponse.class,
                "1000");

        // Assert the response status code
        assertEquals(HttpStatus.OK, response.getStatusCode());

        this.jdbcTemplate.execute("DELETE FROM user WHERE email= 'mock@dal.ca'");
        this.jdbcTemplate.execute("DELETE FROM auction_sale_image WHERE imageurl = 'url'");

    }
}
