package com.java155.controllers;

import com.java155.beans.AutoWired;
import com.java155.service.SalaryService;
import com.java155.web.mvc.Controller;
import com.java155.web.mvc.RequestMapping;
import com.java155.web.mvc.RequestParam;

@Controller
public class SalaryController {

	@AutoWired
	private SalaryService salaryService;

	@RequestMapping("/get_salary.json")
	public Integer getSalary(@RequestParam("name") String name,@RequestParam("experience") String experience){
		return salaryService.calsalary(Integer.parseInt(experience));
	}
}
