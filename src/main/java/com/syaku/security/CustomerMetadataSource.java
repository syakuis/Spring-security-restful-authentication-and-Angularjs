package com.syaku.security;

import org.apache.commons.io.FileUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.RequestMatcher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

public class CustomerMetadataSource extends DefaultFilterInvocationSecurityMetadataSource {

	public CustomerMetadataSource(LinkedHashMap< RequestMatcher, Collection<ConfigAttribute> > requestMap) {
		super(requestMap);
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		
		FilterInvocation fi = (FilterInvocation) object;
		String url = fi.getRequestUrl();
		
		String httpMethod = fi.getRequest().getMethod();
		System.out.println("HTTP method: [" + httpMethod + "]. HTTP URL: [" + url + "].");
		
		if (url != null && url.startsWith("/welcome")) {
			ConfigAttribute configAttribute = new ConfigAttribute() {
				@Override
				public String getAttribute() {
					try {
						final String path = System.getProperty("user.home") + "/.motech/security-db.txt";
						final String attr = FileUtils.readFileToString(new File(path), "UTF-8");
						
						System.out.println("Config attr for /wecome: [" + attr + "]");
						
						return attr;
					} catch (IOException e) {
						return null;
					}
					
				}
			};
				
			Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
			configAttributes.add(configAttribute);
			return configAttributes;
		}
		
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
}