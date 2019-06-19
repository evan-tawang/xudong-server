package com.xudong.im.web.controller;

import com.xudong.core.util.IpUtil;
import com.xudong.im.domain.chat.ChatDTO;
import com.xudong.im.domain.chat.ChatRecord;
import com.xudong.im.domain.chat.ChatRecordQuery;
import com.xudong.im.domain.chat.ChatSession;
import com.xudong.im.domain.user.support.UserAgent;
import com.xudong.im.manage.ChatManage;
import com.xudong.im.session.UserAgentSession;
import org.evanframework.dto.ApiResponse;
import org.evanframework.dto.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
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
    @Autowired
    private UserAgentSession userAgentSession;

    /**
     * 访客发起会话
     * 创建成功则返回 ChatSession对象
     * 访客需要等待则返回 null
     * @param request
     * @return
     */
    @RequestMapping(value = "createSession", method = RequestMethod.POST)
    public ApiResponse<ChatSession> createSession(HttpServletRequest request) {
        UserAgent agent = userAgentSession.get(request);
        ChatSession session = chatManage.createSession(agent, IpUtil.getRemoteIp(request));
        return ApiResponse.create(session);
    }

    /**
     * 发送消息
     * @param chatDTO
     * @param request
     * @return
     */
    @RequestMapping(value = "sendMsg", method = RequestMethod.POST)
    public ApiResponse sendMsg(ChatDTO chatDTO, HttpServletRequest request) {
        UserAgent agent = userAgentSession.get(request);
        ChatRecord chatRecord = chatManage.sendMsg(chatDTO, agent);
        return ApiResponse.create(chatRecord);
    }

    @RequestMapping(value = "disconnect", method = RequestMethod.POST)
    public ApiResponse<PageResult<ChatRecord>> disconnect(String sessionId) {
        chatManage.disconnect(sessionId);
        return ApiResponse.create();
    }

    @RequestMapping(value = "connected", method = RequestMethod.GET)
    public ApiResponse connected(HttpServletRequest request) {
        UserAgent agent = userAgentSession.get(request);
        List<ChatSession> chatSessions = chatManage.connected(agent);
        return ApiResponse.create(chatSessions);
    }

    /**
     * 会话中的聊天记录信息
     * 传递双方id 或者 会话id
     * @param chatRecordQuery
     * @return
     */
    @RequestMapping(value = "history", method = RequestMethod.GET)
    public ApiResponse<PageResult<ChatRecord>> history(ChatRecordQuery chatRecordQuery,HttpServletRequest request) {
        Assert.notNull(chatRecordQuery.getSessionId(),"会话id不能为空");

        if(chatRecordQuery.getPageSize() == 0){
            chatRecordQuery.setPageSize(30);
        }
        chatRecordQuery.setSort("ASC");
        PageResult<ChatRecord> result = chatManage.queryPage(chatRecordQuery);
        return ApiResponse.create(result);
    }

    /**
     * 客服已读
     * @param sessionId
     * @return
     */
    @RequestMapping(value = "read", method = RequestMethod.POST)
    public ApiResponse read(String sessionId) {
        chatManage.read(sessionId);
        return ApiResponse.create();
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
