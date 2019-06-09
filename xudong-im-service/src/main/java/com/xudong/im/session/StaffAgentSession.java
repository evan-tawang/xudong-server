package com.xudong.im.session;

import com.xudong.core.cache.EHCacheUtil;
import com.xudong.core.util.AESException;
import com.xudong.core.util.AESUtil;
import com.xudong.core.util.IpUtil;
import com.xudong.im.constant.CommonConstant;
import com.xudong.im.domain.user.StaffAgent;
import com.xudong.im.exception.RemotingAddrExcetion;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 登录 Session
 *
 * @author Evan.Shen
 * @see
 * @since %I%, %G%
 */
@Component
public class StaffAgentSession {
    private final static Logger LOGGER = LoggerFactory.getLogger(StaffAgentSession.class);

    @Autowired
    private CacheManager ehCacheManager;
    @Autowired
    private StaffAgentCache userAgentCache;
    @Autowired
    private StaffAgentKeyCache userAgentKeyCache;

    private String secretForCacheKey;
    private EHCacheUtil ehCacheUtil;

    @PostConstruct
    public void init() {
        CacheConfiguration conf = new CacheConfiguration();
        conf.setTimeToIdleSeconds(60000);
        conf.setTimeToLiveSeconds(60000);
        conf.setName(StaffAgentSession.class.getSimpleName() + "KeyCache");//缓存名，全局唯一，可以用类名
        ehCacheUtil = new EHCacheUtil(ehCacheManager, conf);
    }

    public void save(StaffAgent userAgent) {
        Assert.notNull(userAgent, "登录用户不能为空");
        Assert.hasLength(userAgent.getIp(), "登录用户Ip不能为空");
        Assert.notNull(userAgent.getId(), "登录用户Id不能为空");
        Assert.hasLength(userAgent.getAccount(), "登录用户账号不能为空");

        //生成用户登录信息存放在redis的key
        String userAgentCacheKey = DigestUtils.sha1Hex(System.currentTimeMillis() + "-" + UUID.randomUUID());
        String secretForCacheKey = DigestUtils.sha1Hex(userAgent.getIp());
        String token = createToken(userAgentCacheKey, secretForCacheKey); //生成token：cacheKey进行AES加密,秘钥为 sha1(ip);
        userAgent.setToken(token);
        userAgent.setTokenSecret(DigestUtils.sha384Hex(token));


        userAgentCache.put(userAgentCacheKey, userAgent);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>> 存储用户会话，userAgentCacheKey【{}】，id【{}】，ip【{}】,account【{}】,token【{}】"
                    , userAgentCacheKey, userAgent.getId(), userAgent.getIp(), userAgent.getAccount(), token);
        }

        String userAgentKeyCacheKey = DigestUtils.sha1Hex(userAgent.getId() + "");
        userAgentKeyCache.put(userAgentKeyCacheKey, userAgentCacheKey);


    }

    public StaffAgent get(HttpServletRequest request) throws RemotingAddrExcetion {
        StaffAgent agent = null;
        String remotingAddr = IpUtil.getRemoteIp(request);

        String token = request.getHeader("token");
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(">>>>> 读取用户会话，token【{}】，客户端ip【{}】，上游服务ip【{}】,url【{}】", token, remotingAddr, request.getRemoteAddr(), request.getRequestURI());
        }
        if (StringUtils.isNotBlank(token) && !CommonConstant.DEFAULT_TOKEN.equals(token)) {
            //String remotingAddr = request.getRemoteAddr();
            String userAgentCacheKey = parseUserAgentCacheKey(token, remotingAddr, request);
            agent = userAgentCache.get(userAgentCacheKey);
        } else {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(">>>>> 读取用户会话时，token为空或等于固定token（表示没登录），客户端ip【{}】，上游服务ip【{}】,url【{}】", remotingAddr, request.getRemoteAddr(), request.getRequestURI());
            }
        }

        return agent;
    }

    /**
     * 根据用户id判断该用户是否登录
     *
     * @param userId
     * @return
     */
    public boolean isLogin(long userId, HttpServletRequest request) {
        boolean returnV = false;

        String userAgentCacheKey = userAgentKeyCache.get(DigestUtils.sha1Hex(userId + ""));
        if (StringUtils.isNotBlank(userAgentCacheKey)) {
            StaffAgent userAgent = userAgentCache.get(userAgentCacheKey);

            String remotingAddr = IpUtil.getRemoteIp(request);

            if (userAgent != null
                    && !remotingAddr.equals(userAgent.getIp())
                    ) {
                returnV = true;
            }
        }
        return returnV;
    }

    public void remove(HttpServletRequest request) throws RemotingAddrExcetion {
        String token = request.getHeader("token");
        String remotingAddr = IpUtil.getRemoteIp(request);
        String cacheKey = parseUserAgentCacheKey(token, remotingAddr, request);

        userAgentCache.remove(cacheKey);
    }

    /**
     * 刷新会话
     *
     * @param newUserAgent 新的userAgent，只刷新该userAgent中不为空的值。id不能为空
     */
    public void refresh(StaffAgent newUserAgent) {
        Assert.notNull(newUserAgent, "新会话不能为空");
        Assert.notNull(newUserAgent.getId(), "新会话用户Id不能为空");

        String userAgentCacheKey = userAgentKeyCache.get(DigestUtils.sha1Hex(newUserAgent.getId() + ""));

        if (StringUtils.isNotBlank(userAgentCacheKey)) {
            StaffAgent userAgent = userAgentCache.get(userAgentCacheKey);

            if (userAgent != null) {

                if (newUserAgent.getStatus() != null) {
                    userAgent.setStatus(newUserAgent.getStatus());
                }
                if (StringUtils.isNotBlank(newUserAgent.getUserName())) {
                    userAgent.setUserName(newUserAgent.getUserName());
                }

                userAgentCache.put(userAgentCacheKey, userAgent);
            }
        }
    }

    /**
     * 创建token
     * <br>实现:对cacheKey进行AES加密
     *
     * @param userAgentCacheKey
     * @param tokenSecret
     * @return
     */
    private String createToken(String userAgentCacheKey, String tokenSecret) {
        String token;
        try {
            token = AESUtil.encrypt(userAgentCacheKey, tokenSecret);
        } catch (AESException e) {
            LOGGER.error("存储用户会话时，加密serAgentCacheKey【" + userAgentCacheKey + "】出错，tokenSecret【" + tokenSecret + "】， " + e.getMessage(), e);
            token = userAgentCacheKey;
        }
        return token.toLowerCase();
    }

    /**
     * 根据客户端传递的token和remotingAddr获取UserAgentCacheKey
     *
     * @param token
     * @param remotingAddr
     * @return
     */
    private String parseUserAgentCacheKey(String token, String remotingAddr, HttpServletRequest request) throws RemotingAddrExcetion {
        String cacheKey = ehCacheUtil.get(token, String.class);

        if (StringUtils.isBlank(cacheKey)) {
            String secretForCacheKey = DigestUtils.sha1Hex(remotingAddr);
            try {
                cacheKey = AESUtil.decrypt(token.toLowerCase(), secretForCacheKey);
            } catch (AESException e1) {
                secretForCacheKey = DigestUtils.sha1Hex(request.getRemoteAddr());
                try {
                    cacheKey = AESUtil.decrypt(token.toLowerCase(), secretForCacheKey);
                } catch (AESException e2) {
                    LOGGER.error("获取用户会话时，解密token【" + token + "】出错，客户端ip【" + remotingAddr + "】，上游服务ip【" + request.getRemoteAddr() + "】,url【" + request.getRequestURI() + "】," + e2.getMessage(), e2);
                    throw new RemotingAddrExcetion("客户端ip【" + remotingAddr + "】不正确，请重新登录！");
                }
            }

            ehCacheUtil.put(token, cacheKey);

            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(">>>> parseUserAgentCacheKey, cacheKey [{}], 客户端ip[{}]，上游服务ip[{}], url[{}]", cacheKey, remotingAddr, request.getRemoteAddr(), request.getRequestURI());
            }
        }

        return cacheKey;
    }

    /**
     * 获取userAgent
     *
     * @param request
     * @return
     */
    public StaffAgent getAndPutToContext(HttpServletRequest request) {
        StaffAgent loginUser = null;
        try {
            loginUser = get(request);
        } catch (RemotingAddrExcetion ex) {
            LOGGER.error(">>>> " + ex.getMessage(), ex);
        }

        if (loginUser != null) {
            StaffAgentContext.put(loginUser);
        }
        return loginUser;
    }

//    public void setEhCacheManager(CacheManager ehCacheManager) {
//        this.ehCacheManager = ehCacheManager;
//    }
//
//    public void setUserAgentCache(StaffAgentCache userAgentCache) {
//        this.userAgentCache = userAgentCache;
//    }
//
//    public void setUserAgentKeyCache(StaffAgentKeyCache userAgentKeyCache) {
//        this.userAgentKeyCache = userAgentKeyCache;
//    }
}
