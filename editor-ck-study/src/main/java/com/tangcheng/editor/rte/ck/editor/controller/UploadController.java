package com.tangcheng.editor.rte.ck.editor.controller;

import com.tangcheng.editor.rte.ck.editor.config.UploadConfig;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author tangcheng
 *         2017/11/10
 */
@Slf4j
@Controller
public class UploadController {

    @Autowired
    private UploadConfig uploadConfig;


    @PostMapping("upload")
    public void upload(@RequestParam(value = "upload", required = false) MultipartFile multipartFile,
                       HttpServletRequest request,
                       HttpServletResponse response) {

        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String extensionName = originalFilename.substring(originalFilename.lastIndexOf("."));
            String saveFileName = LocalDateTime.now().toString("yyyyMMddHHmmssSSS") + Thread.currentThread().getId() + extensionName;
            Path path = Paths.get(uploadConfig.getStorage(), saveFileName);
            Files.write(path, multipartFile.getBytes());
            multipartFile.transferTo(Paths.get(uploadConfig.getStorage(), saveFileName).toFile());
            String fileUrl = uploadConfig.getHost() + uploadConfig.getUrlPrefix() + saveFileName;
            log.info(path.toFile().getAbsolutePath());

            response.setContentType("text/html;charset=UTF-8");
            String callback = request.getParameter("CKEditorFuncNum");
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + fileUrl + "',''" + ")");
            out.println("</script>");
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


}
