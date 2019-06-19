package com.xudong.im.session.auth;


import com.xudong.core.util.Excludor;
import com.xudong.core.util.PathUtils;
import com.xudong.im.domain.user.support.UserAgent;
import com.xudong.im.exception.NoLoginException;
import com.xudong.im.session.UserAgentSession;
import com.xudong.im.session.reader.UserAgentContext;
import com.xudong.im.session.reader.UserAgentReadFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户会话及权限控制拦截器
 *
 * @author Evan.Shen
 * @see UserAgentReadFilter
 * @since %I%, %G%
 */
public class UserAuthInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAuthInterceptor.class);

    /**
     * 拦截的方法缓存,无需每次通过反射读取
     */
    private Map<Method, Set<String>> caches = new ConcurrentHashMap<Method, Set<String>>();
    /**
     * 没有配置权限控制注解的Methed缓存
     */
    private Set<Method> noAuthCaches = Collections.synchronizedSet(new HashSet<Method>());

    private Excludor excludor;
    private UrlPathHelper urlPathHelper;
    private UserAgentSession userAgentSession;

    public void init() {
        if (excludor == null) {
            excludor = new Excludor();
        }

        if (urlPathHelper == null) {
            urlPathHelper = new UrlPathHelper();
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestPath = urlPathHelper.getPathWithinApplication(request);
        if (PathUtils.matches(requestPath, excludor.getExcludes())) {
            return true;
        }

        if (!HandlerMethod.class.isInstance(handler)) {
            LOGGER.warn(">>>> {} is not a instance of HandlerMethod", handler);
            return false;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

//        Set<String> allowFunctions = getAllowFunctions(method);
//
//        if (CollectionUtil.isEmpty(allowFunctions)) {//不要权限控制
//            return true;
//        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>> Validate user login in request [{}], method [{}]", requestPath, method);
        }

        UserAgent userAgent = getLoginUser(request);
        if (userAgent == null) {
            LOGGER.error("=======>> No login in request [{}], method [{}]", requestPath, method);
            throw new NoLoginException();
        }

        return true;
    }

    private UserAgent getLoginUser(HttpServletRequest request) {
        UserAgent loginUser = UserAgentContext.get();
        if (loginUser == null) {
            loginUser = userAgentSession.get(request);
            if (loginUser != null) {
                UserAgentContext.put(loginUser);
            }
        }
        return loginUser;
    }

    private void validatePermissions(HttpServletRequest request, String requestPath, Method handlerMethod) {
    }

    /**
     * 获取允许的Functions
     *
     * @param handlerMethod
     * @return
     */
    private Set<String> getAllowFunctions(Method handlerMethod) {
        Set<String> functions = null;

        if (noAuthCaches.contains(handlerMethod)) { // 该method在不需要权限控制的methed缓存中，则不需判断
            return functions;
        }

        functions = this.caches.get(handlerMethod);
        if (functions == null) {
            functions = getDefindFunctions(handlerMethod);
            // 没有配置@UserAuthority
            if (functions.size() == 0) {
                // 该方法或类没有配置UserAuthority,将handlerMethod加入noControlCaches缓存
                noAuthCaches.add(handlerMethod);
            } else {
                // 配置了@UserAuthority，将配置的的权限加入缓存
                this.caches.put(handlerMethod, functions);
            }
        }

        return functions;
    }

    /**
     * 获取方法或类上定义的允许的功能Id
     * <p>
     * author: Evan.Shen<br>
     * version: 2013-2-28 上午10:47:57 <br>
     *
     * @param handlerMethod
     * @return
     */
    private Set<String> getDefindFunctions(Method handlerMethod) {
        UserAuthority auth1 = null, auth2 = null;
        Set<String> defindFunctions = new HashSet<String>();

        // 取method上的@UserAuthority
        auth1 = AnnotationUtils.getAnnotation(handlerMethod, UserAuthority.class);
        addFunToSet(auth1, defindFunctions);

        // 取method上没有配置@UserAuthority
        if (defindFunctions.isEmpty()) {
            // 取class上的@UserAuthority
            auth2 = AnnotationUtils.getAnnotation(handlerMethod.getDeclaringClass(), UserAuthority.class);
            addFunToSet(auth2, defindFunctions);
        }

        return defindFunctions;
    }

    /**
     * <p>
     * author: Evan.Shen<br>
     * version: 2013-2-28 上午11:05:24 <br>
     *
     * @param auth
     * @param defindFunctions
     */
    private void addFunToSet(UserAuthority auth, Set<String> defindFunctions) {
        if (auth != null && auth.value() != null && auth.value().length > 0) {
            for (String s : auth.value()) {
                defindFunctions.add(s);
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    /**
     * 设置排除<p>
     * 优先在配置拦截器的地方设置排除，不能满足再在这里设置
     *
     * @param excludor
     */
    public void setExcludor(Excludor excludor) {
        this.excludor = excludor;
    }

    public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
        this.urlPathHelper = urlPathHelper;
    }

    public void setUserAgentSession(UserAgentSession userAgentSession) {
        this.userAgentSession = userAgentSession;
    }
}
