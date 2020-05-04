$(function() {
	// 获取此医院下的医生列表的URL
	var listUrl = '/hoc/hospitaladmin/getdoctorlistbyhospital?pageIndex=1&pageSize=999';
	// 商品下架URL
	var statusUrl = '/hoc/hospitaladmin/modifydoctor';
	getList();
	/**
	 * 获取此医院下的医生列表
	 * 
	 * @returns
	 */
	function getList() {
		// 从后台获取此医院的医生列表
		$.getJSON(listUrl, function(data) {
			if (data.success) {
				var doctorList = data.doctorList;
				console.log(doctorList);
				var tempHtml = '';
				//console.log(data.doctorList);
				// 遍历每条商品信息，拼接成一行显示，列信息包括：
				// 商品名称，优先级，上架\下架(含doctorId)，编辑按钮(含doctorId)
				// 预览(含doctorId)
				doctorList.map(function(item, index) {
					var textOp = "在值";
					var contraryStatus = 0;
					if (item.enableStatus == 0) {
						// 若状态值为0，表明是已下架的商品，操作变为上架(即点击上架按钮上架相关商品)
						textOp = "下班";
						contraryStatus = 1;
					} else {
						contraryStatus = 0;
					}
					// 拼接每件商品的行信息
					tempHtml += '' + '<div class="row row-doctor">'
							+ '<div class="col-33">'
							+ item.doctorName
							+ '</div>'
							+ '<div class="col-20">'
							+ item.priority
							+ '</div>'
							+ '<div class="col-40">'
							+ '<a href="#" class="edit" data-id="'
							+ item.doctorId
							+ '" data-status="'
							+ item.enableStatus
							+ '">编辑</a>'
							+ '<a href="#" class="status" data-id="'
							+ item.doctorId
							+ '" data-status="'
							+ contraryStatus
							+ '">'
							+ textOp
							+ '</a>'

							+ '</div>'
							+ '</div>';
				});
				// 将拼接好的信息赋值进html控件中
				$('.doctor-wrap').html(tempHtml);
			}
		});
	}
	// 将class为doctor-wrap里面的a标签绑定上点击的事件
	$('.doctor-wrap')
			.on(
					'click',
					'a',
					function(e) {
						var target = $(e.currentTarget);
						if (target.hasClass('edit')) {
							// 如果有class edit则点击就进入店铺信息编辑页面，并带有doctorId参数
							window.location.href = '/hoc/hospitaladmin/doctoroperation?doctorId='
									+ e.currentTarget.dataset.id;
						} else if (target.hasClass('status')) {
							// 如果有class status则调用后台功能上/下架相关商品，并带有doctorId参数
							changeItemStatus(e.currentTarget.dataset.id,
									e.currentTarget.dataset.status);
						} else if (target.hasClass('preview')) {
							// 如果有class preview则去前台展示系统该商品详情页预览商品情况
							window.location.href = '/hoc/frontend/productdetail?productId='
									+ e.currentTarget.dataset.id;
						}
					});
	function changeItemStatus(id, enableStatus) {
		// 定义doctor json对象并添加doctorId以及状态(上架/下架)
		var doctor = {};
		doctor.doctorId = id;
		doctor.enableStatus = enableStatus;
		$.confirm('确定么?', function() {
			// 上下架相关商品
			$.ajax({
				url : statusUrl,
				type : 'POST',
				data : {
					doctorStr : JSON.stringify(doctor),
					statusChange : true
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.toast('操作成功！');
						getList();
					} else {
						$.toast('操作失败！');
					}
				}
			});
		});
	}
});