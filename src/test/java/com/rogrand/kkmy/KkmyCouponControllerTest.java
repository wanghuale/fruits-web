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
public class KkmyCouponControllerTest extends BaseTest {
	
	private static final Logger logger = LoggerFactory.getLogger(KkmyCouponControllerTest.class);

	private static final JsonMapper mapper = JsonMapper.nonDefaultMapper();
	
	/**
	 * 
	 * 描述：商户获取优惠券列表测试 <br/>
	 * 作者：huaxing.wang@rogrand.com <br/>
	 * 生成日期：2014-4-10 <br/>
	 *
	 */
	@Test
	public void getMerchantCouponListTest(){
		String url = "http://localhost:80/kkmy2.0/coupon/getMerchantCouponList.do";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantId", "31805");
		backInfo(map,url);
	}
	
	/**
	 * 
	 * 描述：商户获取优惠券详细信息测试 <br/>
	 * 作者：huaxing.wang@rogrand.com <br/>
	 * 生成日期：2014-4-10 <br/>
	 *
	 */
	@Test
	public void getMerchantCouponTest(){
		String url = "http://localhost:80/kkmy2.0/coupon/getMerchantCoupon.do";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantId", "31805");
		map.put("couponId", "2");
		backInfo(map,url);
	}
	
	
	/**
	 * 
	 * 描述：商户验证优惠券测试 <br/>
	 * 作者：huaxing.wang@rogrand.com <br/>
	 * 生成日期：2014-4-10 <br/>
	 *
	 */
	@Test
	public void verifyCouponTest(){
		String url = "http://localhost:80/kkmy2.0/coupon/verifyCoupon.do";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantId", "31805");
		map.put("code", "303030");
		map.put("verifyType", "2");
		backInfo(map,url);
	}
	
	
	/**
	 * 
	 * 描述：用户获取优惠券详细测试 <br/>
	 * 作者：huaxing.wang@rogrand.com <br/>
	 * 生成日期：2014-4-10 <br/>
	 *
	 */
	@Test
	public void getUserCouponTest(){
		String url = "http://localhost:80/kkmy2.0/coupon/getUserCoupon.do";
		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userId", "112");
		map.put("merchantId", "35792");
		map.put("couponId", "20");
		backInfo(map,url);
	}
	
	
	/**
	 * 
	 * 描述：用户领取优惠券测试 <br/>
	 * 作者：huaxing.wang@rogrand.com <br/>
	 * 生成日期：2014-4-10 <br/>
	 *
	 */
	@Test
	public void receiveCouponTest(){
		String url = "http://localhost:80/kkmy2.0/coupon/receiveCoupon.do";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", "745");
		map.put("couponId", "17");
		map.put("userAccount", "13628624873");
		map.put("receiveMerchantId", "35794");
		map.put("receiveLongitude", "114.40307");
		map.put("receiveLatitude", "30.476518");
		map.put("receiveAddress", "湖北省 武汉市 洪山区 软件园中路 靠近中国武汉留学生创业园(玉树临风精品酒店南)");
		map.put("receiveImei", "357442055699634");
		map.put("receiveOsName", "Android");
		map.put("receiveOsVersion", "4.1.2");
		backInfo(map,url);
	}
	/**
	 * 
	 * 描述：用户领取优惠券测试 <br/>
	 * 作者：huaxing.wang@rogrand.com <br/>
	 * 生成日期：2014-4-10 <br/>
	 *
	 */
	@Test
	public void getTwoDimensionTest(){
		String url = "http://localhost:80/kkmy2.0/coupon/getTwoDimension.do";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "3180523409");
		backInfoImage(map,url);
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
	
	
	private void backInfoImage(Map<String, Object> map,String url) {
		HttpURL http = new HttpURL();
		try {
			String actionInfo = ActinoInfoUtils.mapToJson(map);
			String data = "dataJson={\"body\":"+actionInfo+",\"head\":{\"method\":\"021102\",\"tokenId\":\"20983678\",\"version\":\"1.0\"},\"mac\":\"\"}";
			
			System.out.println("发送消息param : "+data);
			Map<String, Object> resultJson = new HashMap<String, Object>();

			String code = http.doPostImage(url, data.getBytes());
			
			System.out.println("返回结果：" + code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
