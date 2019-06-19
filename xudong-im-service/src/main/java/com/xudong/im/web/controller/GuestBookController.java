package com.xudong.im.web.controller;

import com.xudong.im.domain.chat.GuestBook;
import com.xudong.im.domain.chat.GuestBookQuery;
import com.xudong.im.domain.user.support.UserAgent;
import com.xudong.im.manage.GuestBookManage;
import com.xudong.im.session.UserAgentSession;
import org.evanframework.dto.ApiResponse;
import org.evanframework.dto.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 留言管理
 */
@RequestMapping("guestBook")
@RestController
public class GuestBookController {
    private final static Logger LOGGER = LoggerFactory.getLogger(GuestBookController.class);

    @Autowired
    private GuestBookManage guestBookManage;
    @Autowired
    private UserAgentSession userAgentSession;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ApiResponse<Object> save(GuestBook guestBook, HttpServletRequest request) {
        UserAgent userAgent = userAgentSession.get(request);
        guestBookManage.save(request.getRemoteAddr(), guestBook, userAgent);
        return ApiResponse.create();
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ApiResponse<Object> list(GuestBookQuery guestBookQuery) {
        PageResult<GuestBook> result = guestBookManage.getForList(guestBookQuery);
        return ApiResponse.create(result);
    }
}