package test.com.xudong.im.manage;

import com.xudong.im.domain.chat.GuestBook;
import com.xudong.im.manage.ChatManage;
import com.xudong.im.manage.GuestBookManage;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.xudong.im.support.ServiceTestCaseSupport;

/**
 * GuestBookManage Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 19, 2019</pre>
 */
public class GuestBookManageTest extends ServiceTestCaseSupport {

    @Autowired
    private GuestBookManage guestBookManage;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: save(String remoteAddr, GuestBook guestBook, UserAgent userAgent)
     */
    @Test
    public void testSave() throws Exception {
        GuestBook guestBook = new GuestBook();
        guestBook.setContent("testing");
        guestBookManage.save("127.0.0.1", guestBook, null);
    }

    /**
     * Method: getForList(GuestBookQuery guestBookQuery)
     */
    @Test
    public void testGetForList() throws Exception {
//TODO: Test goes here... 
    }


} 
