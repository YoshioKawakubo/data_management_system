'use strict'
$(function() {
	$("#parent_category").on("change", function() {
		var parent = document.getElementById('parent_category')

		$.ajax({
			//javaにparent_categoryのvalueを送って中カテゴリの名前一覧を取得してきたい
			url: 'http://localhost:8080/category/childGet',
			dataType: 'json',
			data: {
				parentCategory: parent.value
			},
			async: true
		}).done(function(data) {
			//以下一文の意味がいまいち不明。
			console.log(JSON.stringify(data));
			const selectChildCategory = document.getElementById('child_category');
			/*一旦元のoptionをリセット*/
			while (selectChildCategory.firstChild) {
				selectChildCategory.removeChild(selectChildCategory.firstChild);
			}
			//javaから受け取ったmapを中カテゴリのオプションとして表示するプログラムの作成 (foreachでいける？)
			data.childCategory.forEach(function(child) {
				const option = document.createElement('option');
				option.value = child;
				option.textContent = child;
				selectChildCategory.appendChild(option);
			});
			//child_categoryが変化したら、grandChildの選択肢が動的に変化する関数
			$("#child_category").on("change", function() {
				var child = document.getElementById('child_category')

				$.ajax({
					url: 'http://localhost:8080/category/grandGet',
					dataType: 'json',
					data: {
						parentCategory: parent.value,
						childCategory: child.value
					},
					async: true
				}).done(function(data) {
					console.log(JSON.stringify(data));
					const selectGrandChildCategory = document.getElementById('grand_child');
					//parentのvalue or childのバリューが変わった時に選択肢をリセットする、の書き方の方が良い？
					while (selectGrandChildCategory.firstChild) {
						selectGrandChildCategory.removeChild(selectGrandChildCategory.firstChild);
					}
					data.grandChild.forEach(function(grand) {
						const option = document.createElement('option');
						option.value = grand;
						option.textContent = grand;
						selectGrandChildCategory.appendChild(option);
					});
				})
			});
		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
		console.log('XMLHttpRequest:' + XMLHttpRequest.status);
		console.log('textStatus:' + textStatus);
		console.log('errorThrown:' + errorThrown.message);
		});
	});
});