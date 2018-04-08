package com.tangcheng.editor.rte.ck.editor.service;

import com.tangcheng.editor.rte.ck.editor.config.UploadConfig;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author tangcheng
 * 2018/04/08
 */
@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private UploadConfig uploadConfig;

    @Override
    public String saveUploadFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String extensionName = originalFilename.substring(originalFilename.lastIndexOf("."));
        String saveFileName = LocalDateTime.now().toString("yyyyMMddHHmmssSSS") + Thread.currentThread().getId() + extensionName;
        Path path = Paths.get(uploadConfig.getStorage(), saveFileName);
        Files.write(path, multipartFile.getBytes());
        String fileUrl = uploadConfig.getHost() + uploadConfig.getUrlPrefix() + saveFileName;
        log.info(path.toFile().getAbsolutePath());
        return fileUrl;
    }

    @Override
    public String savePic(String picName, InputStream picIs) throws IOException {
        String extensionName = picName.substring(picName.lastIndexOf("."));
        String saveFileName = LocalDateTime.now().toString("yyyyMMddHHmmssSSS") + Thread.currentThread().getId() + extensionName;
        Path path = Paths.get(uploadConfig.getStorage(), saveFileName);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = picIs.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        Files.write(path, out.toByteArray());
        String fileUrl = uploadConfig.getHost() + uploadConfig.getUrlPrefix() + saveFileName;
        log.info("file path in server :{}", path.toFile().getAbsolutePath());
        return fileUrl;
    }


}
