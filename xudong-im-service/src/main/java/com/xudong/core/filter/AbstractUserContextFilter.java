package com.xudong.core.filter;

import com.xudong.core.util.Excludor;
import com.xudong.core.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.PathMatcher;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author Evan.Shen
 * @since 2019-06-19
 */
public abstract class AbstractUserContextFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractUserContextFilter.class);

    private Set<String> matchesCache = new ConcurrentSkipListSet<>();

    protected Excludor excludor;
    protected PathMatcher pathMatcher;
    protected UrlPathHelper urlPathHelper;

    public void init(FilterConfig filterConfig) throws ServletException {
        if (urlPathHelper == null) {
            urlPathHelper = new UrlPathHelper();
        }
        if (excludor == null) {
            excludor = new Excludor();
        }

        LOGGER.info(">>>> {} inited, excludes [{}]", this.getClass().getSimpleName(), excludor.toString());
    }


    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String requestPath = urlPathHelper.getPathWithinApplication(request);
        //String requestPath = urlPathHelper.getPathWithinServletMapping(request);

        boolean isNeedAuth = false;
        if(matchesCache.contains(requestPath)) {
            isNeedAuth = true;
        }else{
            if(!PathUtils.matches(requestPath, excludor.getExcludes())){
                isNeedAuth = true;
                matchesCache.add(requestPath);
            }
        }

        if (isNeedAuth) {
            process(request);
        }

        chain.doFilter(request, response);

    }

    public abstract void process(HttpServletRequest request);


    public void destroy() {
    }

    public void setExcludor(Excludor excludor) {
        this.excludor = excludor;
    }

    public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
        this.urlPathHelper = urlPathHelper;
    }
}
