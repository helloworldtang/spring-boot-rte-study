package com.tangcheng.editor.rte.ck.editor.controller;

import com.tangcheng.editor.rte.ck.editor.service.DocToHtmlConverterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author tangcheng
 * 2018/04/08
 */
@Controller
public class DocToHtmlController {

    @Autowired
    private DocToHtmlConverterServiceImpl docToHtmlConverterService;


    @PostMapping("/convert/doc/to/html")
    public String convertDocToHtml(@RequestParam("file") MultipartFile file) throws IOException {
        docToHtmlConverterService.convertDocToHtml(file);
        return "redirect:/";
    }

}
