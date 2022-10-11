package com.example.mapper.createTable;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.Category;

@Mapper
public interface CategoryMapper {

	public void insert(@Param("list") List<Category> category);
	
	public Integer findByLargeCategoryName(String categoryName);
	
	public Integer findByLargeCategoryForHome(String categoryName);
	
	public Integer findByLargeCategoryForOther(String categoryName);
	
	//parentIdとcategoryNameからidを検索するメソッド
	public Integer returnMiddleCategoryId(@Param("id")Integer parentId, @Param("name")String categoryName);
	
	//parentIdからcategoryNameをmiddleCategoryのcategoryNameを検索
	public List<String> returnMiddleCategoryNameByParentId(Integer parentId);
	
	//parentCategoryからchildCategory一覧を検索するメソッド
	public List<String> findChildCategoryByParentCategoryName(String parentCategory);
	
	//childCategoryとParentCategoryからgrandChild一覧を検索するメソッド
	public List<String> findGrandChildByChildCategoryNameAndParentCategoryName(@Param("parentCategory")String parentCategory, @Param("childCategory")String childCategory);
	
	//idからcategoryNameを検索するメソッド
	public String returnId(Integer id);
}
