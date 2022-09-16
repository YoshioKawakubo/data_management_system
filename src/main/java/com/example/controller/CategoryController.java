package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.OriginalService;

@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private OriginalService originalService;
	
	@RequestMapping("/largeCategoryRegister")
	public void largeCategoryRegister() {
		originalService.largeCategoryRegister();
	}
	
	@RequestMapping("/middleCategoryRegister")
	public void middleCategoryRegister() {
		originalService.middleCategoryRegister();
	}
	
	@RequestMapping("/smallCategoryRegister")
	public void smallCategoryRegister() {
		originalService.smallCategoryRegister();
	}
}
