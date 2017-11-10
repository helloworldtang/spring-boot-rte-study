package com.tangcheng.editor.rte.markdown.editor.md.controller;

import com.tangcheng.editor.rte.markdown.editor.md.config.UploadConfig;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tangcheng
 * 2017/11/10
 */
@Slf4j
@RestController
public class UploadController {

    @Autowired
    private UploadConfig uploadConfig;


    @PostMapping("upload")
    public Map<String, Object> upload(@RequestParam(value = "editormd-image-file", required = false) MultipartFile multipartFile) {
        Map<String, Object> result = new HashMap<>();
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String extensionName = originalFilename.substring(originalFilename.lastIndexOf("."));
            String saveFileName = LocalDateTime.now().toString("yyyyMMddHHmmssSSS") + Thread.currentThread().getId() + extensionName;
            Path path = Paths.get(uploadConfig.getStorage(), saveFileName);
            Files.write(path, multipartFile.getBytes());
            multipartFile.transferTo(Paths.get(uploadConfig.getStorage(), saveFileName).toFile());
            String fileUrl = uploadConfig.getHost() + uploadConfig.getUrlPrefix() + saveFileName;
            log.info(path.toFile().getAbsolutePath());
            result.put("success", 1);
            result.put("message", "success");
            result.put("url", fileUrl);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.put("success", 0);
            result.put("message", e.getMessage());
        }
        return result;
    }


}
