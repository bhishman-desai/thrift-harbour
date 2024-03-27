package tech.group15.thriftharbour.controller;

import org.hibernate.annotations.processing.SQL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.RedirectView;
import tech.group15.thriftharbour.ThriftHarbourApplication;
import tech.group15.thriftharbour.dto.request.*;
import tech.group15.thriftharbour.dto.response.ForgotPassResponse;
import tech.group15.thriftharbour.dto.response.SignInResponse;
import tech.group15.thriftharbour.enums.RoleEnum;
import tech.group15.thriftharbour.model.User;
import tech.group15.thriftharbour.service.AuthenticationService;
import tech.group15.thriftharbour.service.implementation.AuthenticationServiceImpl;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "DB_URL_DEV=jdbc:mysql://db-5308.cs.dal.ca/CSCI5308_15_DEVINT?reconnect=true?allowPublicKeyRetrieval=true",
        "DB_PASSWORD_DEV=phaNg5xahf",
        "DB_USER_DEV=CSCI5308_15_DEVINT_USER",
        "AWS_ACCESS_KEY_ID_DEV=AKIAW3MEDB33MVU6ELOD",
        "AWS_BUCKET_NAME_DEV=mymitsandboxbucket",
        "AWS_REGION_DEV=ca-central-1",
        "AWS_SECRET_ACCESS_KEY_DEV=jU9znniP3Nv1PY67jNKcFSVSei3w78FQyt7WwIPL"
})
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
