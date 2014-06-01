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
public class OrderControllerTest extends BaseTest {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderControllerTest.class);

	private static final JsonMapper mapper = JsonMapper.nonDefaultMapper();
	
	/**
	 * 
	 * 描述：〈获取已配送订单数量〉 <br/>
	 * 作者：huaxing.wang@rogrand.com <br/>
	 * 生成日期：2014-4-14 <br/>
	 *
	 */
	@Test
	public void getSendOrderCountsTest(){
		String url = "http://localhost:80/kkmy2.0/order/getSendOrderCounts.do";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantId", "5860");
		backInfo(map,url);
	}
	
	/**
	 * 
	 * 描述：〈查询订单配送列表〉 <br/>
	 * 作者：huaxing.wang@rogrand.com <br/>
	 * 生成日期：2014-4-14 <br/>
	 *
	 */
	@Test
	public void getSendOrdersTest(){
		String url = "http://localhost:80/kkmy2.0/order/getSendOrders.do";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantId", "5860");
		backInfo(map,url);
	}
	
	
	/**
	 * 
	 * 描述：〈获取订单待配送列表〉 <br/>
	 * 作者：huaxing.wang@rogrand.com <br/>
	 * 生成日期：2014-4-14 <br/>
	 *
	 */
	@Test
	public void getReadySendOrdersTest(){
		String url = "http://localhost:80/kkmy2.0/order/getReadySendOrders.do";
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
