package com.xudong.im.web.controller;


import com.xudong.im.domain.help.TalkSkill;
import com.xudong.im.domain.help.TalkSkillQuery;
import com.xudong.im.manage.TalkSkillManage;
import com.xudong.im.service.TalkSkillService;
import org.evanframework.dto.ApiResponse;
import org.evanframework.dto.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Evan.Shen
 * @since 2019/6/7
 */
@RestController
@RequestMapping("talkSkill")
public class TalkSkillController {
    @Autowired
    private TalkSkillManage talkSkillMange;

    @Autowired
    private TalkSkillService talkSkillService;

    @GetMapping(value = "manage/list")
    public ApiResponse getForList(TalkSkillQuery talkSkillQuery) {
        PageResult<TalkSkill> result = talkSkillMange.getForList(talkSkillQuery);
        return ApiResponse.create(result);
    }

    @GetMapping(value = "manage/getOne")
    public ApiResponse getOne(@RequestParam("id") Integer id) {
        return ApiResponse.create(talkSkillMange.getOne(id));
    }

    @PostMapping(value = "manage/add")
    public ApiResponse add(TalkSkill o) {
        talkSkillMange.add(o);
        return ApiResponse.create(o);
    }

    @PostMapping(value = "manage/update")
    public ApiResponse update(TalkSkill o) {
        talkSkillMange.update(o);
        return ApiResponse.create();
    }


    @PostMapping(value = "manage/updateStatus")
    public ApiResponse updateStatus(@RequestParam("id") Integer id, @RequestParam("newStatus") Integer newStatus) {
        talkSkillMange.updateStatus(id, newStatus);
        return ApiResponse.create();
    }

    @PostMapping(value = "manage/updateStatusGroup")
    public ApiResponse updateStatusGroup(@RequestParam("ids") int[] ids, @RequestParam("newStatus") int newStatus) {
        talkSkillMange.updateStatusGroup(ids, newStatus);
        return ApiResponse.create();
    }

    @PostMapping(value = "manage/delete")
    public ApiResponse delete(@RequestParam("id") Integer id) {
        talkSkillMange.delete(id);
        return ApiResponse.create();
    }

    @PostMapping(value = "manage/refreshCache")
    public ApiResponse refreshCache() {
        talkSkillMange.refreshCache();
        return ApiResponse.create();
    }

    @GetMapping(value = "service/list")
    public ApiResponse list() {
        List<TalkSkill> list = talkSkillService.getForList();
        return ApiResponse.create(list);
    }
}
