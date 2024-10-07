package com.example.demo.model;

import java.time.LocalDateTime;

public class Document {
    private Long id;
    private String name;  // 原先的文件名
    private String type;  // 文件类型
    private LocalDateTime uploadTime;  // 文件上传时间
    private String fileName;  // 文件在服务器上的名称
    private String filePath;  // 文件在服务器上的存储路径

    // 默認構造函數
    public Document() {}

    // 全參數構造函數
    public Document(Long id, String name, String type, LocalDateTime uploadTime, String fileName, String filePath) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.uploadTime = uploadTime;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    // Getter 和 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", uploadTime=" + uploadTime +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
