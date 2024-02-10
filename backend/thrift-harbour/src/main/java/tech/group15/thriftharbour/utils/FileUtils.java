package tech.group15.thriftharbour.utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    private FileUtils(){}

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
        throw new NullPointerException();
    }

    public static double fileSizeInMB(MultipartFile file){
        return file.getSize()* 0.000_001;
    }
}
