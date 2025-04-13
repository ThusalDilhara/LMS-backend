package com.bodima.project_lms.service.Impl;

import com.bodima.project_lms.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.allowed-types}")
    private List<String> allowedTypes;

    public String storeFile(MultipartFile file) throws IOException {
        // 1. Validate file type
        if (!allowedTypes.contains(file.getContentType())) {
            throw new IllegalArgumentException("Invalid file type. Allowed: " + allowedTypes);
        }

        // 2. Create upload directory if it doesn't exist
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        // 3. Generate a unique filename
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir).resolve(fileName);

        // 4. Save the file locally
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 5. Return the file URL (relative path or full server URL)
        return "/course-materials/" + fileName;
    }

    // Delete a file (optional)
    public void deleteFile(String fileUrl) throws IOException {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        Files.deleteIfExists(filePath);
    }
}