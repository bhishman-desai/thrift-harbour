package tech.group15.thriftharbour.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme