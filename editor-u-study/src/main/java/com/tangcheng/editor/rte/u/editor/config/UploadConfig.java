package com.tangcheng.editor.rte.u.editor.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author tangcheng
 * 2017/11/10
 */
@Data
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "upload.file.path")
public class UploadConfig {
    private String storage;
    private String host;
    /**
     * 与config.json中的imageUrlPrefix对应的值保持一致
     */
    private String urlPrefix = "/public/";
}
