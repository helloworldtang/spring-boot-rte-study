package com.tangcheng.editor.rte.ck.editor.service;

import com.tangcheng.editor.rte.ck.editor.domain.BlogCKEditor;
import com.tangcheng.editor.rte.ck.editor.repository.BlogRepository;
import fr.opensagres.xdocreport.core.io.internal.StringBuilderOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tangcheng
 * 2018/04/08
 */
@Slf4j
@Service
public class DocToHtmlConverterServiceImpl implements DocToHtmlConverterService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private StorageService storageService;

    @Override
    public void convertDocToHtml(MultipartFile file) throws IOException {
        StringBuilderOutputStream stringBuilderOutputStream = null;
        try {
            XWPFDocument document = new XWPFDocument(file.getInputStream());
            List<XWPFPictureData> xwpfPictureDatas = document.getAllPictures();
            Map<String, String> picUrlMaps = new HashMap<>();
            for (XWPFPictureData xwpfPictureData : xwpfPictureDatas) {
                String picName = xwpfPictureData.getFileName();
                InputStream picIs = xwpfPictureData.getPackagePart().getInputStream();
                String imgUrl = storageService.savePic(picName,picIs);
                log.info("{}:{}", picName, imgUrl);
                picUrlMaps.put(picName, imgUrl);
            }
            XHTMLOptions options = XHTMLOptions.create();
            XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
            stringBuilderOutputStream = new StringBuilderOutputStream();
            xhtmlConverter.convert(document, stringBuilderOutputStream, options);
            String str = stringBuilderOutputStream.toString();
            log.info(str);
            str = str.replaceAll("<html><head>", "");
            str = str.replaceAll("</head><body>", "");
            str = str.replaceAll("</body></html>", "");
            String htmlContent = transferToRightPicUrl(str, picUrlMaps);
            BlogCKEditor blogCKEditor = new BlogCKEditor();
            blogCKEditor.setContent(htmlContent);
            Date now = new Date();
            blogCKEditor.setCreateTime(now);
            blogCKEditor.setUpdateTime(now);
            blogRepository.save(blogCKEditor);
        } finally {
            if (stringBuilderOutputStream != null) {
                stringBuilderOutputStream.close();
            }
        }
    }

    private String transferToRightPicUrl(String original, Map<String, String> picUrlMaps) {
        for (Map.Entry<String, String> entry : picUrlMaps.entrySet()) {
            String originUrl = "word/media/" + entry.getKey();
            String replaceUrl = entry.getValue();
            original = original.replaceAll(originUrl, replaceUrl);
        }
        return original;
    }


}
