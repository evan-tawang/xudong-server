package com.xudong.im.web.controller;

import com.xudong.im.domain.chat.ChatDTO;
import com.xudong.im.domain.chat.ChatRecord;
import com.xudong.im.domain.chat.ChatRecordQuery;
import com.xudong.im.domain.chat.ChatSession;
import com.xudong.im.domain.user.StaffAgent;
import com.xudong.im.domain.user.UserTypeEnum;
import com.xudong.im.manage.ChatManage;
import com.xudong.core.util.UUIDUtil;
import com.xudong.im.session.StaffAgentSession;
import org.evanframework.dto.ApiResponse;
import org.evanframework.dto.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("chat")
public class ChatController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatManage chatManage;
    @Autowired
    private StaffAgentSession staffAgentSession;

    /**
     * 访客发起会话
     * 创建成功则返回 ChatSession对象
     * 访客需要等待则返回 null
     * @param request
     * @return
     */
    @RequestMapping(value = "createSession", method = RequestMethod.POST)
    public ApiResponse<ChatSession> createSession(HttpServletRequest request) {
        StaffAgent agent = staffAgentSession.get(request);
        ChatSession session = chatManage.createSession(agent, request.getRemoteAddr());
        return ApiResponse.create(session);
    }


    /**
     * 发送消息
     * @param chatDTO
     * @param request
     * @return
     */
    @RequestMapping(value = "sendMsg", method = RequestMethod.POST)
    public ApiResponse sendMsg(@RequestBody ChatDTO chatDTO,HttpServletRequest request) {
        StaffAgent agent = staffAgentSession.get(request);
        chatManage.sendMsg(chatDTO, agent);
        return ApiResponse.create();
    }

    /**
     * 会话中的聊天记录信息
     * 传递双方id 或者 会话id
     * @param chatRecordQuery
     * @return
     */
    @RequestMapping(value = "history", method = RequestMethod.GET)
    public ApiResponse<PageResult<ChatRecord>> history(ChatRecordQuery chatRecordQuery,HttpServletRequest request) {
        if(StringUtils.isEmpty(chatRecordQuery.getSessionId())){
            Assert.notNull(chatRecordQuery.getConnectorId(),"连接人id不能为空");

            StaffAgent agent = staffAgentSession.get(request);
            String serviceId = null, visitorId = null;

            if(agent == null){
                return ApiResponse.create(PageResult.create(chatRecordQuery, new ArrayList<ChatRecord>(), 0));
            }

            if(UserTypeEnum.SERVICE.getValue().equals(agent.getUserType())){
                serviceId = String.valueOf(agent.getId());
                visitorId = chatRecordQuery.getConnectorId();
            } else {
                visitorId = String.valueOf(agent.getId());
                serviceId = chatRecordQuery.getConnectorId();
            }
            chatRecordQuery.setServiceId(serviceId);
            chatRecordQuery.setVisitorId(visitorId);
        }
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
