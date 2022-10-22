package com.hyeoni.proj.api;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ApiDao {
	
	@Autowired SqlSessionTemplate sqlSessionTemplate;
	
	public Integer insertApiData(Object map) {
		return this.sqlSessionTemplate.insert("api.insertApiData", map);
	}

}
