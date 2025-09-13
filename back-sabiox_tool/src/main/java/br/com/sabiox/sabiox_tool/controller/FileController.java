package br.com.sabiox.sabiox_tool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/uploads")
public class FileController {

    @Value("${upload.dir}")
    private String uploadDir;

    @GetMapping("/avatars/{fileName}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(uploadDir, fileName);
            byte[] image = Files.readAllBytes(filePath);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(image);
        } catch (IOException e) {
            throw new RuntimeException("Error loading image", e);
        }
    }
}
