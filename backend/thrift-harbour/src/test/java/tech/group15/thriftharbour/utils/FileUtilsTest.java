package tech.group15.thriftharbour.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;

import static org.mockito.Mockito.when;

class FileUtilsTest {

    @Mock
    MultipartFile file;


    @Test
    void testGenerateUniqueFileNameForImage() {
        String result = FileUtils.generateUniqueFileNameForImage("sellType", "userName", 0, "fileExtension");
        Assertions.assertEquals("sellType/userName/productImage0fileExtension", result);
    }

    @Test
    void testGenerateImageURL() {
        String result = FileUtils.generateImageURL("bucketName", "region", "fileName");
        Assertions.assertEquals("https://bucketName.s3.region.amazonaws.com/fileName", result);
    }

    @Test
    void testIsImageFile(){
        MockMultipartFile mockFile = new MockMultipartFile("image", "test.jpg", "image/jpg", "test data".getBytes());
        Assertions.assertTrue(FileUtils.isImageFile(mockFile));
    }

    @Test
    void testGetFileExtention(){
        MockMultipartFile mockFile = new MockMultipartFile("image", "test.jpg", "image/jpg", "test data".getBytes());
        Assertions.assertEquals(".jpg", FileUtils.getFileExtention(mockFile));
    }

    @Test
    void testGenerateTempFilePath(){
        MockMultipartFile mockFile = new MockMultipartFile("image", "test.jpg", "image/jpg", "test data".getBytes());
        String result = FileUtils.generateTempFilePath(mockFile).toString();
        boolean isEmp = result.isEmpty();
        Assertions.assertFalse(isEmp);
    }
}
