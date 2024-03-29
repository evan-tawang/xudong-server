package test.com.xudong.im.manage;

import com.xudong.core.util.RandomUtil;
import com.xudong.im.domain.chat.ChatRecordDTO;
import com.xudong.im.domain.user.StaffAgent;
import com.xudong.im.domain.user.VisitorAgent;
import com.xudong.im.manage.ChatManage;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.xudong.im.support.ServiceTestCaseSupport;

/**
 * ChatManage Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 9, 2019</pre>
 */
public class ChatManageTest extends ServiceTestCaseSupport {

    @Autowired
    private ChatManage chatManage;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: sendMsg(ChatRecordDTO chatDTO, StaffAgent agent)
     */
    @Test
    public void testSendMsgBySessionId() throws Exception {
        ChatRecordDTO chatDTO = new ChatRecordDTO();
        chatDTO.setContent("testing");
        chatDTO.setSessionId("5cfcdd131d798610084eb98c");

        StaffAgent agent = new StaffAgent();
        agent.setId(RandomUtil.randomId() + "");

        chatManage.sendMsg(chatDTO, agent);

//TODO: Test goes here... 
    }

    @Test
    public void testSendMsgByReceiveId() throws Exception {
        ChatRecordDTO chatDTO = new ChatRecordDTO();
        chatDTO.setContent("有什么事情吗");
        chatDTO.setReceiveId("f528764d-624d-3129-b32c-21fbca0cb8d6");

        StaffAgent agent = new StaffAgent();
        agent.setId("6459835738292525451");

        chatManage.sendMsg(chatDTO, agent);

//TODO: Test goes here...
    }

    /**
     * Method: createSession(StaffAgent agent, String remoteAddr)
     */
    @Test
    public void testCreateSession() throws Exception {
        VisitorAgent agent = new VisitorAgent();
        agent.setId(RandomUtil.randomId() + "");
//        OperateResult session = chatManage.createSession("1", "127.0.0.1");
//        printInfo(session);
//TODO: Test goes here... 
    }

    /**
     * Method: queryPage(ChatRecordQuery chatRecordQuery)
     */
    @Test
    public void testQueryPage() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: createSession(String serviceId, String visitorId)
     */
    @Test
    public void testCreateSessionForServiceIdVisitorId() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = ChatManage.getClass().getMethod("createSession", String.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: createSession(String serviceId, String visitorId, String visitorIp)
     */
    @Test
    public void testCreateSessionForServiceIdVisitorIdVisitorIp() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = ChatManage.getClass().getMethod("createSession", String.class, String.class, String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: distributionService()
     */
    @Test
    public void testDistributionService() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = ChatManage.getClass().getMethod("distributionService"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 


