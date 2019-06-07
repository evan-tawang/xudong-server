package com.xudong.im.web;

import com.xudong.im.domain.limit.BlackList;
import com.xudong.im.support.WebTestCaseSupport;
import org.evanframework.dto.ApiResponse;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BlackListManageController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>六月 7, 2019</pre>
 */
public class BlackListManageControllerTest extends WebTestCaseSupport {

    private ParameterizedTypeReference<ApiResponse<List<BlackList>>> responseType = new ParameterizedTypeReference<ApiResponse<List<BlackList>>>() {
    };

    /**
     * Method: getForList(BlackListQuery blackListQuery)
     */
    @Test
    public void testGetForList() {
        String url = getFullApiUri("/blackList/manage/list?pageSize={pageSize}&sort={sort}");

        Map<String, Object> parames = new HashMap<String, Object>();
        parames.put("pageSize", 15);
        parames.put("sort", "gmt_modify");

        ResponseEntity<String> responseEntity1 = restTemplate.getForEntity(url, String.class, parames);
        LOGGER.info("========>>testGetList 结果1：" + responseEntity1.getBody());

        ResponseEntity<ApiResponse<List<BlackList>>> responseEntity2 = restTemplate.exchange(url, HttpMethod.GET, null, responseType, parames);
        LOGGER.info("========>>testGetList 结果2：" + responseEntity2.getBody());
    }

    /**
     * Method: getOne(@RequestParam("id") Integer id)
     */
    @Test
    public void testGetOne() {
        String url = getFullApiUri("/blackList/manage/getOne?id={0}");
        ApiResponse response = restTemplate.getForObject(url, ApiResponse.class, 1);
        LOGGER.info("========>>testGetOne 结果1：" + response);
    }

    /**
     * Method: add(BlackList o)
     */
    @Test
    public void testAdd() {
        String url = getFullApiUri("/blackList/manage/add?content={0}");
        ApiResponse response = restTemplate.postForObject(url, null, ApiResponse.class, "121");
        LOGGER.info("========>>testGetOne 结果1：" + response);
    }

    /**
     * Method: addGroup(@RequestParam("blackLists") String blackListContents)
     */
    @Test
    public void testAddGroup() {
        String url = getFullApiUri("/blackList/manage/addGroup?blackLists={0}");
        ApiResponse response = restTemplate.postForObject(url, null, ApiResponse.class, "125,129");
        LOGGER.info("========>>testAddGroup 结果1：" + response);
    }

    /**
     * Method: update(BlackList o)
     */
    @Test
    public void testUpdate() {
        String url = getFullApiUri("/blackList/manage/update?id={id}&content={content}");

        Map<String, Object> parames = new HashMap<String, Object>();
        parames.put("id", 107);
        parames.put("content", "2");

        ApiResponse response = restTemplate.postForObject(url, null, ApiResponse.class, parames);
        LOGGER.info("========>>testUpdate 结果1：" + response);
    }

    /**
     * Method: updateStatus(@RequestParam("id") Integer id, @RequestParam("newStatus") Integer newStatus)
     */
    @Test
    public void testUpdateStatus() {
        String url = getFullApiUri("/blackList/manage/updateStatus?id={id}&newStatus={newStatus}");

        Map<String, Object> parames = new HashMap<String, Object>();
        parames.put("id", 107);
        parames.put("newStatus", 2);

        ApiResponse response = restTemplate.postForObject(url, null, ApiResponse.class, parames);
        LOGGER.info("========>>testUpdateStatus 结果1：" + response);
    }

    /**
     * Method: updateStatusGroup(@RequestParam("ids[]") int[] ids, @RequestParam("newStatus") int newStatus)
     */
    @Test
    public void testUpdateStatusGroup() {
        String url = getFullApiUri("/blackList/manage/updateStatusGroup?ids[]={ids}&newStatus={newStatus}");

        Map<String, Object> parames = new HashMap<String, Object>();
        parames.put("ids", "124,1424");
        parames.put("newStatus", 2);

        ApiResponse response = restTemplate.postForObject(url, null, ApiResponse.class, parames);
        LOGGER.info("========>>testUpdateStatusGroup 结果1：" + response);
    }

    /**
     * Method: delete(@RequestParam("id") Integer id)
     */
    @Test
    public void testDelete() {
        String url = getFullApiUri("/blackList/manage/delete?id={0}");

        ApiResponse response = restTemplate.postForObject(url, null, ApiResponse.class, 12);
        LOGGER.info("========>>testDelete 结果1：" + response);
    }
} 
