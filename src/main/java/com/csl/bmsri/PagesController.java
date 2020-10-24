package com.csl.bmsri;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PagesController {
	
	@RequestMapping(value= {"/", "/login"})
	public String index(Model model)
	{
		System.out.println("Login Page");
		return "login";
	}


}
