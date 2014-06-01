package com.rogrand.kkmy;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rogrand.kkmy.common.BaseTest;
import com.rogrand.kkmy.common.api.KkmyMerchantSettingService;
import com.rogrand.kkmy.common.model.KkmyMerchantSetting;

/**
 * 版权：融贯资讯 <br/>
 * 作者：huaxing.wang@rogrand.com <br/>
 * 生成日期：2014-4-3 <br/>
 * 描述：〈描述〉
 */
public class TransactionControllerTest extends BaseTest {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionControllerTest.class);

	@Autowired
    private KkmyMerchantSettingService kkmyMerchantSettingService;
	
	/**
	 * 
	 * 描述：〈商户个人设置更新〉 <br/>
	 * 作者：huaxing.wang@rogrand.com <br/>
	 * 生成日期：2014-4-10 <br/>
	 *
	 */
	@Test
	public void transactionTest(){
		try {
		//	kkmyMerchantSettingService.updateManager();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
