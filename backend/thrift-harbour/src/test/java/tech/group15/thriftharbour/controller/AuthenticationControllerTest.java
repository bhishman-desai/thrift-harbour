package tech.group15.thriftharbour.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import tech.group15.thriftharbour.dto.request.SignInRequest;
import tech.group15.thriftharbour.dto.request.SignUpRequest;
import tech.group15.thriftharbour.dto.response.SignInResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:testenv.properties")
class AuthenticationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Sql(statements = "DELETE FROM user WHERE email= 'mock@dal.ca'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testSignUpAndLogin(){

        SignUpRequest request = new SignUpRequest();
        request.setEmail("mock@dal.ca");
        request.setFirstName("Mock");
        request.setLastName("Test");
        request.setPassword("Mock@Test.com");

        HttpStatusCode statusCode = restTemplate.postForEntity("/api/v1/auth/signup", request, Void.class).getStatusCode();

        Assertions.assertEquals(statusCode, HttpStatus.OK);

        HttpStatusCode conflictCode = restTemplate.postForEntity("/api/v1/auth/signup", request, Void.class).getStatusCode();

        Assertions.assertEquals(conflictCode, HttpStatus.CONFLICT);

        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmail("mock@dal.ca");
        signInRequest.setPassword("Mock@Test.com");

        ResponseEntity<SignInResponse> msg = restTemplate.postForEntity("/api/v1/auth/signin", signInRequest, SignInResponse.class);

        Assertions.assertEquals(msg.getStatusCode(), HttpStatus.OK);
    }



}
