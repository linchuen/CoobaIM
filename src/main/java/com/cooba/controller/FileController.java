package com.cooba.controller;

import com.cooba.component.FileComponent;
import com.cooba.dto.response.ResultResponse;
import com.cooba.dto.response.UploadFileResponse;
import com.cooba.service.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileComponent fileComponent;

    @PostMapping("/upload")
    public ResultResponse<?> uploadFile(@RequestParam Long roomId, @RequestParam MultipartFile file) {
        UploadFileResponse response = fileComponent.uploadFile(roomId, file);
        return ResultResponse.builder().data(response).build();
    }

    // 下載檔案
    @GetMapping("/download/{roomId}/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long roomId, @PathVariable String fileName) {
        try (InputStream stream = fileComponent.downloadFile(roomId, fileName)) {
            byte[] bytes = stream.readAllBytes();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(bytes);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }


    @DeleteMapping("/delete/{roomId}/{fileName}")
    public ResultResponse<?> deleteFile(@PathVariable Long roomId, @PathVariable String fileName) {
        fileComponent.deleteFile(roomId, fileName);
        return ResultResponse.builder().build();
    }
}
