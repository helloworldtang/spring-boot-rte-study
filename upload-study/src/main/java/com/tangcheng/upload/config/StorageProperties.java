package com.tangcheng.upload.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author tangcheng
 * 2017/11/29
 */
@Data
@NoArgsConstructor
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";

}
