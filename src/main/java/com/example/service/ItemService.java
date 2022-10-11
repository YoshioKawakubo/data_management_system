package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.mapper.ItemMapper;

@Service
@Transactional
public class ItemService {

	@Autowired
	private ItemMapper itemMapper;
	
	/**
	 * @param parentCategory
	 * @param childCategory
	 * @param grandChild
	 * @return category
	 */
	public Integer findCategoryByNameAll(String parentCategory, String childCategory, String grandChild) {
		String nameAll = parentCategory + "/" + childCategory + "/" + grandChild;
		System.out.println(nameAll);
		return itemMapper.findCategoryByNameAll(nameAll);
	}
	
	public void register(Item item) {
		itemMapper.insert(item);
	}
}
