package com.xudong.im.web.controller;


import com.xudong.im.domain.limit.BlackList;
import com.xudong.im.domain.limit.BlackListQuery;
import com.xudong.im.manage.BlackListManage;
import com.xudong.im.service.BlacklistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.evanframework.dto.ApiResponse;
import org.evanframework.dto.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("黑名单管理")
@RestController
@RequestMapping("blackList")
public class BlackListController {

    @Autowired
    private BlackListManage blackListMange;

    @Autowired
    private BlacklistService blacklistService;

    @ApiOperation(value = "列表")
    @GetMapping(value = "manage/list")
    public ApiResponse getForList(BlackListQuery blackListQuery) {
        PageResult<BlackList> result = blackListMange.getForList(blackListQuery);
        return ApiResponse.create(result);
    }

    @ApiOperation(value = "获取单个")
    @GetMapping(value = "manage/getOne")
    public ApiResponse getOne(@RequestParam("id") Integer id) {
        return ApiResponse.create(blackListMange.getOne(id));
    }

    @ApiOperation(value = "添加")
    @PostMapping(value = "manage/add")
    public ApiResponse add(BlackList o) {
        blackListMange.add(o);
        return ApiResponse.create(o);
    }

    @ApiOperation(value = "批量添加")
    @PostMapping(value = "manage/addGroup")
    public ApiResponse addGroup(@ApiParam(value = "需要添加的黑名单，多个以\",\"分割", required = true) @RequestParam("blackLists") String blackListContents) {
        blackListMange.addGroup(blackListContents);
        return ApiResponse.create();
    }

    @ApiOperation(value = "更新")
    @PostMapping(value = "manage/update")
    public ApiResponse update(BlackList o) {
        blackListMange.update(o);
        return ApiResponse.create();
    }


    @ApiOperation(value = "更新状态")
    @PostMapping(value = "manage/updateStatus")
    public ApiResponse updateStatus(@RequestParam("id") Integer id,
                                    @ApiParam(value = "更新后的状态", required = true) @RequestParam("newStatus") Integer newStatus) {
        blackListMange.updateStatus(id, newStatus);
        return ApiResponse.create();
    }

    @ApiOperation(value = "批量更新状态")
    @PostMapping(value = "manage/updateStatusGroup")
    public ApiResponse updateStatusGroup(@ApiParam(value = "需要更新数据的id数组", required = true) @RequestParam("ids") int[] ids,
                                         @ApiParam(value = "更新后的状态", required = true) @RequestParam("newStatus") int newStatus) {
        blackListMange.updateStatusGroup(ids, newStatus);
        return ApiResponse.create();
    }

    @ApiOperation(value = "删除")
    @PostMapping(value = "manage/delete")
    public ApiResponse delete(@RequestParam("id") Integer id) {
        blackListMange.delete(id);
        return ApiResponse.create();
    }

    @ApiOperation(value = "从其他模块将一个地址或账号拉黑")
    @PostMapping(value = "service/setBlock")
    public ApiResponse setBlock(@ApiParam(value = "要设置成黑名单的账号或ip", required = true) @RequestParam("content") String content) {
        blackListMange.setBlock(content);
        return ApiResponse.create();
    }
}
