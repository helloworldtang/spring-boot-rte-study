package com.tangcheng.editor.rte.u.editor.controller;

import com.baidu.ueditor.ActionEnter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tangcheng
 * 2017/11/10
 */
@Slf4j
@RestController
public class UploadController {

    @RequestMapping(value = "/static/ueditor/config")
    public String config(HttpServletRequest request) {
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        return new ActionEnter(request, rootPath).exec();
    }

}
