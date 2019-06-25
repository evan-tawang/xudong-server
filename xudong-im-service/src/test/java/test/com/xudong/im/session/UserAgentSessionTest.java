package test.com.xudong.im.session;


import com.xudong.im.domain.user.StaffAgent;
import com.xudong.im.domain.user.support.UserAgent;
import com.xudong.im.session.UserAgentSession;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.xudong.im.support.WebTestCaseSupport;

import java.util.List;

/**
 * UserAgentSession Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>六月 19, 2019</pre>
 */
public class UserAgentSessionTest extends WebTestCaseSupport {

    @Autowired
    private UserAgentSession userAgentSession;

    /**
     * Method: save(UserAgent userAgent)
     */
    @Test
    public void testSave() {
//TODO: Test goes here... 
    }

    /**
     * Method: get(HttpServletRequest request)
     */
    @Test
    public void testGet() {
        UserAgent userAgent = userAgentSession.get("148", 2);
        LOGGER.info(">>>>>testGet:" + userAgent);

        userAgent = userAgentSession.get("809", 1);
        LOGGER.info(">>>>>testGet:" + userAgent);

        StaffAgent staffAgent = userAgentSession.get("809", StaffAgent.class);
        LOGGER.info(">>>>>testGet:" + staffAgent);
    }

    /**
     * Method: isLogin(String userId, Integer userType, HttpServletRequest request)
     */
    @Test
    public void testIsLogin() {
//TODO: Test goes here... 
    }

    /**
     * Method: remove(HttpServletRequest request)
     */
    @Test
    public void testRemove() {
//TODO: Test goes here... 
    }

    /**
     * Method: refresh(UserAgent newUserAgent)
     */
    @Test
    public void testRefresh() {
//TODO: Test goes here... 
    }

    /**
     * Method: getOnlineStaffs()
     */
    @Test
    public void testGetOnlineStaffs() {
        List<StaffAgent> list = userAgentSession.getOnlineStaffs();

        LOGGER.info(">>>>>testGetOnlineStaffs:" + list);
    }

    /**
     * Method: updateOnlineStatus(Integer status, HttpServletRequest request)
     */
    @Test
    public void testUpdateOnlineStatus() {
//TODO: Test goes here... 
    }

    /**
     * Method: getAndPutToContext(HttpServletRequest request)
     */
    @Test
    public void testGetAndPutToContext() {
//TODO: Test goes here... 
    }

} 
