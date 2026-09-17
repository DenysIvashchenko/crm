package com.agro.crm.features.contract;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    @Value("${app.upload.dir:uploads/contracts}")
    private String uploadDir;

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;
    private static final String ALLOWED_TYPE = "application/pdf";

    public StoredFile store(MultipartFile file, Long contractId) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File too large. Max 10MB");
        }

        if (!ALLOWED_TYPE.equals(file.getContentType())) {
            throw new IllegalArgumentException("Only PDF files are allowed");
        }

        try {
            Path dirPath = Paths.get(uploadDir);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            String originalName = file.getOriginalFilename();
            String storedName = "contract_" + contractId + "_" + UUID.randomUUID() + ".pdf";
            Path filePath = dirPath.resolve(storedName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return new StoredFile(originalName, filePath.toString(), file.getSize());

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + e.getMessage(), e);
        }
    }

    public Resource load(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Resource resource = new UrlResource(path.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new FileNotFoundException("File not found: " + filePath);
            }

            return resource;

        } catch (IOException e) {
            throw new RuntimeException("Failed to load file: " + e.getMessage(), e);
        }
    }

    public record StoredFile(String originalName, String path, Long size) {}
}
