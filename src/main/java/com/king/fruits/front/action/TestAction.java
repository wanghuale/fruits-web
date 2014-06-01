package com.king.fruits.front.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.king.fruits.core.common.base.BaseController;
import com.king.fruits.front.service.api.TestService;

@Controller
@RequestMapping("test")
public class TestAction extends BaseController{
	
	@Autowired
	TestService testService;
	
	@RequestMapping(value = "/index")
	public String index(){
		
		return "/test/test";
	}
}
