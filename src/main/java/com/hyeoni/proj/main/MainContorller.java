package com.hyeoni.proj.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainContorller {
	
	@GetMapping("/view")
	public ModelAndView mainView() {
		return new ModelAndView("main/view");
	}

}
