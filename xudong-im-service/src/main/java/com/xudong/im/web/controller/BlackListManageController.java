package com.xudong.im.web.controller;


import com.xudong.im.domain.limit.BlackList;
import com.xudong.im.domain.limit.BlackListQuery;
import com.xudong.im.manage.BlackListManage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.evanframework.dto.ApiResponse;
import org.evanframework.dto.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "黑名单管理")
@RestController
@RequestMapping("blackList/manage")
public class BlackListManageController {

    @Autowired
    private BlackListManage blackListMange;

    @ApiOperation(value = "列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ApiResponse getForList(BlackListQuery blackListQuery) {
        PageResult<BlackList> result = blackListMange.getForList(blackListQuery);
        return ApiResponse.create(result);
    }

    @ApiOperation(value = "获取单个")
    @RequestMapping(value = "getOne", method = RequestMethod.GET)
    public ApiResponse getOne(@RequestParam("id") Integer id) {
        return ApiResponse.create(blackListMange.getOne(id));
    }

    @ApiOperation(value = "添加")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ApiResponse add(BlackList o) {
        blackListMange.add(o);
        return ApiResponse.create(o);
    }

    @ApiOperation(value = "批量添加")
    @RequestMapping(value = "addGroup", method = RequestMethod.POST)
    public ApiResponse addGroup(@ApiParam(value = "需要添加的黑名单，多个以\",\"分割", required = true) @RequestParam("blackLists") String blackListContents) {
        blackListMange.addGroup(blackListContents);
        return ApiResponse.create();
    }

    @ApiOperation(value = "更新")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ApiResponse update(BlackList o) {
        blackListMange.update(o);
        return ApiResponse.create();
    }


    @ApiOperation(value = "更新状态")
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    public ApiResponse updateStatus(@RequestParam("id") Integer id,
                                    @ApiParam(value = "更新后的状态", required = true) @RequestParam("newStatus") Integer newStatus) {
        blackListMange.updateStatus(id, newStatus);
        return ApiResponse.create();
    }

    @ApiOperation(value = "批量更新状态")
    @RequestMapping(value = "updateStatusGroup", method = RequestMethod.POST)
    public ApiResponse updateStatusGroup(@ApiParam(value = "需要更新数据的id数组", required = true) @RequestParam("ids") int[] ids,
                                         @ApiParam(value = "更新后的状态", required = true) @RequestParam("newStatus") int newStatus) {
        blackListMange.updateStatusGroup(ids, newStatus);
        return ApiResponse.create();
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ApiResponse delete(@RequestParam("id") Integer id) {
        blackListMange.delete(id);
        return ApiResponse.create();
    }
}
