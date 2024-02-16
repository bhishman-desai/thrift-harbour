package tech.group15.thriftharbour.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import tech.group15.thriftharbour.service.AwsS3Service;
import tech.group15.thriftharbour.utils.FileUtils;

@Service
@RequiredArgsConstructor
public class AwsS3ServiceImpl implements AwsS3Service {

    private final S3Client s3Client;

    @Value("${aws.bucketName}")
    private String bucketName;

    @Override
    public int uploadImageToBucket(String path, MultipartFile image) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(path)
                .build();
        PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest,
                FileUtils.generateTempFilePath(image));
        return putObjectResponse.sdkHttpResponse().statusCode();
    }
}
