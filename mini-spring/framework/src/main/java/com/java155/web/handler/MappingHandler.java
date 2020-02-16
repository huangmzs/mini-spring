package com.java155.web.handler;

import com.java155.beans.BeanFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 将url地址映射到Controller方法执行并放回
 */
public class MappingHandler {
	private String uri;
	private Method method;
	private Class<?> controller;
	private String[] args;

	public boolean handle(ServletRequest request, ServletResponse response) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
		String requestUri = ((HttpServletRequest) request).getRequestURI();
		if(!uri.equals(requestUri)){
			return false;
		}
		Object[] parameter=new Object[args.length];
		for (int i = 0; i < args.length; i++) {
			 parameter[i] = request.getParameter(args[i]);
		}
		Object cls = BeanFactory.getBean(controller);
		Object respons = method.invoke(cls, parameter);//调用方法返回结果
		response.getWriter().println(respons);
		return true;
	}

	MappingHandler(String uri,Method method,Class<?> controller,String[] args){
		this.uri=uri;
		this.method=method;
		this.controller=controller;
		this.args=args;
	}


}
