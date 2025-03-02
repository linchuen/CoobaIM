package com.cooba.service.impl;

import com.cooba.service.MinioService;
import io.minio.MinioClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {MinioServiceImpl.class})
class MinioServiceImplTest {
    @Autowired
    MinioService minioService;
    @MockitoBean
    MinioClient minioClient;


    @Test
    void uploadFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "sample.txt",
                "text/plain",
                "Hello, MinIO!".getBytes()
        );

        String fileName = minioService.uploadFile("bucket", file);
        Assertions.assertNotNull(fileName);
    }

    @Test
    void getFileUrl() {
    }

    @Test
    void downloadFile() {
    }

    @Test
    void deleteFile() {
    }
}