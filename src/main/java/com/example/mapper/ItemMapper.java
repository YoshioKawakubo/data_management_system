package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.Item;

@Mapper
public interface ItemMapper {

	/**
	 * カテゴリ名の組み合わせでカテゴリテーブルのid検索をおこなう
	 * @param nameAll
	 * @return
	 */
	public Integer findCategoryByNameAll(String nameAll);
	
	/**
	 * 新しいItemの追加登録用
	 * @param item
	 * @return
	 */
	public void insert(@Param("item")Item item);
}
