package com.xudong.im.web.controller;

import com.xudong.im.manage.SensitiveWordManage;
import org.evanframework.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Evan.Shen
 * @since 2019/6/7
 */
@RestController
@RequestMapping("sensitiveWord/manage")
public class SensitiveWordManageController {
    @Autowired
    private SensitiveWordManage sensitiveWordMange;

    @PostMapping(value = "save")
    public ApiResponse save(@RequestParam("words") String words) {
        sensitiveWordMange.save(words);
        return ApiResponse.create();
    }

    @GetMapping(value = "get")
    public ApiResponse get() {
        String words = sensitiveWordMange.get();
        return ApiResponse.create(words);
    }
}
