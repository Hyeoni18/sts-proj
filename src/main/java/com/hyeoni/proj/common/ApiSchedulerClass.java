package com.hyeoni.proj.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hyeoni.proj.api.ApiService;

@Component
public class ApiSchedulerClass {
	
	@Autowired private ApiService ApiService;
	
	@Scheduled(cron="0 0 1 * * *") //1일 마다 실행 (매일 새벽 1시)  0 0 1 * * *
	public void cronScheduler() throws Exception {
	  ApiService.getApi();
	}
}
