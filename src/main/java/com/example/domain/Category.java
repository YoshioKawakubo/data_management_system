package com.example.domain;

public class Category {
	
	private Integer id;
	
	private Integer ParentId;
	
	private String CategoryName;
	
	private String nameAll;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return ParentId;
	}

	public void setParentId(Integer parentId) {
		ParentId = parentId;
	}

	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}

	public String getNameAll() {
		return nameAll;
	}

	public void setNameAll(String nameAll) {
		this.nameAll = nameAll;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", ParentId=" + ParentId + ", CategoryName=" + CategoryName + ", nameAll="
				+ nameAll + "]";
	}
	
	

}
