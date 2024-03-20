package tech.group15.thriftharbour.utils;

import org.springframework.web.multipart.MultipartFile;
import tech.group15.thriftharbour.exception.FilepathNotFoundException;
import tech.group15.thriftharbour.exception.ImageTypeNotValidException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
  private static final double BYTES_TO_MEGABYTES = 0.000_001;
  private FileUtils() {}

  public static Path generateTempFilePath(MultipartFile file) {
    Path tempFilePath;
    try {
      tempFilePath = Files.createTempFile(file.getOriginalFilename(), "");
      file.transferTo(tempFilePath);
    } catch (IOException e) {
      throw new FilepathNotFoundException("Error processing image file");
    }
    return tempFilePath;
  }

  // Validation for image, accepted format .png .jpg .jpeg
  public static boolean isImageFile(MultipartFile file) {
    if (file != null) {
      String fileName = file.getOriginalFilename();
      assert fileName != null;
      return fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg");
    }
    throw new ImageTypeNotValidException("Images should be of type png, jpg, jpeg");
  }

  public static double fileSizeInMB(MultipartFile file) {
    return file.getSize() * BYTES_TO_MEGABYTES;
  }

  public static String getFileExtention(MultipartFile file) {
    try {
      String fileContent = file.getContentType();
      assert fileContent != null;
      String extension = fileContent.split("/")[1];
      return "." + extension;
    } catch (Exception exception) {
      throw new NullPointerException("File is empty");
    }
  }

  // Generating unique filename for the images, eg DIRECT/{posting id}/productImage1.png
  public static String generateUniqueFileNameForImage(
      String sellType, String userName, int productNumber, String fileExtension) {

    return sellType + "/" + userName + "/" + "productImage" + productNumber + fileExtension;
  }

  public static String generateImageURL(String bucketName, String region, String fileName) {
    return "https://" + bucketName + ".s3." + region + ".amazonaws.com" + "/" + fileName;
  }
}
