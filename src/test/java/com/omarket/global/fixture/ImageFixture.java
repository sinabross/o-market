package com.omarket.global.fixture;

import com.omarket.global.factory.FileFactory;
import org.springframework.web.multipart.MultipartFile;

public class ImageFixture {

    public static class Image1 {
        public static final String NAME = "test_img_1.png";
        public static final MultipartFile MULTIPART_FILE = FileFactory.createMultipartFile(NAME);
    }

}
