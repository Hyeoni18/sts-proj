package com.hyeoni.proj.account;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	AccountDao accountDao;
	
	@Override
	public String create(Map<String, Object> map) {
		int rowCount = this.accountDao.insert(map);
		if(rowCount == 1) {
			System.out.println(map);
			return map.get("accountId").toString();
		}
		return null;
	}

	@Override
	public Map<String, Object> detail(Map<String, Object> map) {
		return this.accountDao.selectDetail(map);
	}
	
	@Override
	public boolean edit(Map<String, Object> map) {
		int rowCount = this.accountDao.update(map);
		return rowCount == 1;
	}
	
	@Override
	public boolean remove(Map<String, Object> map) {
		int rowCount = this.accountDao.delete(map);
		return rowCount == 1;
	}
	
	@Override
	public List<Map<String, Object>> list(Map<String, Object> map) {
		return this.accountDao.selectList(map);
	}
}
