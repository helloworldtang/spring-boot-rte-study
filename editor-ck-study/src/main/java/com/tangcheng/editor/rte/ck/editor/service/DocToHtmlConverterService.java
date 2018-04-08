package com.tangcheng.editor.rte.ck.editor.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author tangcheng
 * 2018/04/08
 */
public interface DocToHtmlConverterService {
    void convertDocToHtml(MultipartFile file) throws IOException;
}
