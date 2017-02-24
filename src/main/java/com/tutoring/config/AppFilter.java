package com.tutoring.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.tutoring.util.JWTGenerators;

public class AppFilter implements Filter {

	@Autowired
	private JWTGenerators jwtGenerator;

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		chain.doFilter(request,response);
		
		
		
		/*HttpServletRequest  httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse  httpServletResponse = (HttpServletResponse) response;
		setResponseHeaders(httpServletRequest, httpServletResponse);

		Object accessToken = httpServletRequest.getSession().getAttribute(AppConstants.ACCESS_TOKEN);
		String token = null;
		
		if(Objects.nonNull(accessToken)) {
			token = String.valueOf(accessToken);
		}

		if("OPTIONS".equals(httpServletRequest.getMethod())){
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
			return;
		}
		else if(!AppUtils.isGoToLogin(httpServletRequest)){
			chain.doFilter(request,response);
		}
		else{
			if(AppUtils.isNotBlank(token) && jwtGenerator.decrypt(token)){
				chain.doFilter(request,response);
			}else{
				if(response instanceof ServletResponse){
					httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					return;
				}
			}
		}*/
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
