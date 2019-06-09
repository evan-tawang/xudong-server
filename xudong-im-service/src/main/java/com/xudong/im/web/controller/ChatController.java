package com.xudong.im.web.controller;

import com.xudong.im.domain.chat.ChatDTO;
import com.xudong.im.domain.chat.ChatRecord;
import com.xudong.im.domain.chat.ChatRecordQuery;
import com.xudong.im.manage.ChatManage;
import com.xudong.im.util.UUIDUtil;
import org.evanframework.dto.ApiResponse;
import org.evanframework.dto.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("chat")
public class ChatController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatManage chatManage;

    @RequestMapping(value = "createSession", method = RequestMethod.POST)
    public ApiResponse<String> createSession(String visitorId, String serviceId, HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();

        LOGGER.info("visitor ip addr:{}", remoteAddr);

        if (StringUtils.isEmpty(visitorId)) {
            visitorId = UUIDUtil.nameUUIDFromBytes(remoteAddr);
        }

        String sessionId = chatManage.createSession(visitorId, serviceId, visitorId);
        return ApiResponse.create(sessionId);
    }

    @RequestMapping(value = "new", method = RequestMethod.POST)
    public ApiResponse add(ChatDTO chatDTO) {
        chatManage.add(chatDTO);
        return ApiResponse.create();
    }

    @RequestMapping(value = "history", method = RequestMethod.GET)
    public ApiResponse<PageResult<ChatRecord>> history(ChatRecordQuery chatRecordQuery) {
        if(chatRecordQuery.getPageSize() == 0){
            chatRecordQuery.setPageSize(30);
        }
        PageResult<ChatRecord> result = chatManage.queryPage(chatRecordQuery);
        return ApiResponse.create(result);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ApiResponse<PageResult<ChatRecord>> getForList(ChatRecordQuery chatRecordQuery) {
        if(chatRecordQuery.getPageSize() == 0){
            chatRecordQuery.setPageSize(10);
        }
        PageResult<ChatRecord> result = chatManage.queryPage(chatRecordQuery);
        return ApiResponse.create(result);
    }
}
