package tech.group15.thriftharbour.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AwsS3Service {
    void uploadImageToBucket(MultipartFile Images);
}
