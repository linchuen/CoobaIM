package com.cooba.component;

import com.cooba.dto.response.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileComponent {

    UploadFileResponse uploadFile(Long roomId, MultipartFile file);

    InputStream downloadFile(Long roomId, String fileName);

    void deleteFile(Long roomId, String fileName);
}
