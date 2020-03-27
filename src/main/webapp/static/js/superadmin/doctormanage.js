$(function() {
	initializePage();
	listDoctorManagementInfo();
});
function listDoctorManagementInfo() {
	$.ajax({
		url : "listdoctor",
		type : "get",
		dataType : 'json',
		success : function(data) {
			console.log(data);
			$('#doctorManagementTable').datagrid('loadData', data);
		}
	});
}
function initializePage() {
	// 加载表格数据
	ajaxTable();

	setDialog_edit();
	closeDialog_edit();
}
/** --------------table------------------* */
/**
 * 加载表格数据
 */
function ajaxTable() {
	// 加载表格
	$('#doctorManagementTable')
		.datagrid(
			{
				loadMsg : '数据加载中,请稍后...',
				// pageList:[10,30,50,100], //设置每页显示多少条
				onLoadError : function() {
					alert('数据加载失败!');
				},
				queryParams : {// 查询条件
				},
				onClickRow : function(rowIndex, rowData) {
					// 取消选择某行后高亮
					$('#doctorManagementTable').datagrid('unselectRow',
						rowIndex);
				},
				onLoadSuccess : function() {
					var value = $('#doctorManagementTable').datagrid(
						'getData')['errorMsg'];
					if (value != null) {
						alert("错误消息:" + value);
					}
				}
			}).datagrid('acceptChanges');
}
/**
 * 设置操作列的信息 参数说明 value 这个可以不管，但是要使用后面 row 和index 这个参数是必须的 row 当前行的数据 index
 * 当前行的索引 从0 开始
 */
function optFormater(value, row, index) {
	// if(row.area!=null){
	// 	return row.area.areaName;
	// }
	// if(row.doctor!=null){
	// 	return row.doctor.doctorName;
	// }
	var doctorId = row.doctorId;
	//console.log(userId);
	var doctorName = row.doctorName;
	var enableStatus = row.enableStatus;
	//var phone = row.phone;
	var params = doctorId + ",'" + doctorName + "'," + enableStatus;
	var edit = '<a href="javascript:openDialog_edit(' + params + ')">编辑</a>';
	return edit;
};

function deal_hospital(value,row,rowIndex) {
	if(row.hospital!=null){
		return row.hospital.hospitalName;
	}
};

function deal_category(value,row,rowIndex) {
	if(row.doctorCategory!=null){
		return row.doctorCategory.doctorCategoryName;
	}
}

function deal_photo(value, row, index) {
	var lineImg = row.imgAddr;
	return '<img src="' + lineImg + '" width="100px" height="60px">';
}

/** --------------添加操作弹出框------------------* */
// 设置弹出框的属性
function setDialog_add() {
	$('#doctorManagementAdd').dialog({
		title : '区域添加',
		modal : true, // 模式窗口：窗口背景不可操作
		collapsible : true, // 可折叠，点击窗口右上角折叠图标将内容折叠起来
		resizable : true, // 可拖动边框大小
		onClose : function() { // 继承自panel的关闭事件
			doctorManagementAddReset();
		}
	});
}
// 打开对话框
function openDialog_add() {
	$('#doctorManagementAdd').dialog('open');
}
// 关闭对话框
function closeDialog_add() {
	$('#doctorManagementAdd').dialog('close');
}



/** --------------编辑操作弹出框------------------* */
// 设置弹出框的属性
function setDialog_edit() {
	$('#doctorManagementEdit').dialog({
		title : '区域编辑',
		modal : true, // 模式窗口：窗口背景不可操作
		collapsible : true, // 可折叠，点击窗口右上角折叠图标将内容折叠起来
		resizable : true
		// 可拖动边框大小
	});
}
// 打开对话框
function openDialog_edit(doctorId, doctorName,enableStatus) {
	doctorManagementEditReset(doctorId, doctorName,enableStatus);
	$('#doctorManagementEdit').dialog('open');
}

// 根据医生id查询用户的信息
function doctorManagementEditReset(doctorId,doctorName,enableStatus) {
	$("#doctorManagementEdit_message").html("");
	$("#doctorManagementEdit_doctorId").val(doctorId);
	$("#doctorManagementEdit_doctorName").val(doctorName);
	$("#doctorManagementEdit_enableStatus").val(enableStatus);
}
// 关闭对话框
function closeDialog_edit() {
	$('#doctorManagementEdit').dialog('close');
}




// 执行用户编辑操作
function doctorManagementEdit() {
	var validateResult = true;
	// easyui 表单验证
	$('#table_doctorManagementEdit input').each(function() {
		if ($(this).attr('required') || $(this).attr('validType')) {
			if (!$(this).validatebox('isValid')) {
				// 如果验证不通过，则返回false
				validateResult = false;
				return;
			}
		}
	});
	if (validateResult == false) {
		return;
	}
	var doctor = {};
	doctor.doctorId = $("#doctorManagementEdit_doctorId").val();
	doctor.doctorName = encodeURIComponent($("#doctorManagementEdit_doctorName").val());
	doctor.enableStatus = $("#doctorManagementEdit_enableStatus").val();
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			doctorStr : JSON.stringify(doctor)
		},
		url : 'modifydoctor',// 请求的action路径
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function() {
			var messgage = "修改成功!";
			listDoctorManagementInfo();
			$("#doctorManagementEdit_message").html(messgage);
		}
	});
}


