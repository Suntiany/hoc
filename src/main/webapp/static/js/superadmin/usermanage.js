$(function() {
	initializePage();
	listHospitalManagementInfo();
});
function listHospitalManagementInfo() {
	$.ajax({
		url : "listuser",
		type : "get",
		dataType : 'json',
		success : function(data) {
			console.log(data);
			$('#userManagementTable').datagrid('loadData', data);
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
	$('#userManagementTable')
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
					$('#userManagementTable').datagrid('unselectRow',
						rowIndex);
				},
				onLoadSuccess : function() {
					var value = $('#userManagementTable').datagrid(
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
	var userId = row.userId;
	//console.log(userId);
	var userName = row.userName;
	var enableStatus = row.enableStatus;
	//var phone = row.phone;
	var params = userId + ",'" + userName + "'," + enableStatus;
	var edit = '<a href="javascript:openDialog_edit(' + params + ')">编辑</a>';
	return edit;
};

function deal_area(value,row,rowIndex) {
	if(row.area!=null){
		return row.area.areaName;
	}
};

function deal_doctor(value,row,rowIndex) {
	if(row.doctor!=null){
		return row.doctor.doctorName;
	}
}

/** --------------添加操作弹出框------------------* */
// 设置弹出框的属性
function setDialog_add() {
	$('#userManagementAdd').dialog({
		title : '区域添加',
		modal : true, // 模式窗口：窗口背景不可操作
		collapsible : true, // 可折叠，点击窗口右上角折叠图标将内容折叠起来
		resizable : true, // 可拖动边框大小
		onClose : function() { // 继承自panel的关闭事件
			userManagementAddReset();
		}
	});
}
// 打开对话框
function openDialog_add() {
	$('#userManagementAdd').dialog('open');
}
// 关闭对话框
function closeDialog_add() {
	$('#userManagementAdd').dialog('close');
}



/** --------------编辑操作弹出框------------------* */
// 设置弹出框的属性
function setDialog_edit() {
	$('#userManagementEdit').dialog({
		title : '区域编辑',
		modal : true, // 模式窗口：窗口背景不可操作
		collapsible : true, // 可折叠，点击窗口右上角折叠图标将内容折叠起来
		resizable : true
		// 可拖动边框大小
	});
}
// 打开对话框
function openDialog_edit(userId, userName, enableStatus) {
	userManagementEditReset(userId, userName, enableStatus);
	$('#userManagementEdit').dialog('open');
}
// 关闭对话框
function closeDialog_edit() {
	$('#userManagementEdit').dialog('close');
}

// 根据用户id查询用户的信息
function userManagementEditReset(userId, userName, enableStatus) {
	$("#userManagementEdit_message").html("");
	$("#userManagementEdit_userId").val(userId);
	$("#userManagementEdit_userName").val(userName);
	$("#userManagementEdit_enableStatus").val(enableStatus);
}


// 执行用户编辑操作
function userManagementEdit() {
	var validateResult = true;
	// easyui 表单验证
	$('#table_userManagementEdit input').each(function() {
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
	var user = {};
	user.userId = $("#userManagementEdit_userId").val();
	user.userName = encodeURIComponent($("#userManagementEdit_userName").val());
	user.enableStatus = $("#userManagementEdit_enableStatus").val();
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			userStr : JSON.stringify(user)
		},
		url : 'modifyuser',// 请求的action路径
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function() {
			var messgage = "修改成功!";
			listHospitalManagementInfo();
			$("#userManagementEdit_message").html(messgage);
		}
	});
}


