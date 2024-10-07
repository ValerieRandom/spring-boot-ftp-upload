package com.example.demo.service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class DocumentService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentService.class);

    // 固定的 user_ID
    private static final String USER_ID = "1111QQQ";

    public void uploadMultipleFilesToSftp(String host, int port, String user, String password, String remoteDir, List<String> fileNames, List<byte[]> fileBytesList) throws Exception {
        Session session = null;
        ChannelSftp channelSftp = null;

        try {
            // 設置 JSch
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            session.setPassword(password);

            // 設置配置來跳過主機鍵檢查
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            // 連接到 SFTP 服務器
            session.connect();
            logger.info("Connected to SFTP server.");

            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            // 檢查並創建遠程目錄
            try {
                channelSftp.cd(remoteDir);
            } catch (Exception e) {
                logger.info("Remote directory does not exist. Creating: {}", remoteDir);
                channelSftp.mkdir(remoteDir);
                channelSftp.cd(remoteDir);
            }

            // 逐個文件上傳
            for (int i = 0; i < fileNames.size(); i++) {
                String originalFileName = fileNames.get(i);
                byte[] fileBytes = fileBytesList.get(i);

                // 生成時間戳和編號，並修改文件名
                String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String newFileName = USER_ID + " - " + (i + 1) + " _ " + timeStamp + fileExtension;
                logger.info("File will be uploaded as: {}", newFileName);

                // 將文件上傳到 SFTP
                try (InputStream inputStream = new ByteArrayInputStream(fileBytes)) {
                    channelSftp.put(inputStream, newFileName);
                }
                logger.info("File uploaded successfully to SFTP: {}/{}", remoteDir, newFileName);
            }

        } catch (Exception e) {
            logger.error("Failed to upload files to SFTP server.", e);
            throw new Exception("Failed to upload files to SFTP.", e);
        } finally {
            if (channelSftp != null) {
                channelSftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }
}
