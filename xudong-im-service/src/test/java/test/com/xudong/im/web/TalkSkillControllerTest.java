package test.com.xudong.im.web;

import com.xudong.core.util.RandomUtil;
import com.xudong.im.domain.help.TalkSkill;
import org.evanframework.dto.ApiResponse;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import test.com.xudong.im.support.WebTestCaseSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Evan.Shen
 * @since 2019/6/8
 */
public class TalkSkillControllerTest extends WebTestCaseSupport {
    private ParameterizedTypeReference<ApiResponse<List<TalkSkill>>> responseType = new ParameterizedTypeReference<ApiResponse<List<TalkSkill>>>() {
    };

    /**
     * Method: getForList(TalkSkillQuery talkSkillQuery)
     */
    @Test
    public void testGetForList() {
        String url = getFullApiUri("/talkSkill/manage/list?pageSize={pageSize}&sort={sort}");

        Map<String, Object> parames = new HashMap<String, Object>();
        parames.put("pageSize", 15);
        parames.put("sort", "gmt_modify");

        ResponseEntity<String> responseEntity1 = restTemplate.getForEntity(url, String.class, parames);
        LOGGER.info("========>>testGetList 结果1：" + responseEntity1.getBody());

        ResponseEntity<ApiResponse<List<TalkSkill>>> responseEntity2 = restTemplate.exchange(url, HttpMethod.GET, null, responseType, parames);
        LOGGER.info("========>>testGetList 结果2：" + responseEntity2.getBody());
    }

    /**
     * Method: getOne(@RequestParam("id") Integer id)
     */
    @Test
    public void testGetOne() {
        String url = getFullApiUri("/talkSkill/manage/getOne?id={0}");
        ApiResponse response = restTemplate.getForObject(url, ApiResponse.class, RandomUtil.randomInt(10));
        LOGGER.info("========>>testGetOne 结果1：" + response);
    }

    /**
     * Method: add(TalkSkill o)
     */
    @Test
    public void testAdd() {
        String url = getFullApiUri("/talkSkill/manage/add?content={0}");
        ApiResponse response = restTemplate.postForObject(url, null, ApiResponse.class, RandomUtil.randomName("Test"));
        LOGGER.info("========>>testGetOne 结果1：" + response);
    }

    /**
     * Method: update(TalkSkill o)
     */
    @Test
    public void testUpdate() {
        String url = getFullApiUri("/talkSkill/manage/update?id={id}&content={content}");

        Map<String, Object> parames = new HashMap<String, Object>();
        parames.put("id", RandomUtil.randomInt(10));
        parames.put("content", RandomUtil.randomName("Test"));

        ApiResponse response = restTemplate.postForObject(url, null, ApiResponse.class, parames);
        LOGGER.info("========>>testUpdate 结果1：" + response);
    }

    /**
     * Method: updateStatus(@RequestParam("id") Integer id, @RequestParam("newStatus") Integer newStatus)
     */
    @Test
    public void testUpdateStatus() {
        String url = getFullApiUri("/talkSkill/manage/updateStatus?id={id}&newStatus={newStatus}");

        Map<String, Object> parames = new HashMap<String, Object>();
        parames.put("id", RandomUtil.randomInt(10));
        parames.put("newStatus", RandomUtil.randomInt(2));

        ApiResponse response = restTemplate.postForObject(url, null, ApiResponse.class, parames);
        LOGGER.info("========>>testUpdateStatus 结果1：" + response);
    }

    /**
     * Method: updateStatusGroup(@RequestParam("ids[]") int[] ids, @RequestParam("newStatus") int newStatus)
     */
    @Test
    public void testUpdateStatusGroup() {
        String url = getFullApiUri("/talkSkill/manage/updateStatusGroup?ids={ids}&newStatus={newStatus}");

        Map<String, Object> parames = new HashMap<String, Object>();
        parames.put("ids", RandomUtil.randomInt(10) + "," + RandomUtil.randomInt(10));
        parames.put("newStatus", RandomUtil.randomInt(2));

        ApiResponse response = restTemplate.postForObject(url, null, ApiResponse.class, parames);
        LOGGER.info("========>>testUpdateStatusGroup 结果1：" + response);
    }

    /**
     * Method: delete(@RequestParam("id") Integer id)
     */
    @Test
    public void testDelete() {
        String url = getFullApiUri("/talkSkill/manage/delete?id={0}");

        ApiResponse response = restTemplate.postForObject(url, null, ApiResponse.class, RandomUtil.randomInt(10));
        LOGGER.info("========>>testDelete 结果1：" + response);
    }

    @Test
    public void testServiceList() {
        String url = getFullApiUri("/talkSkill/service/list");
        ApiResponse response = restTemplate.getForObject(url,  ApiResponse.class);
        LOGGER.info("========>>testServiceList 结果：" + response);
    }
}
