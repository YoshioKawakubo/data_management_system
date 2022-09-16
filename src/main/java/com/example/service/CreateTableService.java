package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.domain.Original;
import com.example.mapper.createTable.CategoryMapper;
import com.example.mapper.createTable.OriginalMapper;

@Service
@Transactional
public class CreateTableService {

	@Autowired
	private OriginalMapper originalMapper;

	@Autowired
	private CategoryMapper categoryMapper;

	/**
	 * カテゴリデータのDB登録用
	 */
	public void categoryRegister() {
		// オリジナルデータの全件取得
		List<Original> originalList = originalMapper.findAll();
		// 重複ありの全件データのカテゴリリストを作成
		List<Category> DuplicationCategories = new ArrayList<>();
		// データを1行ずつ取得
		for (Original original : originalList) {
			String OriginalCategoryName = original.getCategoryName();
			if (!(OriginalCategoryName == null || OriginalCategoryName.equals(""))) {
				String[] originalCategoryNames = OriginalCategoryName.split("/");
				for (int i = 0; i < originalCategoryNames.length; i++) {
					Category category = new Category();
					Category largeCategory = new Category();
					Category middleCategory = new Category();
					// ラージカテゴリはcategoryNameのみセット
					if (i == 0) {
						largeCategory.setCategoryName(originalCategoryNames[i]);
						DuplicationCategories.add(largeCategory);
					}
					// ミドルカテゴリはParentIdとcategoryNameをセット
					else if (i == 1) {
						middleCategory.setParentId(largeCategory.getId());
						middleCategory.setCategoryName(originalCategoryNames[i]);
						DuplicationCategories.add(middleCategory);
					}
					// スモールカテゴリは全てセット
					else if (i == 2) {
						category.setParentId(middleCategory.getId());
						category.setCategoryName(originalCategoryNames[2]);
						category.setNameAll(OriginalCategoryName);
						DuplicationCategories.add(category);
					}
				}
			}
			// 重複を削除したcategoryのリストを作成。
			List<Category> categoryList = DuplicationCategories.stream().distinct().collect(Collectors.toList());
			categoryMapper.insert(categoryList);
		}
	}

	/**
	 * 大カテゴリデータのDB登録用
	 */
	public void largeCategoryRegister() {
		// オリジナルデータの全件取得(重複あり)
		List<String> originalList = originalMapper.findAllCategory();
		// 重複なしのカテゴリデータ格納用リスト
		List<String> categoryList = originalList.stream().distinct().collect(Collectors.toList());
		// 大カテゴリで被りあり用リスト
		List<String> largeArray = new ArrayList<>();
		// 大中小カテゴリで被りなし用リスト
		List<String> largeElementList = new ArrayList<>();
		// データを1行ずつ取得してcategoryに格納
		int listSize = categoryList.size();
		for (int i = 0; i < listSize; i++) {
			// カテゴリを大中小に分割して被りありに追加
			String[] categoryElement = categoryList.get(i).split("/");
			largeArray.add(categoryElement[0]);
		}
		// 被りありリスト→Streamで被りなしリストを作成
		largeElementList = largeArray.stream().distinct().collect(Collectors.toList());
		// DBインサート用リストの作成
		List<Category> largeCategoreis = new ArrayList<>();
		for (String large : largeElementList) {
			Category largeCategory = new Category();
			largeCategory.setCategoryName(large);
			largeCategoreis.add(largeCategory);
		}
		// インサート
		categoryMapper.insert(largeCategoreis);
	}

	/**
	 * 中カテゴリデータのDB登録用
	 */
	public void middleCategoryRegister() {
		// オリジナルデータの全件取得(重複あり)
		List<String> originalList = originalMapper.findAllCategory();
		// 重複なしのカテゴリデータ格納用リスト
		List<String> categoryList = originalList.stream().distinct().collect(Collectors.toList());
		// 中カテゴリで被りあり用リスト
		List<String> middleArray = new ArrayList<>();
		// 大中小カテゴリで被りなし用リスト
		List<String> middleElementList = new ArrayList<>();
		// 親カテゴリ別にリストを生成
		List<String> sportsList = categoryList.stream().filter(str -> str.startsWith("Sports"))
				.collect(Collectors.toList());
		List<String> womenList = categoryList.stream().filter(str -> str.startsWith("Women"))
				.collect(Collectors.toList());
		List<String> beautyList = categoryList.stream().filter(str -> str.startsWith("Beauty"))
				.collect(Collectors.toList());
		List<String> kidsList = categoryList.stream().filter(str -> str.startsWith("Kids"))
				.collect(Collectors.toList());
		List<String> electronicsList = categoryList.stream().filter(str -> str.startsWith("Electronics"))
				.collect(Collectors.toList());
		List<String> menList = categoryList.stream().filter(str -> str.startsWith("Men")).collect(Collectors.toList());
		List<String> vintageList = categoryList.stream().filter(str -> str.startsWith("Vintage"))
				.collect(Collectors.toList());
		List<String> homeList = categoryList.stream().filter(str -> str.startsWith("Home"))
				.collect(Collectors.toList());
		List<String> otherList = categoryList.stream().filter(str -> str.startsWith("Other"))
				.collect(Collectors.toList());
		List<String> handmadeList = categoryList.stream().filter(str -> str.startsWith("Handmade"))
				.collect(Collectors.toList());
		// データを1行ずつ取得してcategoryに格納
		for (int i = 0; i < sportsList.size(); i++) {
			// カテゴリを大中小に分割して被りありに追加
			String[] categoryElement = sportsList.get(i).split("/");
			middleArray.add(categoryElement[1]);
			categoryElement = null;
		}
		// 被りありリスト → Streamで被りなしリストを作成
		middleElementList = middleArray.stream().distinct().collect(Collectors.toList());
		// DBインサート用リストの作成
		List<Category> middleCategoreis = new ArrayList<>();
		for (String middle : middleElementList) {
			Category middleCategory = new Category();
			middleCategory.setCategoryName(middle);
			// 入らなかった為直入れ
			middleCategory.setParentId(1);
			middleCategoreis.add(middleCategory);
		}
		// インサート
		categoryMapper.insert(middleCategoreis);
		// リスト使いまわすために一旦削除。
		middleArray.clear();
		middleElementList.clear();
		middleCategoreis.clear();

		// --2回目以降の繰り返し ②
		// データを1行ずつ取得してcategoryに格納
		for (int i = 0; i < womenList.size(); i++) {
			// カテゴリを大中小に分割して被りありに追加
			String[] categoryElement = womenList.get(i).split("/");
			middleArray.add(categoryElement[1]);
			System.out.println(middleArray);
			categoryElement = null;
		}
		// 被りありリスト → Streamで被りなしリストを作成
		middleElementList = middleArray.stream().distinct().collect(Collectors.toList());
		System.out.println(middleElementList);
		for (String middle : middleElementList) {
			System.out.println(middle);
			Category middleCategory = new Category();
			middleCategory.setCategoryName(middle);
			middleCategory.setParentId(categoryMapper.findByLargeCategoryName("Women"));
			middleCategoreis.add(middleCategory);
		}
		// インサート
		categoryMapper.insert(middleCategoreis);
		// リスト使いまわすために一旦削除。
		middleArray.clear();
		middleElementList.clear();
		middleCategoreis.clear();

		// --2回目以降の繰り返し ③
		// データを1行ずつ取得してcategoryに格納
		for (int i = 0; i < beautyList.size(); i++) {
			// カテゴリを大中小に分割して被りありに追加
			String[] categoryElement = beautyList.get(i).split("/");
			middleArray.add(categoryElement[1]);
			categoryElement = null;
		}
		// 被りありリスト → Streamで被りなしリストを作成
		middleElementList = middleArray.stream().distinct().collect(Collectors.toList());
		for (String middle : middleElementList) {
			Category middleCategory = new Category();
			middleCategory.setCategoryName(middle);
			middleCategory.setParentId(categoryMapper.findByLargeCategoryName("Beauty"));
			middleCategoreis.add(middleCategory);
		}
		// インサート
		categoryMapper.insert(middleCategoreis);
		// リスト使いまわすために一旦削除。
		middleArray.clear();
		middleElementList.clear();
		middleCategoreis.clear();

		// --2回目以降の繰り返し ④
		// データを1行ずつ取得してcategoryに格納
		for (int i = 0; i < kidsList.size(); i++) {
			// カテゴリを大中小に分割して被りありに追加
			String[] categoryElement = kidsList.get(i).split("/");
			middleArray.add(categoryElement[1]);
			categoryElement = null;
		}
		// 被りありリスト → Streamで被りなしリストを作成
		middleElementList = middleArray.stream().distinct().collect(Collectors.toList());
		for (String middle : middleElementList) {
			Category middleCategory = new Category();
			middleCategory.setCategoryName(middle);
			middleCategory.setParentId(categoryMapper.findByLargeCategoryName("Kids"));
			middleCategoreis.add(middleCategory);
		}
		// インサート
		categoryMapper.insert(middleCategoreis);
		// リスト使いまわすために一旦削除。
		middleArray.clear();
		middleElementList.clear();
		middleCategoreis.clear();

		// --2回目以降の繰り返し ⑤
		// データを1行ずつ取得してcategoryに格納
		for (int i = 0; i < electronicsList.size(); i++) {
			// カテゴリを大中小に分割して被りありに追加
			String[] categoryElement = electronicsList.get(i).split("/");
			middleArray.add(categoryElement[1]);
			categoryElement = null;
		}
		// 被りありリスト → Streamで被りなしリストを作成
		middleElementList = middleArray.stream().distinct().collect(Collectors.toList());
		for (String middle : middleElementList) {
			Category middleCategory = new Category();
			middleCategory.setCategoryName(middle);
			middleCategory.setParentId(5);
			middleCategoreis.add(middleCategory);
		}
		// インサート
		categoryMapper.insert(middleCategoreis);
		// リスト使いまわすために一旦削除。
		middleArray.clear();
		middleElementList.clear();
		middleCategoreis.clear();

		// --2回目以降の繰り返し ⑥
		// データを1行ずつ取得してcategoryに格納
		for (int i = 0; i < menList.size(); i++) {
			// カテゴリを大中小に分割して被りありに追加
			String[] categoryElement = menList.get(i).split("/");
			middleArray.add(categoryElement[1]);
			categoryElement = null;
		}
		// 被りありリスト → Streamで被りなしリストを作成
		middleElementList = middleArray.stream().distinct().collect(Collectors.toList());
		for (String middle : middleElementList) {
			Category middleCategory = new Category();
			middleCategory.setCategoryName(middle);
			middleCategory.setParentId(categoryMapper.findByLargeCategoryName("Men"));
			middleCategoreis.add(middleCategory);
		}
		// インサート
		categoryMapper.insert(middleCategoreis);
		// リスト使いまわすために一旦削除。
		middleArray.clear();
		middleElementList.clear();
		middleCategoreis.clear();

		// --2回目以降の繰り返し ⑦
		// データを1行ずつ取得してcategoryに格納
		for (int i = 0; i < vintageList.size(); i++) {
			// カテゴリを大中小に分割して被りありに追加
			String[] categoryElement = vintageList.get(i).split("/");
			middleArray.add(categoryElement[1]);
			categoryElement = null;
		}
		// 被りありリスト → Streamで被りなしリストを作成
		middleElementList = middleArray.stream().distinct().collect(Collectors.toList());
		for (String middle : middleElementList) {
			Category middleCategory = new Category();
			middleCategory.setCategoryName(middle);
			// 入らなかった為直入れ
			middleCategory.setParentId(7);
			middleCategoreis.add(middleCategory);
		}
		// インサート
		categoryMapper.insert(middleCategoreis);
		// リスト使いまわすために一旦削除。
		middleArray.clear();
		middleElementList.clear();
		middleCategoreis.clear();

		// --2回目以降の繰り返し ⑧
		// データを1行ずつ取得してcategoryに格納
		for (int i = 0; i < homeList.size(); i++) {
			// カテゴリを大中小に分割して被りありに追加
			String[] categoryElement = homeList.get(i).split("/");
			middleArray.add(categoryElement[1]);
			categoryElement = null;
		}
		// 被りありリスト → Streamで被りなしリストを作成
		middleElementList = middleArray.stream().distinct().collect(Collectors.toList());
		for (String middle : middleElementList) {
			Category middleCategory = new Category();
			middleCategory.setCategoryName(middle);
			// 入らなかった為直入れ
			middleCategory.setParentId(8);
			middleCategoreis.add(middleCategory);
		}
		// インサート
		categoryMapper.insert(middleCategoreis);
		// リスト使いまわすために一旦削除。
		middleArray.clear();
		middleElementList.clear();
		middleCategoreis.clear();

		// --2回目以降の繰り返し ⑨
		// データを1行ずつ取得してcategoryに格納
		for (int i = 0; i < otherList.size(); i++) {
			// カテゴリを大中小に分割して被りありに追加
			String[] categoryElement = otherList.get(i).split("/");
			middleArray.add(categoryElement[1]);
			categoryElement = null;
		}
		// 被りありリスト → Streamで被りなしリストを作成
		middleElementList = middleArray.stream().distinct().collect(Collectors.toList());
		for (String middle : middleElementList) {
			Category middleCategory = new Category();
			middleCategory.setCategoryName(middle);
			// 入らなかった為直入れ
			middleCategory.setParentId(9);
			middleCategoreis.add(middleCategory);
		}
		// インサート
		categoryMapper.insert(middleCategoreis);
		// リスト使いまわすために一旦削除。
		middleArray.clear();
		middleElementList.clear();
		middleCategoreis.clear();

		// --2回目以降の繰り返し ⑩
		// データを1行ずつ取得してcategoryに格納
		for (int i = 0; i < handmadeList.size(); i++) {
			// カテゴリを大中小に分割して被りありに追加
			String[] categoryElement = handmadeList.get(i).split("/");
			middleArray.add(categoryElement[1]);
			categoryElement = null;
		}
		// 被りありリスト → Streamで被りなしリストを作成
		middleElementList = middleArray.stream().distinct().collect(Collectors.toList());
		for (String middle : middleElementList) {
			Category middleCategory = new Category();
			middleCategory.setCategoryName(middle);
			middleCategory.setParentId(categoryMapper.findByLargeCategoryName("Handmade"));
			middleCategoreis.add(middleCategory);
		}
		// インサート
		categoryMapper.insert(middleCategoreis);
		// リスト使いまわすために一旦削除。
		middleArray.clear();
		middleElementList.clear();
		middleCategoreis.clear();
	}

	/**
	 * 小カテゴリデータのDB登録用
	 */
	public void smallCategoryRegister() {
		// オリジナルデータの全件取得(重複あり)
		List<String> originalList = originalMapper.findAllCategory();
		// 重複なしのカテゴリデータ格納用リスト
		List<String> categoryList = originalList.stream().distinct().collect(Collectors.toList());
		// 小カテゴリで被りあり用リスト
		List<Category> smallArray = new ArrayList<>();
		// 小カテゴリで被りなし用リスト
		List<Category> smallElementList = new ArrayList<>();
		// 親カテゴリ別にリストを生成
		List<String> sportsList = categoryList.stream().filter(str -> str.startsWith("Sports"))
				.collect(Collectors.toList());
		List<String> womenList = categoryList.stream().filter(str -> str.startsWith("Women"))
				.collect(Collectors.toList());
		List<String> beautyList = categoryList.stream().filter(str -> str.startsWith("Beauty"))
				.collect(Collectors.toList());
		List<String> kidsList = categoryList.stream().filter(str -> str.startsWith("Kids"))
				.collect(Collectors.toList());
		List<String> electronicsList = categoryList.stream().filter(str -> str.startsWith("Electronics"))
				.collect(Collectors.toList());
		List<String> menList = categoryList.stream().filter(str -> str.startsWith("Men")).collect(Collectors.toList());
		List<String> vintageList = categoryList.stream().filter(str -> str.startsWith("Vintage"))
				.collect(Collectors.toList());
		List<String> homeList = categoryList.stream().filter(str -> str.startsWith("Home"))
				.collect(Collectors.toList());
		List<String> otherList = categoryList.stream().filter(str -> str.startsWith("Other"))
				.collect(Collectors.toList());
		List<String> handmadeList = categoryList.stream().filter(str -> str.startsWith("Handmade"))
				.collect(Collectors.toList());

		// ①
		// 重複あり状態でインサート用リストの作成
		for (int i = 0; i < sportsList.size(); i++) {
			Category smallCategory = new Category();
			String[] categoryElement = sportsList.get(i).split("/");
			smallCategory.setCategoryName(categoryElement[2]);
			smallCategory.setNameAll(sportsList.get(i));
			smallCategory.setParentId(categoryMapper.returnMiddleCategoryId(1, categoryElement[1]));
			smallArray.add(smallCategory);
		}
		// 重複を削除
		smallElementList = smallArray.stream().distinct().collect(Collectors.toList());
		categoryMapper.insert(smallElementList);
		// リスト使いまわすために一旦削除。
		smallArray.clear();
		smallElementList.clear();

		// ②
		// 重複あり状態でインサート用リストの作成
		for (int i = 0; i < womenList.size(); i++) {
			Category smallCategory = new Category();
			String[] categoryElement = womenList.get(i).split("/");
			smallCategory.setCategoryName(categoryElement[2]);
			smallCategory.setNameAll(womenList.get(i));
			smallCategory.setParentId(categoryMapper.returnMiddleCategoryId(2, categoryElement[1]));
			smallArray.add(smallCategory);
		}
		// 重複を削除
		smallElementList = smallArray.stream().distinct().collect(Collectors.toList());
		categoryMapper.insert(smallElementList);
		// リスト使いまわすために一旦削除。
		smallArray.clear();
		smallElementList.clear();

		// ③
		// 重複あり状態でインサート用リストの作成
		for (int i = 0; i < beautyList.size(); i++) {
			Category smallCategory = new Category();
			String[] categoryElement = beautyList.get(i).split("/");
			smallCategory.setCategoryName(categoryElement[2]);
			smallCategory.setNameAll(beautyList.get(i));
			smallCategory.setParentId(categoryMapper.returnMiddleCategoryId(3, categoryElement[1]));
			smallArray.add(smallCategory);
		}
		// 重複を削除
		smallElementList = smallArray.stream().distinct().collect(Collectors.toList());
		categoryMapper.insert(smallElementList);
		// リスト使いまわすために一旦削除。
		smallArray.clear();
		smallElementList.clear();

		// ④
		// 重複あり状態でインサート用リストの作成
		for (int i = 0; i < kidsList.size(); i++) {
			Category smallCategory = new Category();
			String[] categoryElement = kidsList.get(i).split("/");
			smallCategory.setCategoryName(categoryElement[2]);
			smallCategory.setNameAll(kidsList.get(i));
			smallCategory.setParentId(categoryMapper.returnMiddleCategoryId(4, categoryElement[1]));
			smallArray.add(smallCategory);
		}
		// 重複を削除
		smallElementList = smallArray.stream().distinct().collect(Collectors.toList());
		categoryMapper.insert(smallElementList);
		// リスト使いまわすために一旦削除。
		smallArray.clear();
		smallElementList.clear();

		// ⑤
		// 重複あり状態でインサート用リストの作成
		for (int i = 0; i < electronicsList.size(); i++) {
			Category smallCategory = new Category();
			String[] categoryElement = electronicsList.get(i).split("/");
			smallCategory.setCategoryName(categoryElement[2]);
			smallCategory.setNameAll(electronicsList.get(i));
			smallCategory.setParentId(categoryMapper.returnMiddleCategoryId(5, categoryElement[1]));
			smallArray.add(smallCategory);
		}
		// 重複を削除
		smallElementList = smallArray.stream().distinct().collect(Collectors.toList());
		categoryMapper.insert(smallElementList);
		// リスト使いまわすために一旦削除。
		smallArray.clear();
		smallElementList.clear();

		// ⑥
		// 重複あり状態でインサート用リストの作成
		for (int i = 0; i < menList.size(); i++) {
			Category smallCategory = new Category();
			String[] categoryElement = menList.get(i).split("/");
			smallCategory.setCategoryName(categoryElement[2]);
			smallCategory.setNameAll(menList.get(i));
			smallCategory.setParentId(categoryMapper.returnMiddleCategoryId(6, categoryElement[1]));
			smallArray.add(smallCategory);
		}
		// 重複を削除
		smallElementList = smallArray.stream().distinct().collect(Collectors.toList());
		categoryMapper.insert(smallElementList);
		// リスト使いまわすために一旦削除。
		smallArray.clear();
		smallElementList.clear();

		// ⑦
		// 重複あり状態でインサート用リストの作成
		for (int i = 0; i < vintageList.size(); i++) {
			Category smallCategory = new Category();
			String[] categoryElement = vintageList.get(i).split("/");
			smallCategory.setCategoryName(categoryElement[2]);
			smallCategory.setNameAll(vintageList.get(i));
			smallCategory.setParentId(categoryMapper.returnMiddleCategoryId(7, categoryElement[1]));
			smallArray.add(smallCategory);
		}
		// 重複を削除
		smallElementList = smallArray.stream().distinct().collect(Collectors.toList());
		categoryMapper.insert(smallElementList);
		// リスト使いまわすために一旦削除。
		smallArray.clear();
		smallElementList.clear();

		// ⑧
		// 重複あり状態でインサート用リストの作成
		for (int i = 0; i < homeList.size(); i++) {
			Category smallCategory = new Category();
			String[] categoryElement = homeList.get(i).split("/");
			smallCategory.setCategoryName(categoryElement[2]);
			smallCategory.setNameAll(homeList.get(i));
			smallCategory.setParentId(categoryMapper.returnMiddleCategoryId(8, categoryElement[1]));
			smallArray.add(smallCategory);
		}
		// 重複を削除
		smallElementList = smallArray.stream().distinct().collect(Collectors.toList());
		categoryMapper.insert(smallElementList);
		// リスト使いまわすために一旦削除。
		smallArray.clear();
		smallElementList.clear();

		// ⑨
		// 重複あり状態でインサート用リストの作成
		for (int i = 0; i < otherList.size(); i++) {
			Category smallCategory = new Category();
			String[] categoryElement = otherList.get(i).split("/");
			smallCategory.setCategoryName(categoryElement[2]);
			smallCategory.setNameAll(otherList.get(i));
			smallCategory.setParentId(categoryMapper.returnMiddleCategoryId(9, categoryElement[1]));
			smallArray.add(smallCategory);
		}
		// 重複を削除
		smallElementList = smallArray.stream().distinct().collect(Collectors.toList());
		categoryMapper.insert(smallElementList);
		// リスト使いまわすために一旦削除。
		smallArray.clear();
		smallElementList.clear();

		// ⑩ ここだけparent_idがうまく挿入できないので、parent_idのみSQL文直書きで挿入。
		// 重複あり状態でインサート用リストの作成
		for (int i = 0; i < handmadeList.size(); i++) {
			Category smallCategory = new Category();
			String[] categoryElement = handmadeList.get(i).split("/");
			smallCategory.setCategoryName(categoryElement[2]);
			smallCategory.setNameAll(handmadeList.get(i));
			smallCategory.setParentId(categoryMapper.returnMiddleCategoryId(10, categoryElement[1]));
			smallArray.add(smallCategory);
		}
		// 重複を削除
		smallElementList = smallArray.stream().distinct().collect(Collectors.toList());
		categoryMapper.insert(smallElementList);
		// リスト使いまわすために一旦削除。
		smallArray.clear();
		smallElementList.clear();

	}
}
