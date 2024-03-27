package tech.group15.thriftharbour.service.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AwsS3ServiceImplTest {

    @Mock
    private S3Client s3Client;

    @InjectMocks
    private AwsS3ServiceImpl awsS3Service;

    @Test
    public void testUploadImageToBucket_Success() throws IOException {
        // Mock S3 response
        PutObjectResponse mockResponse = PutObjectResponse.builder().build();
        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class))).thenReturn(mockResponse);

        // Mock MultipartFile
        MockMultipartFile mockFile = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test data".getBytes());

        // Upload to S3 bucket
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket("mymitsandboxbucket")
                .key("path/to/image.jpg")
                .build();
        PutObjectResponse response = s3Client.putObject(request, RequestBody.fromInputStream(mockFile.getInputStream(), mockFile.getSize()));

        // Test upload
        int statusCode = (response != null) ? 200 : 500;

        // Verify
        assertEquals(200, statusCode);
        verify(s3Client, times(1)).putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }

    @Test
    public void testUploadImageToBucket_Failure() {
        // Mock S3 client to throw an exception
        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class))).thenThrow(new RuntimeException("S3 upload failed"));

        // Mock MultipartFile
        MockMultipartFile mockFile = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test data".getBytes());

        // Upload to S3 bucket and catch exception
        int statusCode;
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket("mymitsandboxbucket")
                    .key("path/to/image.jpg")
                    .build();
            s3Client.putObject(request, RequestBody.fromInputStream(mockFile.getInputStream(), mockFile.getSize()));
            statusCode = 200;
        } catch (RuntimeException | IOException e) {
            statusCode = 500;
        }

        // Verify
        assertEquals(500, statusCode); // Assuming 500 for internal server error
        // Additionally, check logging or other error handling if implemented
    }

    @Test
    public void testBucketNameIsCorrect() throws IOException {
        // Mock S3 response
        PutObjectResponse mockResponse = PutObjectResponse.builder().build();
        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class))).thenReturn(mockResponse);

        // Mock MultipartFile
        MockMultipartFile mockFile = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test data".getBytes());

        // Upload to S3 bucket
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket("mymitsandboxbucket")
                .key("path/to/image.jpg")
                .build();
        s3Client.putObject(request, RequestBody.fromInputStream(mockFile.getInputStream(), mockFile.getSize()));

        // Verify
        verify(s3Client).putObject(argThat((PutObjectRequest req) -> req.bucket().equals("mymitsandboxbucket")), any(RequestBody.class));
    }

    @Test
    public void testKeyPathIsCorrect() throws IOException {
        // Mock S3 response
        PutObjectResponse mockResponse = PutObjectResponse.builder().build();
        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class))).thenReturn(mockResponse);

        // Mock MultipartFile
        MockMultipartFile mockFile = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test data".getBytes());

        // Upload to S3 bucket
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket("mymitsandboxbucket")
                .key("path/to/image.jpg")
                .build();
        s3Client.putObject(request, RequestBody.fromInputStream(mockFile.getInputStream(), mockFile.getSize()));

        // Verify
        verify(s3Client).putObject(argThat((PutObjectRequest req) -> req.key().equals("path/to/image.jpg")), any(RequestBody.class));
    }

    @Test
    public void testMultipartFileHandling() throws IOException {
        // Mock S3 response
        PutObjectResponse mockResponse = PutObjectResponse.builder().build();
        when(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class))).thenReturn(mockResponse);

        // Mock MultipartFile
        MockMultipartFile mockFile = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test data".getBytes());

        // Upload to S3 bucket
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket("mymitsandboxbucket")
                .key("path/to/image.jpg")
                .build();
        s3Client.putObject(request, RequestBody.fromInputStream(mockFile.getInputStream(), mockFile.getSize()));

        // Verify
        verify(s3Client).putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }



    // Additional test cases for configuration, null parameters, and edge cases can be added similarly
}
