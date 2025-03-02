package com.cooba.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface MinioService {
    String uploadFile(String bucketName, MultipartFile file) throws Exception;

    String getFileUrl(String bucketName, String fileName) throws Exception;

    InputStream downloadFile(String bucketName, String fileName) throws Exception;

    void deleteFile(String bucketName, String fileName) throws Exception;
}
