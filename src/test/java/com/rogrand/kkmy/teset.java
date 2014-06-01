package com.rogrand.kkmy;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

import com.rogrand.kkmy.util.Cache;
import com.rogrand.kkmy.util.CacheManager;
import com.rogrand.kkmy.util.DateUtil;
import com.rogrand.kkmy.util.StringUtil;

public class teset {
	
	public static void main(String[] args) {
		String receiveLongitude = "234.909";
//    	BigDecimal.valueOf(Long.valueOf(receiveLongitude).longValue());
//		Double.valueOf(receiveLongitude);
		for (int i = 0; i < 10; i++) {
			System.out.println(getCode());
		}
		
	}
	
	public static synchronized String getCode() {
		String bastNum = null;
		//从缓存中获取验证码
		Cache codeCache = CacheManager.getCacheInfo("codeCache");
		if(codeCache==null){
			String now = DateUtil.getDate("yyyyMMdd").substring(2);
			//获取当天最大优惠券验证码
//		    bastNum = kkmyCouponReceiveService.getMaxCode();
			bastNum = "1405120001";
			if(bastNum==null||(bastNum!=null&&bastNum.substring(0,6).equals(now))){
				bastNum = StringUtil.genRandomNum(4);
			}else{
				bastNum = bastNum.substring(6);
			}
			int nexRandNum = Integer.valueOf(bastNum)+new Random().nextInt(5);
			bastNum = now+new DecimalFormat("0000").format(nexRandNum);
		}else{
			//从缓存中获取验证码
			bastNum = codeCache.getValue().toString().substring(6);
			String now = DateUtil.getDate("yyyyMMdd").substring(2);
			int nexRandNum = Integer.valueOf(bastNum)+new Random().nextInt(5);
			bastNum = now+new DecimalFormat("0000").format(nexRandNum);
			bastNum = now+nexRandNum;
		}
		
		CacheManager.putCache("codeCache", new Cache("codeNum",bastNum));
		return bastNum;
	}
}
