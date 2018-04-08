package com.tangcheng.editor.rte.ck.editor.controller;

import com.tangcheng.editor.rte.ck.editor.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author tangcheng
 * 2017/11/10
 */
@Slf4j
@Controller
public class UploadController {


    @Autowired
    private StorageService storageService;

    @PostMapping("upload")
    public void upload(@RequestParam(value = "upload") MultipartFile multipartFile,
                       HttpServletRequest request,
                       HttpServletResponse response) {
        try {
            String fileUrl = storageService.saveUploadFile(multipartFile);

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
