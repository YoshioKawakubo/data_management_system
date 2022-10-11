package com.example.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class ItemAddForm {
	@NotEmpty(message="商品名を入力してください。")
	private String name;
	@NotNull(message="価格を入力してください。")
	private Double price;
	@NotEmpty(message="ブランドを入力してください。")
	private String brand;
	@NotNull(message="コンディションは選択必須です。")
	private Integer condition;
	@NotEmpty(message="入力必須です。")
	private String description;
	@NotEmpty(message="大カテゴリは選択必須です。")
	private String parentCategory;
	@NotEmpty(message="中カテゴリは選択必須です。")
	private String childCategory;
	@NotEmpty(message="小カテゴリは選択必須です。")
	private String grandChild;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Integer getCondition() {
		return condition;
	}
	public void setCondition(Integer condition) {
		this.condition = condition;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getParentCategory() {
		return parentCategory;
	}
	public void setParentCategory(String parentCategory) {
		this.parentCategory = parentCategory;
	}
	public String getChildCategory() {
		return childCategory;
	}
	public void setChildCategory(String childCategory) {
		this.childCategory = childCategory;
	}
	public String getGrandChild() {
		return grandChild;
	}
	public void setGrandChild(String grandChild) {
		this.grandChild = grandChild;
	}
	@Override
	public String toString() {
		return "ItemAddForm [name=" + name + ", price=" + price + ", brand=" + brand + ", condition=" + condition
				+ ", description=" + description + ", parentCategory=" + parentCategory + ", childCategory="
				+ childCategory + ", grandChild=" + grandChild + "]";
	}
	
	

}
