package com.xudong.im.session.reader;


import com.xudong.core.filter.AbstractUserContextFilter;
import com.xudong.core.util.Excludor;
import com.xudong.core.util.HttpUtils;
import com.xudong.im.domain.user.support.UserAgent;
import com.xudong.im.exception.RemotingAddrExcetion;
import com.xudong.im.session.UserAgentSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * 登录用户读取
 * 从session中读取登录用户对象UserAgent，并把读取到的UserAgent对象放到ThreadLocal中，以便在当前线程的各个环节中使用
 *
 * @author shenwei
 * @since 1.0
 */
public class UserAgentReadFilter extends AbstractUserContextFilter implements Filter {
    private final Logger LOGGER = LoggerFactory.getLogger(UserAgentReadFilter.class);

    private UserAgentSession userAgentSession;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (excludor == null) {
            excludor = new Excludor();

            Set<String> excludes = new HashSet<>();

            excludes.add("");
            excludes.add("/");
            excludes.add("/error");
            excludes.add("/**/*swagger-*");
            excludes.add("/**/*swagger-*/**");
            excludes.add("/v2/api-docs");
            excludes.add("/**/login");
            excludes.add("/**/login/**");
            excludes.add("/**/validate-code/**");
            excludes.add("/**/ws/**");

            excludor.setExcludes(excludes);
        }

        super.init(filterConfig);

        //LOGGER.info(">>>> LoginUserFilter inited，{}", excludor);
    }

    @Override
    public void process(HttpServletRequest request) {
//        if(LOGGER.isDebugEnabled()){
//            LOGGER.debug(">>>> " + request.getRequestURI());
//        }

        UserAgent userAgent = null;

        try {
            userAgent = userAgentSession.get(request);
        } catch (RemotingAddrExcetion ex) {
            LOGGER.warn("=====>>读取用户会话失败，客户端ip【{}】，上游服务ip【{}】,url【{}】", HttpUtils.getRemoteAddr(request), request.getRemoteAddr(), request.getRequestURI());
            UserAgentContext.remove();
        }

        if (userAgent != null) {
            UserAgentContext.put(userAgent);
            //request.setAttribute(UserAgent.REQUEST_ATTR_NAME, userAgent);
        } else {
            UserAgentContext.remove();
        }
    }

    public void setUserAgentSession(UserAgentSession userAgentSession) {
        this.userAgentSession = userAgentSession;
    }
}
