package com.xudong.im.data.mongo;

import com.xudong.im.constant.CommonConstant;
import com.xudong.im.domain.chat.GuestBook;
import com.xudong.im.domain.chat.GuestBookQuery;
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
 * 留言
 */
@Repository
public class GuestBookRepository {

    private static final String COLLECTION_NAME = "guest_book";

    @Autowired
    private MongoTemplate mongoTemplate;

    public GuestBook load(String id) {
        return mongoTemplate.findById(id, GuestBook.class, COLLECTION_NAME);
    }

    public GuestBook insert(GuestBook o) {
        if (o == null) {
            return null;
        }
        o.setGmtCreate(new Date());
        mongoTemplate.insert(o, COLLECTION_NAME);
        return o;
    }

    public List<GuestBook> queryList(GuestBookQuery guestBookQuery) {
        Query query = buildQuery(guestBookQuery);
        return mongoTemplate.find(query, GuestBook.class, COLLECTION_NAME);
    }

    public PageResult<GuestBook> queryPage(GuestBookQuery guestBookQuery) {
        if (guestBookQuery.getPageSize() == 0) {
            guestBookQuery.setPageSize(CommonConstant.DEFAULT_PAGE_SIZE);
        }

        Query query = buildQuery(guestBookQuery);

        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "gmtCreate"));
        PageRequest pageRequest = new PageRequest(guestBookQuery.getPageNo() - 1, guestBookQuery.getPageSize(), sort);
        query.with(pageRequest);

        List<GuestBook> list = mongoTemplate.find(query, GuestBook.class, COLLECTION_NAME);
        if (CollectionUtils.isEmpty(list)) {
            return PageResult.create(guestBookQuery, new ArrayList<>(), 0);
        }
        long count = mongoTemplate.count(query, COLLECTION_NAME);
        return PageResult.create(guestBookQuery, list, count);
    }

    private Query buildQuery(GuestBookQuery guestBookQuery) {
        Query query = new Query();
        return query;
    }
}
