package com.xudong.im.data.mongo;

import com.xudong.im.constant.CommonConstant;
import com.xudong.im.domain.chat.ChatSession;
import com.xudong.im.domain.chat.ChatSessionQuery;
import com.xudong.core.util.MongoUtil;
import org.evanframework.dto.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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

    public ChatSession load(String id) {
        return mongoTemplate.findById(id, ChatSession.class, COLLECTION_NAME);
    }

    public ChatSession insert(ChatSession o) {
        if (o == null) {
            return null;
        }
        o.setGmtCreate(new Date());
        mongoTemplate.insert(o, COLLECTION_NAME);
        return o;
    }

    public List<ChatSession> queryList(ChatSessionQuery chatSessionQuery) {
        Query query = buildQuery(chatSessionQuery);
        return mongoTemplate.find(query, ChatSession.class, COLLECTION_NAME);
    }

    public PageResult<ChatSession> queryPage(ChatSessionQuery chatSessionQuery) {
        if (chatSessionQuery.getPageSize() == 0) {
            chatSessionQuery.setPageSize(CommonConstant.DEFAULT_PAGE_SIZE);
        }

        Query query = buildQuery(chatSessionQuery);

        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "gmtCreate"));
        PageRequest pageRequest = new PageRequest(chatSessionQuery.getPageNo() - 1, chatSessionQuery.getPageSize(), sort);
        query.with(pageRequest);

        List<ChatSession> list = mongoTemplate.find(query, ChatSession.class, COLLECTION_NAME);
        if (CollectionUtils.isEmpty(list)) {
            return PageResult.create(chatSessionQuery, new ArrayList<>(), 0);
        }
        long count = mongoTemplate.count(query, COLLECTION_NAME);
        return PageResult.create(chatSessionQuery, list, count);
    }

    private Query buildQuery(ChatSessionQuery chatSessionQuery) {
        Query query = new Query();

        MongoUtil.buildQueryForIn(query, "id", chatSessionQuery.getIdArray());
        MongoUtil.buildQueryForIs(query, "staffId", chatSessionQuery.getStaffId());
        MongoUtil.buildQueryForIs(query, "visitorId", chatSessionQuery.getVisitorId());
        MongoUtil.buildQueryForIs(query, "visitorIp", chatSessionQuery.getVisitorIp());
        return query;
    }

    public void update(ChatSession chatSession) {
        Assert.notNull(chatSession.getId(),"id不能为空");

        Query query = new Query(Criteria.where("_id").in(chatSession.getId()));
        Update update = new Update();

        MongoUtil.buildUpdate(update,"connectStartTime",chatSession.getConnectStartTime());
        MongoUtil.buildUpdate(update,"connectEndTime",chatSession.getConnectEndTime());

        mongoTemplate.updateMulti(query, update, COLLECTION_NAME);
    }
}
