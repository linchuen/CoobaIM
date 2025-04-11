package com.cooba.controller;

import com.cooba.component.FileComponent;
import com.cooba.dto.response.ResultResponse;
import com.cooba.dto.response.UploadFileResponse;
import com.cooba.service.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileComponent fileComponent;

    @PostMapping("/upload/avatar")
    @ResponseBody
    public ResultResponse<?> uploadAvatar(@RequestParam MultipartFile file) {
        UploadFileResponse response = fileComponent.uploadFile(file);
        return ResultResponse.builder().data(response).build();
    }

    @PostMapping("/upload/{roomId}")
    @ResponseBody
    public ResultResponse<?> uploadFile(@PathVariable Long roomId, @RequestParam MultipartFile file) {
        UploadFileResponse response = fileComponent.uploadFile(roomId, file);
        return ResultResponse.builder().data(response).build();
    }

    // 下載檔案
    @GetMapping("/download/{roomId}/{fileName}")
    @ResponseBody
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

    @GetMapping("/images/{roomId}/{fileName}")
    @ResponseBody
    public ResponseEntity<byte[]> serveImage(@PathVariable Long roomId, @PathVariable String fileName) {
        try (InputStream stream = fileComponent.downloadFile(roomId, fileName)) {
            byte[] bytes = stream.readAllBytes();

            String contentType = Files.probeContentType(Paths.get(fileName));
            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(bytes);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/delete/{roomId}/{fileName}")
    @ResponseBody
    public ResultResponse<?> deleteFile(@PathVariable Long roomId, @PathVariable String fileName) {
        fileComponent.deleteFile(roomId, fileName);
        return ResultResponse.builder().build();
    }
}
