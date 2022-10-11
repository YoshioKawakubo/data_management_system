package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.CreateTableService;

@Controller
@RequestMapping("/category")
public class CreateTableController {
	
	@Autowired
	private CreateTableService createTableService;
	
	@RequestMapping("/largeCategoryRegister")
	public void largeCategoryRegister() {
		createTableService.largeCategoryRegister();
	}
	
	@RequestMapping("/middleCategoryRegister")
	public void middleCategoryRegister() {
		createTableService.middleCategoryRegister();
	}
	
	@RequestMapping("/smallCategoryRegister")
	public void smallCategoryRegister() {
		createTableService.smallCategoryRegister();
	}
}
