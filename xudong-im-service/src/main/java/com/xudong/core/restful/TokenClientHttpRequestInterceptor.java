package com.xudong.core.restful;

import com.xudong.im.domain.user.support.UserAgent;
import com.xudong.im.session.reader.UserAgentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;

/**
 * Created on 2017/9/5.
 *
 * @author evan.shen
 */
public class TokenClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenClientHttpRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        UserAgent userAgent = UserAgentContext.get();

        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);
        HttpHeaders headers = requestWrapper.getHeaders();

        if (userAgent != null) {
            headers.set("token", userAgent.getToken());
            headers.set("X-Forwarded-For", userAgent.getIp());
        }

//        String random = System.currentTimeMillis() + "";
//        headers.set("random", random);

        LOGGER.info("=====>> HttpRequest[{}],uri:{}", request.getHeaders(), request.getURI());

        return execution.execute(requestWrapper, body);
    }
}
