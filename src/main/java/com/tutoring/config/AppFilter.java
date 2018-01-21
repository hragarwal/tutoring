
package com.tutoring.config;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tutoring.exception.AppException;
import com.tutoring.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.tutoring.util.AppConstants;
import com.tutoring.util.AppUtils;
import com.tutoring.util.JWTGenerators;

public class AppFilter implements Filter {

	@Autowired
	private JWTGenerators jwtGenerator;

	@Autowired
	ProfileService profileService;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest  httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse  httpServletResponse = (HttpServletResponse) response;
		setResponseHeaders(httpServletRequest, httpServletResponse);

		String accessToken = 	((HttpServletRequest) request).getHeader("Authorization");

		if(Objects.nonNull(accessToken)) {
			 accessToken = accessToken.substring(7);
		}
		// not required once old version is removed from backend app
		if(httpServletRequest.getSession().getAttribute(AppConstants.ACCESS_TOKEN) != null) {
			accessToken = (String) httpServletRequest.getSession().getAttribute(AppConstants.ACCESS_TOKEN);
		}

		if("OPTIONS".equals(httpServletRequest.getMethod())){
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
			return;
		}
		else if(AppUtils.isExcludedURL(httpServletRequest)) {
			chain.doFilter(request,response);
		}
		else{
			if(AppUtils.isNotBlank(accessToken)){
				long profileId = jwtGenerator.decrypt(accessToken);
				if(profileId > 0) {
					try {
						((HttpServletRequest) request).getSession().setAttribute(AppConstants.PROFILE, profileService.getProfile(profileId));
						((HttpServletRequest) request).getSession().setAttribute(AppConstants.ACCESS_TOKEN, accessToken);
						chain.doFilter(request, response);
					}catch (AppException ex) {
						ex.printStackTrace();
					}
				}
				else if(response instanceof ServletResponse){
					System.out.println("App shutdown called after");
					httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					return;
				}
			}else{
				if(response instanceof ServletResponse){
					System.out.println("App shutdown called after");
					httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					return;
				}
			}
		}
	}

	public void setResponseHeaders(HttpServletRequest request, HttpServletResponse response){
		String origin = request.getHeader("origin");
		response.setHeader("Access-Control-Allow-Origin", origin);
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
		response.setHeader("Access-Control-Allow-Credentials","true");
		response.setHeader("Access-Control-Allow-Headers", "accept,content-type");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,filterConfig.getServletContext());
	}

}
