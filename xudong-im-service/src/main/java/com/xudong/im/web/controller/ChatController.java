package com.xudong.im.web.controller;

import com.xudong.core.util.IpUtil;
import com.xudong.im.domain.chat.ChatDTO;
import com.xudong.im.domain.chat.ChatRecord;
import com.xudong.im.domain.chat.ChatRecordQuery;
import com.xudong.im.domain.chat.ChatSessionVO;
import com.xudong.im.domain.user.support.UserAgent;
import com.xudong.im.manage.ChatManage;
import com.xudong.im.session.UserAgentSession;
import org.evanframework.dto.ApiResponse;
import org.evanframework.dto.OperateResult;
import org.evanframework.dto.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("chat")
public class ChatController {
    private final static Logger log = LoggerFactory.getLogger(ChatController.class);

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
    public ApiResponse<ChatSessionVO> createSession(String connectId, HttpServletRequest request) {

        connectId = StringUtils.isEmpty(connectId) ? IpUtil.getRemoteIp(request) : connectId;

        log.info(">>>>>>>>>>>>> client ip:" + connectId);

        OperateResult data = chatManage.createSession(connectId, request.getRemoteAddr());
        if(data == null){
            return ApiResponse.create();
        }
        return ApiResponse.create(data);
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

    /**
     * 断开连接
     * @param sessionId
     * @return
     */
    @RequestMapping(value = "disconnect", method = RequestMethod.POST)
    public ApiResponse<PageResult<ChatRecord>> disconnect(String sessionId) {
        chatManage.disconnect(sessionId);
        return ApiResponse.create();
    }

    /**
     * 获取已经连接
     * @param request
     * @return
     */
    @RequestMapping(value = "connected", method = RequestMethod.GET)
    public ApiResponse<List<ChatSessionVO>> connected(HttpServletRequest request) {
        UserAgent agent = userAgentSession.get(request);
        List<ChatSessionVO> chatSessions = chatManage.connected(agent);
        return ApiResponse.create(chatSessions);
    }

    /**
     * 未读取条数
     * 会话id
     * @param chatRecordQuery
     * @return
     */
    @RequestMapping(value = "queryNonReadCount", method = RequestMethod.GET)
    public ApiResponse<Long> queryNonReadCount(ChatRecordQuery chatRecordQuery, HttpServletRequest request) {
        Assert.notNull(chatRecordQuery.getSessionId(),"会话id不能为空");

        UserAgent userAgent = userAgentSession.get(request);

        Long count = chatManage.queryNonReadCount(chatRecordQuery, userAgent);

        return ApiResponse.create(count);
    }


    /**
     * 会话中的聊天记录信息
     * 会话id
     * @param sessionId
     * @param currentCount
     * @return
     */
    @RequestMapping(value = "history", method = RequestMethod.GET)
    public ApiResponse<List<ChatRecord>> history(String sessionId, Integer currentCount, HttpServletRequest request) {
        List<ChatRecord> list = chatManage.history(sessionId,currentCount);
        return ApiResponse.create(list);
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

    /**
     * 会话中的聊天记录信息
     * 会话id
     * @param sessionId
     * @return
     */
    @RequestMapping(value = "pullBlack", method = RequestMethod.POST)
    public ApiResponse<PageResult<ChatRecord>> pullBlack(String sessionId,HttpServletRequest request) {
        Assert.notNull(sessionId, "会话id不能为空");

        chatManage.pullBlack(sessionId);

        return ApiResponse.create();
    }

    @RequestMapping(value = "all-history", method = RequestMethod.GET)
    public ApiResponse<PageResult<ChatRecord>> getForList(ChatRecordQuery chatRecordQuery) {
        if(chatRecordQuery.getPageSize() == 0){
            chatRecordQuery.setPageSize(50);
        }
//        if(chatRecordQuery.getEndDate() != null){
//            chatRecordQuery.setEndDate(DateUtils.addDays(chatRecordQuery.getEndDate() ,1));
//        }

        PageResult<ChatRecord> result = chatManage.queryPage(chatRecordQuery);
        return ApiResponse.create(result);
    }
}
