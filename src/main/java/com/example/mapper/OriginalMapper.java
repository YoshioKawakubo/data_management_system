package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.Original;

@Mapper
public interface OriginalMapper {
	
	public List<String> categoryList();
	
	
	/**
	 * 全件取得
	 * @return
	 */
	public List<Original> findAll(); 
	
	/**
	 * カテゴリの全件検索
	 * @return
	 */
	public List<String> findAllCategory();
}
