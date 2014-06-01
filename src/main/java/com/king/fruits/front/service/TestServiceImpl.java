package com.king.fruits.front.service;

import org.springframework.stereotype.Service;

import com.king.fruits.core.model.KingTest;
import com.king.fruits.front.service.api.TestService;
import com.rogrand.kkmy.parent.db.api.DBInterface;
import com.rogrand.kkmy.parent.service.base.ParentServiceImpl;

@Service
public class TestServiceImpl extends ParentServiceImpl<KingTest> implements TestService{

	@Override
	protected DBInterface<KingTest> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

}
