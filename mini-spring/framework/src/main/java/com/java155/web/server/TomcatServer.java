package com.java155.web.server;

import com.java155.web.servlet.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

/**
 * 嵌入一个tomcat
 * 跟Servlet建立联系
 */
public class TomcatServer {
	private Tomcat tomcat;
	private String[] args;

	public TomcatServer(String[] args){
		this.args=args;
	}


	public void startServer() throws LifecycleException {//启动方法
		tomcat=new Tomcat();
		tomcat.setPort(6699);
		tomcat.start();

		//实现一个容器
		Context context=new StandardContext();
		context.setPath("");//一个路径
		context.addLifecycleListener(new Tomcat.FixContextListener());//设置一个监听器


		DispatcherServlet dispatcherServlet=new DispatcherServlet();//实例化Servlet
		tomcat.addServlet(//添加一个Servlet并支持异步
						context,
						"dispatcherServlet", dispatcherServlet)
						.setAsyncSupported(true);

		context.addServletMappingDecoded(//添加一个Servlet到url的映射
						"/",
						"dispatcherServlet");
		tomcat.getHost().addChild(context);//tomcat的Context容器需要依附在Host容器内tomcat和Servlet的联系建立完成

		//防止结束创建一个等待线程
		Thread awaitThread=new Thread("tomcat_await_thread"){
			@Override
			public void run() {
				TomcatServer.this.tomcat.getServer().await();//tomcat一直在等待
			}
		};
		awaitThread.setDaemon(false);//非守护线程
		awaitThread.start();
	}

}
