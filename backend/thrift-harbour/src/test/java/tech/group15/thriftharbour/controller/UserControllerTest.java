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
import tech.group15.thriftharbour.dto.request.BuyerRatingsRequest;
import tech.group15.thriftharbour.dto.request.SellerRatingsRequest;
import tech.group15.thriftharbour.dto.request.SignInRequest;
import tech.group15.thriftharbour.dto.request.SignUpRequest;
import tech.group15.thriftharbour.dto.response.SignInResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:testenv.properties")
public class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void testAddBuyerRatings(){
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

        BuyerRatingsRequest buyerRatingsRequest = new BuyerRatingsRequest();
        buyerRatingsRequest.setRatingToUserId(1000);
        buyerRatingsRequest.setBuyerRatings(5);

        // Set the authentication token in the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        System.out.println(token);
        // Create the request entity with headers
        HttpEntity<BuyerRatingsRequest> requestEntity = new HttpEntity<>(buyerRatingsRequest, headers);

        this.jdbcTemplate.execute("insert into buyer_ratings (rating_to_user_id, rating_from_user_id, buyer_ratings) values (1000, 2, 5)");

        // Send a Post request to the endpoint
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/user/add-buyer-ratings",
                HttpMethod.POST,
                requestEntity,
                String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        this.jdbcTemplate.execute("DELETE FROM buyer_ratings WHERE rating_to_user_id = 1000");
    }

    @Test
    public void testAddSellerRatings(){
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

        SellerRatingsRequest sellerRatingsRequest = new SellerRatingsRequest();
        sellerRatingsRequest.setRatingToUserId(1000);
        sellerRatingsRequest.setSellerRatings(5);

        // Set the authentication token in the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        System.out.println(token);
        // Create the request entity with headers
        HttpEntity<SellerRatingsRequest> requestEntity = new HttpEntity<>(sellerRatingsRequest, headers);

        this.jdbcTemplate.execute("insert into seller_ratings (rating_to_user_id, rating_from_user_id, seller_ratings) values (1000, 2, 5)");

        // Send a Post request to the endpoint
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/user/add-seller-ratings",
                HttpMethod.POST,
                requestEntity,
                String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        this.jdbcTemplate.execute("DELETE FROM seller_ratings WHERE rating_to_user_id = 1000");
    }
}
