package com.java155.web.mvc;

import java.lang.annotation.*;

@Documented //注解表明这个注解应该被 javadoc工具记录
@Retention(RetentionPolicy.RUNTIME)//注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
@Target(ElementType.METHOD)//用于描述类、接口(方法)
public @interface RequestMapping {
	String value();
}
