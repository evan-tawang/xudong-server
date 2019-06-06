package com.xudong.service;

import com.xudong.cache.BlackListCache;
import com.xudong.domain.model.BlackList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public String test(){
        return "hello-2";
    }
}
