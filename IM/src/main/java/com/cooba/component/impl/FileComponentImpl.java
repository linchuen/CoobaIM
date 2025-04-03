package com.cooba.component.impl;

import com.cooba.annotation.ObjectLayer;
import com.cooba.aop.UserThreadLocal;
import com.cooba.component.FileComponent;
import com.cooba.constant.ErrorEnum;
import com.cooba.dto.response.UploadFileResponse;
import com.cooba.exception.BaseException;
import com.cooba.service.MinioService;
import com.cooba.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

import static com.cooba.constant.Minio.BUCKET_PREFIX;

@Slf4j
@ObjectLayer
@RequiredArgsConstructor
public class FileComponentImpl implements FileComponent {
    private final MinioService minioService;
    private final RoomService roomService;
    private final UserThreadLocal userThreadLocal;

    @Override
    public UploadFileResponse uploadFile(MultipartFile file) {
        String userName = userThreadLocal.getCurrentUserName();
        try {
            String bucketName = BUCKET_PREFIX + userName;
            String fileName = minioService.uploadFile(bucketName, file);
            String fileUrl = minioService.getFileUrl(bucketName, fileName);

            return UploadFileResponse.builder()
                    .fileName(fileName)
                    .url(fileUrl)
                    .build();
        } catch (Exception e) {
            log.error("Failed to upload file for user {}", userName, e);
            throw new RuntimeException("File upload failed", e);
        }
    }

    @Override
    public UploadFileResponse uploadFile(Long roomId, MultipartFile file) {
        long userId = userThreadLocal.getCurrentUserId();
        boolean isRoomMember = roomService.isRoomMember(roomId, userId);
        if (!isRoomMember) throw new BaseException(ErrorEnum.FORBIDDEN);

        try {
            String bucketName = BUCKET_PREFIX + roomId;
            String fileName = minioService.uploadFile(bucketName, file);
            String fileUrl = minioService.getFileUrl(bucketName, fileName);

            return UploadFileResponse.builder()
                    .fileName(fileName)
                    .url(fileUrl)
                    .build();
        } catch (Exception e) {
            log.error("Failed to upload file for room {}", roomId, e);
            throw new RuntimeException("File upload failed", e);
        }
    }

    @Override
    public InputStream downloadFile(Long roomId, String fileName) {
        long userId = userThreadLocal.getCurrentUserId();
        boolean isRoomMember = roomService.isRoomMember(roomId, userId);
        if (!isRoomMember) throw new BaseException(ErrorEnum.FORBIDDEN);

        try {
            String bucketName = BUCKET_PREFIX + roomId;
            return minioService.downloadFile(bucketName, fileName);
        } catch (Exception e) {
            log.error("Failed to download file {} for room {}", fileName, roomId, e);
            throw new RuntimeException("File download failed", e);
        }
    }

    @Override
    public void deleteFile(Long roomId, String fileName) {
        long userId = userThreadLocal.getCurrentUserId();
        boolean isRoomMember = roomService.isRoomMember(roomId, userId);
        if (!isRoomMember) throw new BaseException(ErrorEnum.FORBIDDEN);

        try {
            String bucketName = BUCKET_PREFIX + roomId;
            minioService.deleteFile(bucketName, fileName);
            log.info("File {} deleted successfully for room {}", fileName, roomId);
        } catch (Exception e) {
            log.error("Failed to delete file {} for room {}", fileName, roomId, e);
            throw new RuntimeException("File deletion failed", e);
        }
    }
}
