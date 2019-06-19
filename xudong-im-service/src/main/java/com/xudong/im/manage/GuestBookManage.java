package com.xudong.im.manage;

import com.xudong.im.data.mongo.GuestBookRepository;
import com.xudong.im.domain.chat.GuestBook;
import com.xudong.im.domain.chat.GuestBookQuery;
import com.xudong.im.domain.user.support.UserAgent;
import org.evanframework.dto.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 聊天
 */
@Service
public class GuestBookManage {

    @Autowired
    private GuestBookRepository guestBookRepository;

    public void save(String remoteAddr, GuestBook guestBook, UserAgent userAgent) {
        if (StringUtils.isEmpty(guestBook.getContent())) {
            return;
        }
        if(userAgent != null){
            guestBook.setVisitorIp(userAgent.getRemoteAddr());
            guestBook.setVisitorId(userAgent.getId());
        } else {
            guestBook.setVisitorIp(remoteAddr);
        }
        guestBookRepository.insert(guestBook);
    }

    public PageResult<GuestBook> getForList(GuestBookQuery guestBookQuery) {
       return guestBookRepository.queryPage(guestBookQuery);
    }
}
