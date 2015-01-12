package com.syaku.security;

import java.security.Principal;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Resource(name = "homeService")
	private HomeService homeService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model,@RequestParam(value="user", defaultValue="", required=true) String user) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		UserVO userVO = new UserVO();
		userVO.setUser_name(user);
		
		homeService.getUser(userVO);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin() {
		return "admin";
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "/admin/test", method = RequestMethod.GET)
	public String admin_test() {
		return "admin";
	}
	
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String user(Principal principal) {
		
		// 첫번째 방법 
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info(auth.toString());
		
		// 두번째 방법
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.info(user.toString());
		
		// 세번째 방법
		logger.info(principal.toString());
		
		return "home";
	}
	
}
