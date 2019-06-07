package com.xudong.im.web.controller;


import com.xudong.im.manage.TalkSkillMange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class BlackListManageController {

    @Autowired
    private TalkSkillMange testService;


}
