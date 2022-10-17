package com.hyeoni.proj.account;

import java.util.List;
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
	
	@GetMapping("/create")
	public ModelAndView mainView() {
		return new ModelAndView("account/view");
	}
	
	@PostMapping("/create")
	public ModelAndView createAccount(@RequestParam Map<String, Object> map) {
		ModelAndView mav = new ModelAndView();
		String accountId = this.accountService.create(map);
		if(accountId == null) {
			mav.setViewName("redirect:/");
		} else {
			mav.setViewName("redirect:/detail?accountId="+accountId);
		}
		return mav;
	}
	
	@GetMapping("/detail")
	public ModelAndView detailView(@RequestParam Map<String, Object> map) {
		Map<String, Object> detailMap = this.accountService.detail(map);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("data", detailMap);
		String accountId = map.get("accountId").toString();
		mav.addObject("accountId", accountId);
		mav.setViewName("account/detail");
		return mav;
	}
	
	@GetMapping("/update")
	public ModelAndView updateView(@RequestParam Map<String, Object> map) {
		Map<String, Object> detailMap = this.accountService.detail(map);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("data", detailMap);
		mav.setViewName("/account/update");
		return mav;
	}
	
	@PostMapping("/update")
	public ModelAndView updateAccount(@RequestParam Map<String, Object> map) {
		ModelAndView mav = new ModelAndView();
		boolean isUpdateSuccess = this.accountService.edit(map);
		if(isUpdateSuccess) {
			String accountId = map.get("accountId").toString();
			mav.setViewName("redirect:/detail?accountId="+accountId);
		} else {
			mav = this.updateView(map);
		}
		return mav;
	}
	
	@PostMapping("/delete")
	public ModelAndView deleteAccount(@RequestParam Map<String, Object> map) {
		ModelAndView mav = new ModelAndView();
		boolean isDeleteSuccess = this.accountService.remove(map);
		if(isDeleteSuccess) {
			mav.setViewName("redirect:/list");
		} else {
			String accountId = map.get("accountId").toString();
			mav.setViewName("redirect:/detail?accountId="+accountId);
		}
		return mav;
	}
	
	@GetMapping("/list")
	public ModelAndView list(@RequestParam Map<String, Object> map) {
		List<Map<String, Object>> list = this.accountService.list(map);
		ModelAndView mav = new ModelAndView();
		mav.addObject("data", list);
		if(map.containsKey("keyword")) {
			mav.addObject("keyword", map.get("keyword"));
		}
		mav.setViewName("/account/list");
		return mav;
	}
}
