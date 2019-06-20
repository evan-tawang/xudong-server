package test.com.xudong.im.data.mongo;

import com.xudong.im.data.mongo.GuestBookRepository;
import com.xudong.im.domain.chat.GuestBook;
import com.xudong.im.domain.chat.GuestBookQuery;
import org.evanframework.dto.PageResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import test.com.xudong.im.support.MongoDBTestCaseSupport;

/**
 * GuestBookRepository Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>六月 20, 2019</pre>
 */
public class GuestBookRepositoryTest extends MongoDBTestCaseSupport {

    @Autowired
    private GuestBookRepository guestBookRepository;

    /**
     * Method: load(String id)
     */
    @Test
    public void testLoad() {
//TODO: Test goes here... 
    }

    /**
     * Method: insert(GuestBook o)
     */
    @Test
    public void testInsert() {
//TODO: Test goes here... 
    }

    /**
     * Method: queryList(GuestBookQuery guestBookQuery)
     */
    @Test
    public void testQueryList() {
//TODO: Test goes here... 
    }

    /**
     * Method: queryPage(GuestBookQuery guestBookQuery)
     */
    @Test
    public void testQueryPage() {
        GuestBookQuery guestBookQuery = new GuestBookQuery();

        guestBookQuery.setQueryKey("test1");

        PageResult<GuestBook> result = guestBookRepository.queryPage(guestBookQuery);

        System.out.println("=====>> testQueryPage:" + result);
    }


} 
