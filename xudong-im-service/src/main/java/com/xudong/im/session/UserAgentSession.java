package com.xudong.im.session;

import com.xudong.core.cache.AbstractCache;
import com.xudong.core.cache.EHCacheUtil;
import com.xudong.core.util.AESException;
import com.xudong.core.util.AESUtil;
import com.xudong.core.util.IpUtil;
import com.xudong.im.constant.CommonConstant;
import com.xudong.im.domain.user.StaffAgent;
import com.xudong.im.domain.user.VisitorAgent;
import com.xudong.im.domain.user.support.UserAgent;
import com.xudong.im.enums.OnlineStatusEnum;
import com.xudong.im.enums.UserOnlineStatusEnum;
import com.xudong.im.enums.UserTypeEnum;
import com.xudong.im.exception.RemotingAddrExcetion;
import com.xudong.im.session.cache.StaffAgentCache;
import com.xudong.im.session.cache.VisitorAgentCache;
import com.xudong.im.session.reader.UserAgentContext;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录 Session
 *
 * @author Evan.Shen
 * @see
 * @since %I%, %G%
 */
@Component
public class UserAgentSession {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserAgentSession.class);

    @Autowired
    private CacheManager ehCacheManager;

    @Autowired
    private StaffAgentCache staffAgentCache;

    @Autowired
    private VisitorAgentCache visitorAgentCache;
//    @Autowired
//    private UserAgentKeyCache userAgentKeyCache;

    //private String secretForCacheKey;
    private EHCacheUtil ehCacheUtil;

    private Map<Integer, AbstractCache> userAgentCacheMap = new HashMap<>(8);

    private Map<String, UserTypeEnum> userTypeMap = new HashMap<>(8);

    @PostConstruct
    public void init() {
        CacheConfiguration conf = new CacheConfiguration();
        conf.setTimeToIdleSeconds(60000);
        conf.setTimeToLiveSeconds(60000);
        conf.setName(UserAgentSession.class.getSimpleName() + "KeyCache");//缓存名，全局唯一，可以用类名
        ehCacheUtil = new EHCacheUtil(ehCacheManager, conf);

        userAgentCacheMap.put(UserTypeEnum.STAFF.getValue(), staffAgentCache);
        userAgentCacheMap.put(UserTypeEnum.VISITOR.getValue(), visitorAgentCache);

        userTypeMap.put(StaffAgent.class.getSimpleName(), UserTypeEnum.STAFF);
        userTypeMap.put(VisitorAgent.class.getSimpleName(), UserTypeEnum.VISITOR);
    }

    public void save(UserAgent userAgent) {
        Assert.notNull(userAgent, "登录用户不能为空");
        Assert.hasLength(userAgent.getRemoteAddr(), "登录用户Ip不能为空");
        Assert.notNull(userAgent.getId(), "登录用户Id不能为空");
        Assert.hasLength(userAgent.getAccount(), "登录用户账号不能为空");

        //生成用户登录信息存放在redis的key
        String userAgentCacheKey = generateUserAgentCacheKey(userAgent.getId(), userAgent.getUserType());
        //String userAgentCacheKey = DigestUtils.sha1Hex(System.currentTimeMillis() + "-" + UUID.randomUUID());

        String tokenSecret = DigestUtils.sha1Hex(userAgent.getRemoteAddr());//生成用于加密token的加密秘钥
        String token = createToken(userAgent.getUserType(), userAgentCacheKey, tokenSecret); //生成token：cacheKey进行AES加密,秘钥为 sha1(ip);
        userAgent.setToken(token);
        userAgent.setTokenSecret(DigestUtils.sha384Hex(token));

        // todo: 默认为在线
        userAgent.setOnlineStatus(UserOnlineStatusEnum.ON_LINE.getValue());

        AbstractCache userAgentCache = getUserAgentCache(userAgent.getUserType());

        userAgentCache.put(userAgentCacheKey, userAgent);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>> 存储用户会话，userAgentCacheKey【{}】，id【{}】，ip【{}】,account【{}】,token【{}】"
                    , userAgentCacheKey, userAgent.getId(), userAgent.getRemoteAddr(), userAgent.getAccount(), token);
        }

        //String userAgentKeyCacheKey = DigestUtils.sha1Hex(userAgent.getId() + "");
        // 、、userAgentKeyCache.put(userAgentKeyCacheKey, userAgentCacheKey);
    }

    /**
     * 根据Request获取
     *
     * @param request
     * @return
     * @throws RemotingAddrExcetion
     */
    public UserAgent get(HttpServletRequest request) throws RemotingAddrExcetion {
        UserAgent agent = null;
        String remotingAddr = IpUtil.getRemoteIp(request);

        String token = request.getHeader("token");
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace(">>>>> 读取用户会话，token【{}】，客户端ip【{}】，上游服务ip【{}】,url【{}】", token, remotingAddr, request.getRemoteAddr(), request.getRequestURI());
        }
        if (StringUtils.isNotBlank(token) && !CommonConstant.DEFAULT_TOKEN.equals(token)) {
            //String remotingAddr = request.getRemoteAddr();
            String[] tmps = parseToken(token, remotingAddr, request);

            Integer userType = Integer.valueOf(tmps[0]);
            String cacheKey = tmps[1];

            AbstractCache userAgentCache = getUserAgentCache(userType);
            Object o = userAgentCache.get(cacheKey);

            if (o != null) {
                agent = (UserAgent) o;
            }
        } else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>> 读取用户会话时，token为空或等于固定token（表示没登录），客户端ip【{}】，上游服务ip【{}】,url【{}】", remotingAddr, request.getRemoteAddr(), request.getRequestURI());
            }
        }

        return agent;
    }

    /**
     * 根据id和类型获取用户
     *
     * @param userId
     * @param userType UserTypeEnum
     * @return
     * @see UserTypeEnum
     */
    public UserAgent get(String userId, Integer userType) {
        UserAgent userAgent = null;

        String userAgentCacheKey = generateUserAgentCacheKey(userId, userType);
        if (StringUtils.isNotBlank(userAgentCacheKey)) {
            AbstractCache<UserAgent> userAgentCache = getUserAgentCache(userType);
            userAgent = userAgentCache.get(userAgentCacheKey);
        }
        return userAgent;
    }

    public <T extends UserAgent> T get(String userId, Class<T> c) {
        UserTypeEnum userType = userTypeMap.get(c.getSimpleName());

        UserAgent userAgent = null;

        String userAgentCacheKey = generateUserAgentCacheKey(userId, userType.getValue());
        if (StringUtils.isNotBlank(userAgentCacheKey)) {
            AbstractCache<UserAgent> userAgentCache = getUserAgentCache(userType.getValue());
            userAgent = userAgentCache.get(userAgentCacheKey);
        }
        return (T)userAgent;
    }

    /**
     * 根据用户id判断该用户是否登录
     *
     * @param userId
     * @param userType UserTypeEnum
     * @return
     * @see UserTypeEnum
     */
    public boolean isLogin(String userId, Integer userType, String remotingAddr) {
        boolean returnV = false;

        UserAgent userAgent = get(userId, userType);
        if (userAgent != null
                && !remotingAddr.equals(userAgent.getRemoteAddr())
        ) {
            returnV = true;
        }
        return returnV;
    }

    /**
     * @param request
     * @throws RemotingAddrExcetion
     */
    public void remove(HttpServletRequest request) throws RemotingAddrExcetion {
        String token = request.getHeader("token");
        String remotingAddr = IpUtil.getRemoteIp(request);

        String[] tmps = parseToken(token, remotingAddr, request);
        Integer userType = Integer.valueOf(tmps[0]);
        String cacheKey = tmps[1];
        AbstractCache userAgentCache = getUserAgentCache(userType);

        userAgentCache.remove(cacheKey);
    }

    /**
     * 刷新会话
     *
     * @param newUserAgent 新的userAgent，只刷新该userAgent中不为空的值。id不能为空
     */
    public void refresh(UserAgent newUserAgent) {
        Assert.notNull(newUserAgent, "新会话不能为空");
        Assert.notNull(newUserAgent.getId(), "新会话用户Id不能为空");

        String userAgentCacheKey = DigestUtils.sha1Hex(DigestUtils.sha1Hex(newUserAgent.getId() + "-" + newUserAgent.getUserType()));

        if (StringUtils.isNotBlank(userAgentCacheKey)) {
            AbstractCache<UserAgent> userAgentCache = getUserAgentCache(newUserAgent.getUserType());

            UserAgent userAgent = userAgentCache.get(userAgentCacheKey);

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
     * 获取在线客服
     *
     * @return
     */
    public List<StaffAgent> getOnlineStaffs() {
        return staffAgentCache.getByOnlineStatus(OnlineStatusEnum.ONLINE.getValue());
    }


    /**
     * 更新客服自己的在线状态
     *
     * @param status 更新后的状态
     * @see com.xudong.im.enums.OnlineStatusEnum
     */
    public void updateOnlineStatus(Integer status, HttpServletRequest request) {
        UserAgent loginUser = get(request);

        String userAgentCacheKey = generateUserAgentCacheKey(loginUser.getId(),loginUser.getUserType());

        if (StringUtils.isNotBlank(userAgentCacheKey)) {
            loginUser.setOnlineStatus(status);
            AbstractCache<UserAgent> userAgentCache = getUserAgentCache(loginUser.getUserType());
            userAgentCache.put(userAgentCacheKey, loginUser);
        }
    }

    /**
     * 生成cacheKey
     *
     * @param userId
     * @param userType
     * @return
     */
    private static String generateUserAgentCacheKey(String userId, Integer userType) {
        return DigestUtils.sha1Hex(userId + "-" + userType);
    }

    /**
     * 创建token
     * <br>实现:对cacheKey进行AES加密
     *
     * @param userAgentCacheKey
     * @param tokenSecret
     * @return
     */
    private String createToken(Integer userType, String userAgentCacheKey, String tokenSecret) {
        String token;
        try {
            token = AESUtil.encrypt(userType + userAgentCacheKey, tokenSecret);
        } catch (AESException e) {
            LOGGER.error("存储用户会话时，加密serAgentCacheKey【" + userAgentCacheKey + "】出错，tokenSecret【" + tokenSecret + "】， " + e.getMessage(), e);
            token = userAgentCacheKey;
        }
        return token.toLowerCase();
    }

    /**
     * 解析token
     *
     * @param token
     * @param remotingAddr
     * @return 返回数组，0位用户类型，1位cacheKey
     */
    private String[] parseToken(String token, String remotingAddr, HttpServletRequest request) throws RemotingAddrExcetion {
        String[] returnV = ehCacheUtil.get(token, String[].class);

        if (returnV == null || returnV.length == 0) {
            String secretForToken = DigestUtils.sha1Hex(remotingAddr);
            String tokenPlaintext = null;
            try {
                tokenPlaintext = AESUtil.decrypt(token.toLowerCase(), secretForToken);
            } catch (AESException e1) {
                secretForToken = DigestUtils.sha1Hex(request.getRemoteAddr());
                try {
                    tokenPlaintext = AESUtil.decrypt(token.toLowerCase(), secretForToken);
                } catch (AESException e2) {
                    LOGGER.error("获取用户会话时，解密token【" + token + "】出错，客户端ip【" + remotingAddr + "】，上游服务ip【" + request.getRemoteAddr() + "】,url【" + request.getRequestURI() + "】," + e2.getMessage(), e2);
                    throw new RemotingAddrExcetion("客户端ip【" + remotingAddr + "】不正确，请重新登录！");
                }
            }

            String userType = tokenPlaintext.substring(0, 1);
            String cacheKey = tokenPlaintext.substring(1);

            returnV = new String[]{userType, cacheKey};

            ehCacheUtil.put(token, returnV);

            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace(">>>> parseToken, userType[{}] ,cacheKey [{}], 客户端ip[{}]，上游服务ip[{}], url[{}]", userType, cacheKey, remotingAddr, request.getRemoteAddr(), request.getRequestURI());
            }
        }

        return returnV;
    }

    /**
     * 获取userAgent
     *
     * @param request
     * @return
     */
    public UserAgent getAndPutToContext(HttpServletRequest request) {
        UserAgent loginUser = null;
        try {
            loginUser = get(request);
        } catch (RemotingAddrExcetion ex) {
            LOGGER.error(">>>> " + ex.getMessage(), ex);
        }

        if (loginUser != null) {
            UserAgentContext.put(loginUser);
        }
        return loginUser;
    }

    private AbstractCache getUserAgentCache(Integer userType) {
        return userAgentCacheMap.get(userType);
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

    public static void main(String[] args) {
        System.out.println(generateUserAgentCacheKey("699", 1));
    }
}
