package com.xudong.im.web.controller;


import com.xudong.im.domain.limit.BlackList;
import com.xudong.im.domain.limit.BlackListQuery;
import com.xudong.im.manage.BlackListMange;
import org.evanframework.dto.ApiResponse;
import org.evanframework.dto.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("blackList/manage")
public class BlackListManageController {

    @Autowired
    private BlackListMange blackListMange;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ApiResponse getForList(BlackListQuery blackListQuery) {
        PageResult<BlackList> result = blackListMange.getForList(blackListQuery);
        return ApiResponse.create(result);
    }

    @RequestMapping(value = "getOne", method = RequestMethod.GET)
    public ApiResponse getOne(@RequestParam("id") Integer id) {
        return ApiResponse.create(blackListMange.getOne(id));
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ApiResponse add(BlackList o) {
        blackListMange.add(o);
        return ApiResponse.create(o);
    }

    @RequestMapping(value = "addGroup", method = RequestMethod.POST)
    public ApiResponse addGroup(@RequestParam("blackLists") String blackListContents) {
        blackListMange.addGroup(blackListContents);
        return ApiResponse.create();
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ApiResponse update(BlackList o) {
        blackListMange.update(o);
        return ApiResponse.create();
    }


    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    public ApiResponse updateStatus(@RequestParam("id") Integer id, @RequestParam("newStatus") Integer newStatus) {
        blackListMange.updateStatus(id, newStatus);
        return ApiResponse.create();
    }

    @RequestMapping(value = "updateStatusGroup", method = RequestMethod.POST)
    public ApiResponse updateStatusGroup(int[] ids, int newStatus) {
        blackListMange.updateStatusGroup(ids, newStatus);
        return ApiResponse.create();
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ApiResponse delete(@RequestParam("id") Integer id) {
        blackListMange.delete(id);
        return ApiResponse.create();
    }
}
