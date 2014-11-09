package com.syaku.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class SigninSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private static final Logger logger = LoggerFactory.getLogger(SigninSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
		
		String accept = request.getHeader("accept");
		
		if( StringUtils.indexOf(accept, "html") > -1 ) {
			
			super.onAuthenticationSuccess(request, response, auth);
			
		} else if( StringUtils.indexOf(accept, "xml") > -1 ) {
			response.setContentType("application/xml");
			response.setCharacterEncoding("utf-8");
			
			String data = StringUtils.join(new String[] {
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
				"<response>",
				"<error>false</error>",
				"<message>로그인하였습니다.</message>",
				"</response>"
			});
			
			PrintWriter out = response.getWriter();
			out.print(data);
			out.flush();
			out.close();
			
		} else if( StringUtils.indexOf(accept, "json") > -1 ) {
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			
			String data = StringUtils.join(new String[] {
				" { \"response\" : {",
				" \"error\" : false , ",
				" \"message\" : \"로그인하였습니다.\" ",
				"} } "
			});
			
			PrintWriter out = response.getWriter();
			out.print(data);
			out.flush();
			out.close();
			
		}
	}
}