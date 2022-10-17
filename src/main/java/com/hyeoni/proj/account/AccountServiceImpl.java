package com.hyeoni.proj.account;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	AccountDto accountDto;
	
	@Override
	public String create(Map<String, Object> map) {
		int rowCount = this.accountDto.insert(map);
		if(rowCount == 1) {
			System.out.println(map);
			return map.get("accountId").toString();
		}
		return null;
	}

}
