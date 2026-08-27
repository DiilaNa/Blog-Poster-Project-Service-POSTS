package com.miniblog.postservice.service.impl;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.miniblog.postservice.service.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {

    @Value("${storage.mode:local}")
    private String storageMode;

    @Value("${storage.local-dir:/home/dilan/.gemini/antigravity/scratch/mini-blog-platform/post-service/uploads}")
    private String localDir;

    @Value("${storage.gcs.bucket-name:mini-blog-platform-covers}")
    private String bucketName;

    @Value("${storage.gcs.project-id:your-gcp-project-id}")
    private String projectId;

    private Storage gcsStorage;

    @Override
    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Cannot upload empty file");
        }

        String fileExtension = getFileExtension(file.getOriginalFilename());
        String generatedFileName = UUID.randomUUID().toString() + fileExtension;

        if ("gcs".equalsIgnoreCase(storageMode)) {
            try {
                return uploadToGcs(file, generatedFileName);
            } catch (Exception e) {
                System.err.println("GCS Upload failed. Falling back to local storage. Error: " + e.getMessage());
                // Fallback to local storage if GCS fails
            }
        }

        return uploadToLocal(file, generatedFileName);
    }

    private String uploadToLocal(MultipartFile file, String fileName) {
        try {
            Path directoryPath = Paths.get(localDir);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            Path filePath = directoryPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);
            // This URL will be served via PostController at /api/v1/posts/images/{filename}
            return "http://localhost:8080/api/v1/posts/images/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Could not store file locally: " + e.getMessage(), e);
        }
    }

    private String uploadToGcs(MultipartFile file, String fileName) throws IOException {
        if (gcsStorage == null) {
            gcsStorage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        }
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        gcsStorage.create(blobInfo, file.getBytes());
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return ".jpg"; // default
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
