package test.com.xudong.im.cache;

import com.xudong.im.cache.ChatWaitConnectQueueCache;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.xudong.im.support.CacheTestCaseSupport;

/**
 * ChatWaitConnectQueueCache Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 18, 2019</pre>
 */
public class ChatWaitConnectQueueCacheTest extends CacheTestCaseSupport {

    @Autowired
    private ChatWaitConnectQueueCache chatWaitConnectQueueCache;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: init()
     */
    @Test
    public void testInit() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: rightPop()
     */
    @Test
    public void testRightPop() throws Exception {

    }

    /**
     * Method: leftPush(String key, String value)
     */
    @Test
    public void testLeftPush() throws Exception {
        chatWaitConnectQueueCache.leftPush("1111");
        chatWaitConnectQueueCache.leftPush("222");


        String value = chatWaitConnectQueueCache.rightPop();
        System.out.println(value);

        String value1 = chatWaitConnectQueueCache.rightPop();
        System.out.println(value1);

        String value2 = chatWaitConnectQueueCache.rightPop();
        System.out.println(value2);
    }


} 
