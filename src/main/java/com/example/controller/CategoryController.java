package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	/**
	 * @param parentCategory
	 * @return json形式でchildCategoryをjsに返す
	 */
	@GetMapping("/childGet")
	@ResponseBody
	public Map<String, List<String>> childGet(String parentCategory) {
		return categoryService.findChildCategoryByParentCategoryName(parentCategory);
	}
	
	@GetMapping("/grandGet")
	@ResponseBody
	public Map<String, List<String>> grandGet(String parentCategory, String childCategory) {
		return categoryService.findGrandChildByChildCategoryNameAndParentCategoryName(parentCategory, childCategory);
	}
}
