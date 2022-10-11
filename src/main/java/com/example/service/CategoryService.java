package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mapper.createTable.CategoryMapper;

@Service
@Transactional
public class CategoryService {
	@Autowired
	private CategoryMapper categoryMapper;
	
	public Map<String, List<String>> findChildCategoryByParentCategoryName(String parentCategory) {
		List<String> childCategoryList =  categoryMapper.findChildCategoryByParentCategoryName(parentCategory);
		Map<String, List<String>> map = new HashMap<>();
//		for(String childCategory : childCategoryList) {
//			map.put("childCategory", childCategory);
//		}
		map.put("childCategory", childCategoryList);
		return map;
	}

	
	public Map<String, List<String>>  findGrandChildByChildCategoryNameAndParentCategoryName(String parentCategory, String childCategory) {
		List<String> grandChildList = categoryMapper.findGrandChildByChildCategoryNameAndParentCategoryName(parentCategory, childCategory);
		Map<String, List<String>> map = new HashMap<>();
		map.put("grandChild", grandChildList);
		return map;
	}
}
