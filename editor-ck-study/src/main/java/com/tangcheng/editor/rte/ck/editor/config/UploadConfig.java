package com.tangcheng.editor.rte.ck.editor.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

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
    private String urlPrefix = "/public/image/";

    @PostConstruct
    public void init() throws IOException {
        FileUtils.forceMkdir(new File(this.getStorage()));
    }

}
