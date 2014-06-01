package com.king.fruits.core.common.base;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.king.fruits.core.model.ParamInfo;
import com.king.fruits.core.util.SysCode;
import com.king.fruits.core.util.Body;
import com.king.fruits.core.util.Head;
import com.king.fruits.core.util.JsonUtils;
import com.king.fruits.core.util.RequestJSON;
import com.king.fruits.core.util.ResponseJSON;
import com.king.fruits.core.util.ResponseUtils;
import com.king.fruits.core.util.RoUtil;

public class BaseController {

    protected static Logger logger = Logger.getLogger(BaseController.class);

    protected SysCode returnCode;
    protected String returnResult;

    /**
     * 构建输出到终端JSON 串HEAD <br/>
     * 构建输出到终端JSON 串HEAD <br/>
     * 
     * @param [method]-[后台调用方法名] <br/>
     * @param [sNumber]-[序列号] <br/>
     * @param [version]-[版本号] <br/>
     * @return [Head] 输出到终端JSON 串 HEAD<br/>
     */
    protected Head getResponseHead(String method, String sNumber, String version) {
        return new Head(method, sNumber, version);
    }

    /**
     * 构建输出到终端JSON 串BODY <br/>
     * 构建输出到终端JSON 串BODY <br/>
     * 
     * @param [SysCode]-[返回消息消息码 和消息] <br/>
     * @param [result]-[返回JSON 结果] <br/>
     * @return [Body] 输出到终端JSON串 BODY<br/>
     */
    protected Body getResponseBody(SysCode code, Object result) {
        Body body = new Body();
        body.setMessage(code.getMessage());
        body.setCode(code.getCode());
        body.setResult(result);
        return body;
    }

    /**
     * 构建返回给前端的JSON对象 <br/>
     * 构建返回给前端的JSON对象 <br/>
     * 
     * @param [mac]-[加密信息] <br/>
     * @param [method]-[后台调用方法] <br/>
     * @param [sNumber]-[序列号] <br/>
     * @param [version]-[版本号] <br/>
     * @param [code]-[返回消息消息码和消息] <br/>
     * @param [result]-[返回结果] <br/>
     * @return [ResponseJSON] 返回给前段JSON对象<br/>
     */
    protected ResponseJSON getResponseJSON(String mac, String method, String sNumber, String version, SysCode code,
            Object result) {
        ResponseJSON responseJSON = new ResponseJSON();
        responseJSON.setMac(mac);
        responseJSON.setHead(getResponseHead(method, sNumber, version));
        responseJSON.setBody(getResponseBody(code, result));
        return responseJSON;
    }

    protected ParamInfo parseRequest(HttpServletRequest request, String method, String[] paramKey) {
        String jsonParam = request.getParameter("dataJson");
        Object jsonStr = "";
        String mac = UUID.randomUUID().toString();
        String sNumber = UUID.randomUUID().toString();
        String version = "V1.0";

        logger.info("parseRequest method:" + method + "; jsonParam:" + jsonParam);

        ParamInfo info = new ParamInfo();

        // 如果传入字符串为空,则提示参数不合法
        if (RoUtil.isEmpty(jsonParam)) {
            logger.info("parameter error !");
            info.setState(SysCode.PARAM_IS_ERROR);
            info.setResponse(getResponseJSON(mac, method, sNumber, version, SysCode.PARAM_IS_ERROR, jsonStr));
            return info;
        }

        RequestJSON requestJSON = JSON.parseObject(jsonParam, RequestJSON.class);
        info.setRequestJSON(JSON.parseObject(jsonParam, RequestJSON.class));

        if (paramKey != null && paramKey.length > 0) {
            for (int i = 0; i < paramKey.length; i++) {
                String jp = requestJSON.getBody().getString(paramKey[i]);
                if (!RoUtil.isEmpty(jp)) {
                    info.getParams().put(paramKey[i], jp);
                    logger.info("【text-- the key:" + paramKey[i] + " ,value :" + jp + "】");
                } else {
                    logger.info("【warn-- the key:" + paramKey[i] + " is empty or is null!】");
                }
            }
        }

        info.setResponse(getResponseJSON(mac, method, sNumber, version, SysCode.SUCCESS, jsonStr));
        info.setState(SysCode.SUCCESS);
        logger.info("parameter SUCCESS !");
        return info;
    }

    /**
     * 描述：〈响应输出内容，包含跨域处理〉 <br/>
     * 作者：xuan.zhou@rogrand.com <br/>
     * 生成日期：2014-03-13 <br/>
     */
    protected void renderResponseJson(HttpServletRequest request, HttpServletResponse response, SysCode sysCode,
            Object content) {
        String callback = request.getParameter("callback");
        if (StringUtils.isNotEmpty(callback)) {
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("code", sysCode.getCode());
            result.put("message", sysCode.getMessage());
            result.put("result", content);

            ResponseUtils.renderText(response, callback + "(" + JSON.toJSON(result) + ");");
        } else {
            ResponseUtils.renderJson(response, JsonUtils.toJsonString(content, false));
        }
    }

    /**
     * 描述：〈响应输出内容，包含跨域处理〉 <br/>
     * 作者：xuan.zhou@rogrand.com <br/>
     * 生成日期：2014-03-13 <br/>
     */
    protected void renderResponseJson(HttpServletRequest request, HttpServletResponse response, Object content) {
        String callback = request.getParameter("callback");
        if (StringUtils.isNotEmpty(callback)) {
            ResponseUtils.renderText(response, callback + "(" + JSON.toJSON(content) + ");");
        } else {
            ResponseUtils.renderJson(response, JsonUtils.toJsonString(content, false));
        }
    }

    protected void renderResponseJson(HttpServletResponse response, ResponseJSON responseJson) {
        ResponseUtils.renderJson(response, JsonUtils.toJsonString(responseJson, false));
    }

    protected void renderResponseJson(HttpServletResponse response, ResponseJSON responseJson,
            String[] excludesProperties) {
        ResponseUtils.renderJson(response, JsonUtils.toJsonString(responseJson, null, excludesProperties, false));
    }

    protected void renderResponseJson(HttpServletResponse response, ResponseJSON responseJson,
            String[] includesProperties, String[] excludesProperties) {
        ResponseUtils.renderJson(response,
                JsonUtils.toJsonString(responseJson, includesProperties, excludesProperties, false));
    }

    protected String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
