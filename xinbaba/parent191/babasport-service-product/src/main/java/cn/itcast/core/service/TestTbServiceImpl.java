package cn.itcast.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.bean.TestTb;
import cn.itcast.core.dao.TestTbDao;

@Service
@Transactional
public class TestTbServiceImpl implements TestTbService{

	@Autowired
	private TestTbDao testTbDao;
	
	@Override
	public void insertTestTb(TestTb testTb) {

		testTbDao.insertTestTb(testTb);
		
	}

}
