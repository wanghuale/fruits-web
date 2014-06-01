package com.rogrand.kkmy;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rogrand.kkmy.common.BaseTest;
import com.rogrand.kkmy.util.ActinoInfoUtils;
import com.rogrand.kkmy.util.HttpURL;
import com.rogrand.kkmy.util.JsonMapper;

/**
 * 版权：融贯资讯 <br/>
 * 作者：huaxing.wang@rogrand.com <br/>
 * 生成日期：2014-4-3 <br/>
 * 描述：〈商户评价测试〉
 */
public class EvaluateControllerTest extends BaseTest {
	
	private static final Logger logger = LoggerFactory.getLogger(EvaluateControllerTest.class);

	private static final JsonMapper mapper = JsonMapper.nonDefaultMapper();
	
	
	@Test
	public void getEvaluateListTest(){
		String url = "http://localhost:80/kkmy2.0/evaluate/getEvaluateList.do";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantId", "5861");
		backInfo(map,url);
	}
	
	private void backInfo(Map<String, Object> map,String url) {
		HttpURL http = new HttpURL();
		try {
			String actionInfo = ActinoInfoUtils.mapToJson(map);
			String data = "dataJson={\"body\":"+actionInfo+",\"head\":{\"method\":\"021102\",\"tokenId\":\"20983678\",\"version\":\"1.0\"},\"mac\":\"\"}";
			
			System.out.println("发送消息param : "+data);
			Map<String, Object> resultJson = new HashMap<String, Object>();

			String js = http.doPost(url, data.getBytes());
			resultJson = mapper.fromJson(js, Map.class);
			String ACTION_INFO = mapper.toJson(resultJson.get("body"));
			System.out.println("返回结果：" + ACTION_INFO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
