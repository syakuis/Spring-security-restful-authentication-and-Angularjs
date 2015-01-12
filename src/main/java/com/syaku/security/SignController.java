package com.syaku.security;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value="/signin", method = RequestMethod.GET)
	public String signin(@RequestParam(value="error", required=false) String error, Model model, HttpServletRequest request) {
		
		model.addAttribute("error", error);
		
		String referer = request.getHeader("referer");
		model.addAttribute("referer",referer);
		
		return "signin";
	}
	
	@PreAuthorize("authenticated")
	@RequestMapping(value="/mypage", method = RequestMethod.GET)
	public String mypage(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("user_name", auth.getName());
		
		return "mypage";
	}

	
	@RequestMapping(value="/denied", method = RequestMethod.GET)
	public String denied() {
		return "denied";
	}
	
}