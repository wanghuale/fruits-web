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
 * 描述：〈描述〉
 */
public class MerchantControllerTest extends BaseTest {
	
	private static final Logger logger = LoggerFactory.getLogger(MerchantControllerTest.class);

	private static final JsonMapper mapper = JsonMapper.nonDefaultMapper();
	
	/**
	 * 
	 * 描述：〈商户个人设置更新〉 <br/>
	 * 作者：huaxing.wang@rogrand.com <br/>
	 * 生成日期：2014-4-10 <br/>
	 *
	 */
	@Test
	public void merchantStatusSettingTest(){
		String url = "http://localhost:80/kkmy2.0/merchant/merchantStatusSetting.do";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "4475");
		map.put("merchantId", "4474");
		map.put("inStatusbar", "y");
		map.put("createShortcut", "y");
		map.put("wifiShowImage", "y");
		map.put("receiveServiceRemind", "y");
		map.put("dialogMsgRemind", "y");
		map.put("systemMsgRemind", "n");
		map.put("noDisturb", "n");
		map.put("busyTimes", "[{\"time\":\"8:00-9:00\",\"statu\":true}]");
		map.put("sendDistance", "5.0");
		backInfo(map,url);
	}
	
	/**
	 * 
	 * 描述：〈获取商户设置信息〉 <br/>
	 * 作者：huaxing.wang@rogrand.com <br/>
	 * 生成日期：2014-4-24 <br/>
	 *
	 */
	@Test
	public void getSettingTest(){
		String url = "http://localhost:80/kkmy2.0/merchant/getSetting.do";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantId", "5860");
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
