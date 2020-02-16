package com.java155.web.handler;

import com.java155.web.mvc.Controller;
import com.java155.web.mvc.RequestMapping;
import com.java155.web.mvc.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * 映射地址
 */
public class HandlerManager {
	public static List<MappingHandler> mappingHandlerList=new ArrayList<>();

	public static List<Class<?>> testList=new ArrayList<>();

	public static void resolveMappingHandler(List<Class<?>> clss){
		for (Class<?> cls:clss){
			if(cls.isAnnotationPresent(Controller.class)){
				testList.add(cls);
				paresHandlerFromController(cls);
			}
		}
	}

	private static void paresHandlerFromController(Class<?> cls){
		Method[] methods = cls.getDeclaredMethods();
		for(Method method:methods){
			if(!method.isAnnotationPresent(RequestMapping.class)){
				continue;
			}
			String uri = method.getDeclaredAnnotation(RequestMapping.class).value();
			List<String> parmList=new ArrayList<>();
			for (Parameter parameter:method.getParameters()){
				if(parameter.isAnnotationPresent(RequestParam.class)){
					parmList.add(parameter.getDeclaredAnnotation(
									RequestParam.class).value());
				}
			}
			String[] parms=parmList.toArray(new String[parmList.size()]);
			MappingHandler mappingHandler=new MappingHandler(uri, method, cls,
							parms);
			HandlerManager.mappingHandlerList.add(mappingHandler);
		}
	}
}
