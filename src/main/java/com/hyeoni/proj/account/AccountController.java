package com.hyeoni.proj.account;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	@PostMapping("/create")
	public ModelAndView createAccount(@RequestParam Map<String, Object> map) {
		ModelAndView mav = new ModelAndView();
		String accountId = this.accountService.create(map);
		if(accountId == null) {
			mav.setViewName("redirect:/");
		} else {
			mav.setViewName("redirect:/detail");
		}
		return mav;
	}
	
	@GetMapping("/detail")
	public ModelAndView detailView() {
		return new ModelAndView("account/detail");
	}

}
