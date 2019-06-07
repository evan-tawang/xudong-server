package com.xudong.im.web.controller;


import com.xudong.im.domain.help.TalkSkill;
import com.xudong.im.domain.help.TalkSkillQuery;
import com.xudong.im.manage.TalkSkillMange;
import org.evanframework.dto.ApiResponse;
import org.evanframework.dto.PageResult;
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
@RequestMapping("talkSkill/manage")
public class TalkSkillMangeController {
    @Autowired
    private TalkSkillMange talkSkillMange;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ApiResponse getForList(TalkSkillQuery talkSkillQuery) {
        PageResult<TalkSkill> result = talkSkillMange.getForList(talkSkillQuery);
        return ApiResponse.create(result);
    }

    @RequestMapping(value = "getOne", method = RequestMethod.GET)
    public ApiResponse getOne(@RequestParam("id") Integer id) {
        return ApiResponse.create(talkSkillMange.getOne(id));
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ApiResponse add(TalkSkill o) {
        talkSkillMange.add(o);
        return ApiResponse.create(o);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ApiResponse update(TalkSkill o) {
        talkSkillMange.update(o);
        return ApiResponse.create();
    }


    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    public ApiResponse updateStatus(@RequestParam("id") Integer id, @RequestParam("newStatus") Integer newStatus) {
        talkSkillMange.updateStatus(id, newStatus);
        return ApiResponse.create();
    }

    @RequestMapping(value = "updateStatusGroup", method = RequestMethod.POST)
    public ApiResponse updateStatusGroup(@RequestParam("ids[]") int[] ids, @RequestParam("newStatus") int newStatus) {
        talkSkillMange.updateStatusGroup(ids, newStatus);
        return ApiResponse.create();
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ApiResponse delete(@RequestParam("id") Integer id) {
        talkSkillMange.delete(id);
        return ApiResponse.create();
    }
}
