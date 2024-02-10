package tech.group15.thriftharbour.utils;

import org.springframework.web.multipart.MultipartFile;
import tech.group15.thriftharbour.exception.ImageTypeNotValidException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    private FileUtils() {
    }

    public static Path generateTempFilePath(MultipartFile file) {
        Path tempFilePath = null;
        try {
            tempFilePath = Files.createTempFile(file.getOriginalFilename(), "");
            file.transferTo(tempFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tempFilePath;
    }

    public static boolean isImageFile(MultipartFile file) {
        if (file != null) {
            String fileName = file.getOriginalFilename();
            return fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg");
        }
        throw new ImageTypeNotValidException("Images should be of type png, jpg, jpeg");
    }

    public static double fileSizeInMB(MultipartFile file) {
        return file.getSize() * 0.000_001;
    }

    public static String getFileExtention(MultipartFile file){
        String fileContent = file.getContentType();
        String extension = fileContent.split("/")[1];
        return "." + extension;
    }

    public static String generateUniqueFileNameForImage(String sellType,
                                                        String userName,
                                                        int productNumber,
                                                        String fileExtension) {

        String fileName = sellType + "/" + userName + "/" + "productImage" +
                Integer.toString(productNumber) + fileExtension;
        return fileName;
    }

    public static String generateImageURL(String bucketName, String region, String fileName){
        String imageURL = "https://"+bucketName+".s3."+region + ".amazonaws.com" + "/" + fileName;
        return imageURL;
    }
}
