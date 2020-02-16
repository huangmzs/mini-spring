package com.java155.starter;

import com.java155.beans.BeanFactory;
import com.java155.core.ClassScanner;
import com.java155.web.handler.HandlerManager;
import com.java155.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.IOException;
import java.util.List;

public class MiniApplication {
	public static void run(Class<?> cls,String[] args){
		TomcatServer tomcatServer=new TomcatServer(args);
		try {
			tomcatServer.startServer();//运行一个tomcat服务
			List<Class<?>> classList = ClassScanner.scanClasses(
							cls.getPackage().getName());//类扫描器
			BeanFactory.initBean(classList);
			HandlerManager.resolveMappingHandler(classList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
