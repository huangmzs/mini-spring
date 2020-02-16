package com.java155.web.mvc;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)//用于参数
public @interface RequestParam {
	String value();
}
