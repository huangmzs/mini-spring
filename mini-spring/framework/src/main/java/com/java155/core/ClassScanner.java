package com.java155.core;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类扫描器
 * 将jar包文件下的类扫描到容器类
 */
public class ClassScanner {

	public static List<Class<?>> scanClasses(String packageName) throws IOException, ClassNotFoundException {
		List<Class<?>> classList=new ArrayList<>();//创建一个容器
		String path=packageName.replace(".", "/");
		ClassLoader classLoader=Thread.currentThread().getContextClassLoader();//获取类加载器
		Enumeration<URL> resources = classLoader.getResources(path);
		while (resources.hasMoreElements()){
			URL resource = resources.nextElement();
			if(resource.getProtocol().contains("jar")){
				JarURLConnection jarURLConnection = (JarURLConnection) resource.openConnection();
					String jarFilePath=jarURLConnection.getJarFile().getName();//E:\Users\idea-workspace2019\mini-spring\framework\build\libs\framework-1.0-SNAPSHOT.jar
					classList.addAll(getClassFromJar(jarFilePath, path));
			}else {
					//todo
			}
		}
		return classList;
	}

	public static List<Class<?>> getClassFromJar(String jarFilePath,String path) throws IOException, ClassNotFoundException {
		List<Class<?>> clases=new ArrayList<>();//创建一个容器
		JarFile jarFile=new JarFile(jarFilePath);
		Enumeration<JarEntry> jarEntries = jarFile.entries();
		while (jarEntries.hasMoreElements()){
			JarEntry jarEntry = jarEntries.nextElement();
			String entryName = jarEntry.getName(); // com/java155/test/test.class
			if(entryName.startsWith(path)&&entryName.endsWith(".class")){
				String classfullName = entryName.replace("/",
								".").substring(0, entryName.length() - 6);
				clases.add(Class.forName(classfullName));
			}
		}
		return clases;
	}

}
