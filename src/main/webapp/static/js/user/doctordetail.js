$(function() {
	// 从地址栏的URL里获取productId
	var getUserUrl = "/hoc/user/getuserbysession";
	$.getJSON(getUserUrl,function (data) {
		if(data.success){
			//var doctorId = doctor.doctorId;
			//console.log(doctorId);
			//console.log(data.user.doctor.doctorId);
			var doctorId = data.user.doctor.doctorId;
			var doctorUrl = '/hoc/hospitaladmin/getdoctorbyid?doctorId='+doctorId;
			//获取后台医生的信息
			$.getJSON(doctorUrl,function (data) {
				if(data.success) {
					//获取医生信息
					var doctor = data.doctor;
					var doctorCategory = data.doctor.doctorCategory;
					console.log(doctor);
					//给医生信息相关HTML控件赋值

					//医生缩略图
					$('#doctor-img').attr('src',doctor.imgAddr);
					//医生姓名
					$('#doctor-name').text(doctor.doctorName);
					$('#doctor-desc').text("医生简介："+doctor.doctorDesc);
					$('#doctor-time').text("上任时间："+doctor.createTime);
					$('#doctor-category').text("所属科室："+doctorCategory.doctorCategoryName);
					var imgListHtml='';
					doctor.doctorImgList.map(function(item, index) {
						imgListHtml += '<div> <img src="' + item.imgAddr
							+ '" width="50%" /></div>';
					});
					$('#imgList').html(imgListHtml);
				}
			})
		}
	})

});
