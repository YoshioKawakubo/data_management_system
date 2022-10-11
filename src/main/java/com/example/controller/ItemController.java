package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.form.ItemAddForm;
import com.example.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@ModelAttribute
	public ItemAddForm setUpItemAddForm() {
		return new ItemAddForm();
	}
	
	
	@RequestMapping("/add")
	public String add(@Validated ItemAddForm itemAddForm, BindingResult result, Model model) {
		//入力値チェック
		if(result.hasErrors()) {
			return index();
		}
		Item item = new Item();
		//formの内容をItemオブジェクトにコピー
		BeanUtils.copyProperties(itemAddForm, item);
		//Itemのidを検索するためのserviceクラスメソッドの呼び出して、Itemオブジェクトのカテゴリにセット
		item.setCategory(itemService.findCategoryByNameAll(itemAddForm.getParentCategory(), itemAddForm.getChildCategory(), itemAddForm.getGrandChild()));
		//登録用serviceクラスメソッドの呼び出し
		itemService.register(item);
		return "list";
	}
	
	@RequestMapping("/")
	public String index() {
		return "add";
	}
	
	@RequestMapping("/edit")
	public String index3() {
		return "edit";
	}
	
	@RequestMapping("/list")
	public String index4() {
		return "list";
	}
	
	@RequestMapping("/login")
	public String index5() {
		return "login";
	}
	
	@RequestMapping("/register")
	public String index6() {
		return "register";
	}

}
