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
import tech.group15.thriftharbour.dto.request.PlaceBidRequest;
import tech.group15.thriftharbour.dto.request.SignInRequest;
import tech.group15.thriftharbour.dto.request.SignUpRequest;
import tech.group15.thriftharbour.dto.response.SignInResponse;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:testenv.properties")
public class BiddingControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void testPlaceBid(){

        HttpHeaders headers = new HttpHeaders();


        String token = createUserToken();
        headers.set("Authorization", "Bearer " + token);

        PlaceBidRequest bidRequest = new PlaceBidRequest("uuid", 20.5);
        HttpEntity<PlaceBidRequest> requestEntity = new HttpEntity<>(bidRequest, headers);

        addAuctionListing();

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/user/place-bid",
                HttpMethod.POST,
                requestEntity,
                String.class);

        jdbcTemplate.execute("DELETE FROM auction_sale_listing where auction_sale_listingid = 'uuid'");
        jdbcTemplate.execute("DELETE FROM user WHERE email= 'mock@dal.ca'");

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testPlaceBidWhenLessthanCurrentBid(){
        HttpHeaders headers = new HttpHeaders();


        String token = createUserToken();
        headers.set("Authorization", "Bearer " + token);

        PlaceBidRequest bidRequest = new PlaceBidRequest("1", 1.5);
        HttpEntity<PlaceBidRequest> requestEntity = new HttpEntity<>(bidRequest, headers);

        addAuctionListing();

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/user/place-bid",
                HttpMethod.POST,
                requestEntity,
                String.class);

        jdbcTemplate.execute("DELETE FROM auction_sale_listing where auction_sale_listingid = 'uuid'");
        jdbcTemplate.execute("DELETE FROM user WHERE email= 'mock@dal.ca'");

        assertFalse(response.getBody().isEmpty());

    }

    private String createUserToken(){
        SignUpRequest request = new SignUpRequest();
        request.setEmail("mock@dal.ca");
        request.setFirstName("Mock");
        request.setLastName("Test");
        request.setPassword("Mock@Test.com");

        restTemplate.postForEntity("/api/v1/auth/signup", request, Void.class);

        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmail("mock@dal.ca");
        signInRequest.setPassword("Mock@Test.com");

        ResponseEntity<SignInResponse> message = restTemplate.postForEntity("/api/v1/auth/signin", signInRequest, SignInResponse.class);

        return message.getBody().getToken();
    }

    private void addAuctionListing(){
        String query = "INSERT\n" +
                "\tINTO\n" +
                "\tauction_sale_listing (active,\n" +
                "\thighest_bid,\n" +
                "\tis_approved,\n" +
                "\tis_rejected,\n" +
                "\tis_sold,\n" +
                "\tstarting_bid,\n" +
                "\tauction_slot,\n" +
                "\tcreated_date,\n" +
                "\tupdated_date,\n" +
                "\tapprover_email,\n" +
                "\tauction_sale_listingid,\n" +
                "\tcategory,\n" +
                "\tcurrent_highest_bid_user_mail,\n" +
                "\tproduct_description,\n" +
                "\tproduct_name,\n" +
                "\tseller_email)\n" +
                "values (1,\n" +
                "10,\n" +
                "1,\n" +
                "0,\n" +
                "0,\n" +
                "10,\n" +
                "'2024-10-10',\n" +
                "'2024-10-10',\n" +
                "'2024-10-10',\n" +
                "'approve@admin',\n" +
                "'uuid',\n" +
                "'Electronics',\n" +
                "'higbid@mail.com',\n" +
                "'pd',\n" +
                "'pn',\n" +
                "'seller@mail')";
        jdbcTemplate.execute(query);
    }
}
