$(function() {
	// 从URL里获取doctorId参数的值
	var doctorId = getQueryString('doctorId');
	// 通过doctorId获取商品信息的URL
	var infoUrl = '/hoc/hospitaladmin/getdoctorbyid?doctorId=' + doctorId;
	// 获取当前店铺设定的商品类别列表的URL
	var categoryUrl = '/hoc/hospitaladmin/getdoctorcategorylist';
	// 更新商品信息的URL
	var doctorPostUrl = '/hoc/hospitaladmin/modifydoctor';
	// 由于商品添加和编辑使用的是同一个页面，
	// 该标识符用来标明本次是添加还是编辑操作
	var isEdit = false;
	if (doctorId) {
		// 若有doctorId则为编辑操作
		getInfo(doctorId);
		isEdit = true;
	} else {
		getCategory();
		doctorPostUrl = '/hoc/hospitaladmin/adddoctor';
	}

	// 获取需要编辑的商品的商品信息，并赋值给表单
	function getInfo(id) {
		$
				.getJSON(
						infoUrl,
						function(data) {
							if (data.success) {
								// 从返回的JSON当中获取doctor对象的信息，并赋值给表单
								var doctor = data.doctor;
								$('#doctor-name').val(doctor.doctorName);
								$('#doctor-desc').val(doctor.doctorDesc);
								$('#priority').val(doctor.priority);
								// $('#point').val(doctor.point);
								var optionHtml = '';
								var optionArr = data.doctorCategoryList;
								var optionSelected = doctor.doctorCategory.doctorCategoryId;
								// 生成前端的HTML商品类别列表，并默认选择编辑前的商品类别
								optionArr
										.map(function(item, index) {
											var isSelect = optionSelected === item.doctorCategoryId ? 'selected'
													: '';
											optionHtml += '<option data-value="'
													+ item.doctorCategoryId
													+ '"'
													+ isSelect
													+ '>'
													+ item.doctorCategoryName
													+ '</option>';
										});
								$('#category').html(optionHtml);
							}
						});
	}

	// 为商品添加操作提供该店铺下的所有商品类别列表
	function getCategory() {
		$.getJSON(categoryUrl, function(data) {
			if (data.success) {
				var doctorCategoryList = data.data;
				var optionHtml = '';
				doctorCategoryList.map(function(item, index) {
					optionHtml += '<option data-value="'
							+ item.doctorCategoryId + '">'
							+ item.doctorCategoryName + '</option>';
				});
				$('#category').html(optionHtml);
			}
		});
	}

	// 针对商品详情图控件组，若该控件组的最后一个元素发生变化（即上传了图片），
	// 且控件总数未达到6个，则生成新的一个文件上传控件
	$('.detail-img-div').on('change', '.detail-img:last-child', function() {
		if ($('.detail-img').length < 6) {
			$('#detail-img').append('<input type="file" class="detail-img">');
		}
	});

	// 提交按钮的事件响应，分别对商品添加和编辑操作做不同响应
	$('#submit').click(
			function() {
				// 创建商品json对象，并从表单里面获取对应的属性值
				var doctor = {};
				doctor.doctorName = $('#doctor-name').val();
				doctor.doctorDesc = $('#doctor-desc').val();
				doctor.priority = $('#priority').val();
				// 获取选定的商品类别值
				doctor.doctorCategory = {
					doctorCategoryId : $('#category').find('option').not(
							function() {
								return !this.selected;
							}).data('value')
				};
				doctor.doctorId = doctorId;

				// 获取缩略图文件流
				var thumbnail = $('#small-img')[0].files[0];
				// 生成表单对象，用于接收参数并传递给后台
				var formData = new FormData();
				formData.append('thumbnail', thumbnail);
				// 遍历商品详情图控件，获取里面的文件流
				$('.detail-img').map(
						function(index, item) {
							// 判断该控件是否已选择了文件
							if ($('.detail-img')[index].files.length > 0) {
								// 将第i个文件流赋值给key为DoctorImgi的表单键值对里
								formData.append('doctorImg' + index,
										$('.detail-img')[index].files[0]);
							}
						});
				// 将doctor json对象转成字符流保存至表单对象key为doctorStr的的键值对里
				formData.append('doctorStr', JSON.stringify(doctor));
				// 获取表单里输入的验证码
				var verifyCodeActual = $('#j_captcha').val();
				if (!verifyCodeActual) {
					$.toast('请输入验证码！');
					return;
				}
				formData.append("verifyCodeActual", verifyCodeActual);
				// 将数据提交至后台处理相关操作
				$.ajax({
					url : doctorPostUrl,
					type : 'POST',
					data : formData,
					contentType : false,
					processData : false,
					cache : false,
					success : function(data) {
						if (data.success) {
							$.toast('提交成功！');
							$('#captcha_img').click();
						} else {
							$.toast('提交失败！');
							$('#captcha_img').click();
						}
					}
				});
			});

});