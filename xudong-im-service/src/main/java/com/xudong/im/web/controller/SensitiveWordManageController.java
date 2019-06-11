package com.xudong.im.web.controller;

import com.xudong.im.manage.SensitiveWordManage;
import org.evanframework.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Evan.Shen
 * @since 2019/6/7
 */
@RestController
@RequestMapping("sensitiveWord/manage")
public class SensitiveWordManageController {
    @Autowired
    private SensitiveWordManage sensitiveWordMange;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ApiResponse save(@RequestParam("words") String words) {
        sensitiveWordMange.save(words);
        return ApiResponse.create();
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public ApiResponse get() {
        String[] words = sensitiveWordMange.get();
        return ApiResponse.create(words);
    }
}
