package com.java155.beans;


import com.java155.web.mvc.Controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean工厂管理对象
 */
public class BeanFactory {
	//将建好的Bean放入容器内
	private static Map<Class<?>,Object> classToBean= new ConcurrentHashMap<>();

	//获取Bean
	public static Object getBean(Class<?> cls){
		return classToBean.get(cls);
	}

	//初始化bean
	public static void initBean(List<Class<?>> classList) throws Exception {
		List<Class<?>> toCreate=new ArrayList<>(classList);
		while (toCreate.size()!=0){
			int toCreateSize = toCreate.size();
			for (int i = 0; i < toCreate.size(); i++) {
				if(finitCreate(toCreate.get(i))){
					toCreate.remove(i);
				}
			}
			if(toCreate.size()==toCreateSize){
				throw new Exception("cycle dependency");
			}
		}
	}

	private static boolean finitCreate(Class<?> cls) throws IllegalAccessException, InstantiationException {//创建bean对象
		if(!cls.isAnnotationPresent(Bean.class)&&!cls.isAnnotationPresent(Controller.class)){
			return true;
		}
		Object bean = cls.newInstance();//初始化Bean
		for (Field field:cls.getDeclaredFields()) {
			if(field.isAnnotationPresent(AutoWired.class)){//判断bean属性
				Class<?> fieldType = field.getType();
				Object reliantBean = BeanFactory.getBean(fieldType);
				if(reliantBean==null){
					return false;
				}
				field.setAccessible(true);
				field.set(bean, reliantBean);
			}
		}
		classToBean.put(cls, bean);
		return true;
	}

}
