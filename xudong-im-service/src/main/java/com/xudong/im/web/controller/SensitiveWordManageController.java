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
    private SensitiveWordManage sensitiveWordManage;

    @PostMapping(value = "save")
    public ApiResponse save(@RequestParam("words") String words) {
        sensitiveWordManage.save(words);
        return ApiResponse.create();
    }

    @GetMapping(value = "get")
    public ApiResponse get() {
        String words = sensitiveWordManage.get();
        return ApiResponse.create(words);
    }

    @GetMapping(value = "refreshCache")
    public ApiResponse refreshCache() {
        String words = sensitiveWordManage.get();
        return ApiResponse.create(words);
    }
}
