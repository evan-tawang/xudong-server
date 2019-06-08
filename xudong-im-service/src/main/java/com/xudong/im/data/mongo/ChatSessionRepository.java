package com.xudong.im.data.mongo;

import com.xudong.im.constant.CommonConstant;
import com.xudong.im.domain.chat.ChatSession;
import com.xudong.im.domain.chat.ChatSessionQuery;
import com.xudong.im.util.MongoUtil;
import org.evanframework.dto.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 聊天会话
 */
@Repository
public class ChatSessionRepository {

    private static final String COLLECTION_NAME = "chat_session";

    @Autowired
    private MongoTemplate mongoTemplate;

    public ChatSession insert(ChatSession o) {
        if (o == null) {
            return null;
        }
        o.setGmtCreate(new Date());
        mongoTemplate.insert(o, COLLECTION_NAME);
        return o;
    }

    public List<ChatSession> queryList(ChatSessionQuery ChatSessionQuery) {
        Query query = buildQuery(ChatSessionQuery);
        return mongoTemplate.find(query, ChatSession.class, COLLECTION_NAME);
    }

    public PageResult<ChatSession> queryPage(ChatSessionQuery ChatSessionQuery) {
        if (ChatSessionQuery.getPageSize() == 0) {
            ChatSessionQuery.setPageSize(CommonConstant.DEFAULT_PAGE_SIZE);
        }

        Query query = buildQuery(ChatSessionQuery);

        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "gmtCreate"));
        PageRequest pageRequest = new PageRequest(ChatSessionQuery.getPageNo() - 1, ChatSessionQuery.getPageSize(), sort);
        query.with(pageRequest);

        List<ChatSession> list = mongoTemplate.find(query, ChatSession.class, COLLECTION_NAME);
        if (CollectionUtils.isEmpty(list)) {
            return PageResult.create(ChatSessionQuery, new ArrayList<>(), 0);
        }
        long count = mongoTemplate.count(query, COLLECTION_NAME);
        return PageResult.create(ChatSessionQuery, list, count);
    }

    private Query buildQuery(ChatSessionQuery ChatSessionQuery) {
        Query query = new Query();

        MongoUtil.buildQueryForIs(query, "serviceId", ChatSessionQuery.getServiceId());
        MongoUtil.buildQueryForIs(query, "visitorId", ChatSessionQuery.getVisitorId());
        MongoUtil.buildQueryForIs(query, "visitorIp", ChatSessionQuery.getVisitorIp());
        return query;
    }

}
