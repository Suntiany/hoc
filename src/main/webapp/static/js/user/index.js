$(function() {
	//定义访问后台，获取头条列表以及一级类别列表的URL
	var url = '/hoc/user/signpageinfo';
	//访问后台，获取头条列表以及一级类别列表
	$.getJSON(url, function(data) {
		if (data.success) {
			//获取后台传递过来的头条列表
			var hospitalListArea = data.hospitalListArea;
			var hospitalListAll = data.hospitalListAll;
			var headLineList = data.headLineList;
			var swiperHtml = '';
			//遍历头条列表，并拼接出轮播图组
			headLineList.map(function(item, index) {
				swiperHtml += '' + '<div class="swiper-slide img-wrap">'
						+ '<a href="' + item.lineLink
						+ '" external><img class="banner-img" src="' + item.lineImg
						+ '" alt="' + item.lineName + '"></a>' + '</div>';
			});
			//将轮播图组赋值给前端HTML控件
			$('.swiper-wrapper').html(swiperHtml);
			//设定轮播图轮换时间为3秒
			$(".swiper-container").swiper({
				autoplay : 3000,
				//用户对轮播图进行操作时，是否自动停止autoplay
				autoplayDisableOnInteraction : false
			});
				//将医院信息填充
			handleListNear(hospitalListArea);
			handleListAll(hospitalListAll);
		}
	});

	function handleListNear(data){
		var html='';
		data.map(function(item,index) {
			html+='<div class="row row-shop"><div class="col-40">'
				+ item.hospitalName + '</div><div class="col-40">'
				+ "正常营业"
				+ '</div><div class="col-20" id="info">'
				+ goHospital(item.hospitalId) + '</div></div>';
		});
		$('.row-shop').html(html);
	}

	function handleListAll(data){
		var html='';
		data.map(function(item,index) {
			html+='<div class="row row-shop"><div class="col-40">'
				+ item.hospitalName + '</div><div class="col-40">'
				+ "正常营业"
				+ '</div><div class="col-20" id="info">'
				+ goHospital(item.hospitalId) + '</div></div>';
		});
		$('.row-shopall').html(html);
	}

	function goHospital(id) {
		return '<a href="/hoc/user/hospitalinfo?hospitalId=' + id
			+ '" external>进入</a>';
	}

	//若点击"我的"，则显示侧栏
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});

	//若点击“资讯”，则跳转到资讯页面
	$('#news').click(function() {
		var newslistUrl = "/hoc/user/newslist";
		window.location.href = newslistUrl;
	})

	$('.row').on('click', '.shop-classify', function(e) {
		var shopCategoryId = e.currentTarget.dataset.category;
		var newUrl = '/o2o/frontend/shoplist?parentId=' + shopCategoryId;
		window.location.href = newUrl;
	});

});
