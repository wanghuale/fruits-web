package com.rogrand.kkmy.common.provider;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.rogrand.kkmy.common.BaseTest;
import com.rogrand.kkmy.common.api.KkmyServiceMessageService;
import com.rogrand.kkmy.common.entity.DialogMessageListItemBean;
import com.rogrand.kkmy.common.model.KkmyServiceMessage;
import com.rogrand.kkmy.common.page.Pagination;
import com.rogrand.kkmy.util.JsonUtils;

/**
 * 版权：融贯资讯 <br/>
 * 作者：xuan.zhou@rogrand.com <br/>
 * 生成日期：2014-3-25 <br/>
 * 描述：〈描述〉
 */
public class KkmyServiceMessageServiceTest extends BaseTest {

    @Autowired
    private KkmyServiceMessageService kkmyServiceMessageService;

    @Test
    public void getServiceMessageList() {
        int serviceId = 10;
        List<DialogMessageListItemBean> list = kkmyServiceMessageService.getServiceMessageList(serviceId);
        System.out.println("ServiceMessageList:" + JSON.toJSONString(list));
    }

    @Test
    public void getUserDialogMessageList() {
        String sourceType = "User";
        int dialogId = 10;
        String orderByClause = null;
        Pagination<KkmyServiceMessage> p1 = new Pagination<KkmyServiceMessage>(1, 15);
        Pagination<DialogMessageListItemBean> p2 = kkmyServiceMessageService.getDialogMessageList(sourceType, dialogId,
                new Date(), orderByClause, p1);
        System.out.println("ServiceMessageList:" + JSON.toJSONString(p2));
    }

    @Test
    public void getMerchantDialogMessageList() {
        String sourceType = "Merchant";
        int dialogId = 10;
        String orderByClause = null;
        Pagination<KkmyServiceMessage> p1 = new Pagination<KkmyServiceMessage>(1, 15);
        Pagination<DialogMessageListItemBean> p2 = kkmyServiceMessageService.getDialogMessageList(sourceType, dialogId,
                new Date(), orderByClause, p1);
        System.out.println("ServiceMessageList:" + JSON.toJSONString(p2));
    }
    
    @Test
    public void insert() {
        KkmyServiceMessage message = new KkmyServiceMessage();
        kkmyServiceMessageService.insert(message);
        System.out.println(JsonUtils.toJsonString(message));
    }
}
