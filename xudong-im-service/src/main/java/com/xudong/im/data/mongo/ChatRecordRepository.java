package com.xudong.im.data.mongo;

import com.xudong.core.util.MongoUtil;
import com.xudong.core.util.UUIDUtil;
import com.xudong.im.constant.CommonConstant;
import com.xudong.im.domain.chat.ChatRecord;
import com.xudong.im.domain.chat.ChatRecordQuery;
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
 * 聊天记录
 */
@Repository
public class ChatRecordRepository {
    private static final String COLLECTION_NAME = "chat_record";


    @Autowired
    private MongoTemplate mongoTemplate;

    public ChatRecord insert(ChatRecord o) {
        if (o == null) {
            return null;
        }
        o.setGmtCreate(new Date());
        mongoTemplate.insert(o, getCollectionName(o.getSessionId()));
        return o;
    }

    public void insert(String sessionId, List<ChatRecord> list) {
        Assert.hasLength(sessionId, "会话id不能为空");
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(o -> {
            o.setSessionId(sessionId);
            insert(o);
        });
    }

//    public ChatRecord load(String id) {
//        Assert.hasLength(id, "id不能为空");
//
//        return mongoTemplate.findById(id, ChatRecord.class, getCollectionName( sessionId));
//    }

    public void updateIsRead(String sessionId) {
        Query query = new Query(Criteria.where("sessionId").in(sessionId));
        Update update = new Update();
        update.set("read", true);

        mongoTemplate.updateMulti(query, update, getCollectionName( sessionId));
    }

    public List<ChatRecord> queryList(ChatRecordQuery chatRecordQuery) {
        Query query = buildQuery(chatRecordQuery);
        return mongoTemplate.find(query, ChatRecord.class, getCollectionName(chatRecordQuery.getSessionId()));
    }

    public long queryCount(ChatRecordQuery chatRecordQuery){
        Query query = buildQuery(chatRecordQuery);
        return mongoTemplate.count(query, getCollectionName(chatRecordQuery.getSessionId()));
    }

    public PageResult<ChatRecord> queryPage(ChatRecordQuery chatRecordQuery) {
        if (chatRecordQuery.getPageSize() == 0) {
            chatRecordQuery.setPageSize(CommonConstant.DEFAULT_PAGE_SIZE);
        }

        Query query = buildQuery(chatRecordQuery);

        Sort.Direction direction = Sort.Direction.ASC;
        if (!StringUtils.isEmpty(chatRecordQuery.getSort()) && Sort.Direction.DESC.name().equals(chatRecordQuery.getSort())) {
            direction = Sort.Direction.DESC;
        }

        String sortCode = StringUtils.isEmpty(chatRecordQuery.getSortCode()) ? "gmtCreate" : chatRecordQuery.getSortCode();
        Sort sort = new Sort(new Sort.Order(direction, sortCode));
        PageRequest pageRequest = new PageRequest(chatRecordQuery.getPageNo() - 1, chatRecordQuery.getPageSize(), sort);
        query.with(pageRequest);

        List<ChatRecord> list = mongoTemplate.find(query, ChatRecord.class, getCollectionName(chatRecordQuery.getSessionId()));
        if (CollectionUtils.isEmpty(list)) {
            return PageResult.create(chatRecordQuery, new ArrayList<>(), 0);
        }
        long count = mongoTemplate.count(query,  getCollectionName(chatRecordQuery.getSessionId()));
        return PageResult.create(chatRecordQuery, list, count);
    }

    public List<ChatRecord> history(String sessionId, Integer currentCount) {
        Assert.notNull(sessionId, "sessionId 不能为空");

        Query query = new Query(Criteria.where("sessionId").in(sessionId));
        if(currentCount != null){
            query.skip(currentCount);
        }
        query.limit(30);

        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = new Sort(new Sort.Order(direction, "gmtCreate"));

        query.with(sort);

        return mongoTemplate.find(query, ChatRecord.class, getCollectionName(sessionId));
    }

    private Query buildQuery(ChatRecordQuery chatRecordQuery) {
        Query query = new Query();

        MongoUtil.buildQueryForIs(query, "read", chatRecordQuery.getRead());
        MongoUtil.buildQueryForIs(query, "staffId", chatRecordQuery.getStaffId());
        MongoUtil.buildQueryForIs(query, "visitorId", chatRecordQuery.getVisitorId());
        MongoUtil.buildQueryForIs(query, "sessionId", chatRecordQuery.getSessionId());
        MongoUtil.buildQueryForLike(query, "content", chatRecordQuery.getContent(), MongoUtil.LikeType.ALL);
        MongoUtil.buildQueryForGtAndLtDate(query, "gmtCreate", chatRecordQuery.getBeginDate(), chatRecordQuery.getEndDate());

        return query;
    }

    private String getCollectionName(String sessionId) {
        String lastName = sessionId.substring(sessionId.length() - 1, sessionId.length());
        return COLLECTION_NAME + "_" + lastName;
    }


    public static void main(String[] args) {
        String sessionId = "12321312a";
        String lastName = sessionId.substring(sessionId.length() - 1, sessionId.length());
        System.out.println(lastName);
    }
}
