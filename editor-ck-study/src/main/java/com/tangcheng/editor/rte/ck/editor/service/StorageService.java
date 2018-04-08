package com.tangcheng.editor.rte.ck.editor.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author tangcheng
 * 2018/04/08
 */
public interface StorageService {
    String saveUploadFile(MultipartFile multipartFile) throws IOException;

    String savePic(String picName, InputStream picIs) throws IOException;
}
