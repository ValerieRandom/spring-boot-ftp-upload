package com.example.demo.controller;

import com.example.demo.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/upload")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping
    public ResponseEntity<String> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        try {
            // SFTP 連接信息
            String host = "192.168.33.10"; // SFTP 主機
            int port = 22; // SFTP 默認端口
            String user = "vagrant"; // 用戶名
            String password = "vagrant"; // 密碼
            String remoteDir = "/opt/java/uploads"; // 遠程目錄

            List<String> fileNames = new ArrayList<>();
            List<byte[]> fileBytesList = new ArrayList<>();

            // 構建文件名列表和文件內容列表
            for (MultipartFile file : files) {
                fileNames.add(file.getOriginalFilename());
                fileBytesList.add(file.getBytes());
            }

            // 上傳多個文件到 SFTP
            documentService.uploadMultipleFilesToSftp(host, port, user, password, remoteDir, fileNames, fileBytesList);

            return ResponseEntity.ok("Files uploaded successfully");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }
}
