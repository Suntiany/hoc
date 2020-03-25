$(function() {
	initializePage();
	listHospitalManagementInfo();
});
function listHospitalManagementInfo() {
	$.ajax({
		url : "listhospital",
		type : "get",
		dataType : 'json',
		success : function(data) {
			console.log(data);
			$('#areaManagementTable').datagrid('loadData', data);
		}
	});
}
function initializePage() {
	// 加载表格数据
	ajaxTable();

	// 初始化弹出层
	setDialog_add();
	closeDialog_add();
	setDialog_edit();
	closeDialog_edit();
}
/** --------------table------------------* */
/**
 * 加载表格数据
 */
function ajaxTable() {
	// 加载表格
	$('#areaManagementTable')
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
					$('#areaManagementTable').datagrid('unselectRow',
						rowIndex);
				},
				onLoadSuccess : function() {
					var value = $('#areaManagementTable').datagrid(
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
	var ownerName = row.ownerName;
	var hospitalId = row.hospitalId;
	var hospitalName = row.hospitalName;
	var enableStatus = row.enableStatus;
	var priority = row.priority;
	var phone = row.phone;
	var params = hospitalId + ",'" + hospitalName + "'," + enableStatus;
	var edit = '<a href="javascript:openDialog_edit(' + params + ')">审核</a>';
	return edit;
};

/** --------------添加操作弹出框------------------* */
// 设置弹出框的属性
function setDialog_add() {
	$('#areaManagementAdd').dialog({
		title : '区域添加',
		modal : true, // 模式窗口：窗口背景不可操作
		collapsible : true, // 可折叠，点击窗口右上角折叠图标将内容折叠起来
		resizable : true, // 可拖动边框大小
		onClose : function() { // 继承自panel的关闭事件
			areaManagementAddReset();
		}
	});
}
// 打开对话框
function openDialog_add() {
	$('#areaManagementAdd').dialog('open');
}
// 关闭对话框
function closeDialog_add() {
	$('#areaManagementAdd').dialog('close');
}



/** --------------编辑操作弹出框------------------* */
// 设置弹出框的属性
function setDialog_edit() {
	$('#areaManagementEdit').dialog({
		title : '区域编辑',
		modal : true, // 模式窗口：窗口背景不可操作
		collapsible : true, // 可折叠，点击窗口右上角折叠图标将内容折叠起来
		resizable : true
		// 可拖动边框大小
	});
}
// 打开对话框
function openDialog_edit(hospitalId, hospitalName, enableStatus) {
	areaManagementEditReset(hospitalId, hospitalName, enableStatus);
	$('#areaManagementEdit').dialog('open');
}
// 关闭对话框
function closeDialog_edit() {
	$('#areaManagementEdit').dialog('close');
}




// 根据医院id查询用户的信息
function areaManagementEditReset(hospitalId, hospitalName, enableStatus) {
	$("#areaManagementEdit_message").html("");
	$("#areaManagementEdit_hospitalId").val(hospitalId);
	$("#areaManagementEdit_hospitalName").val(hospitalName);
	$("#areaManagementEdit_enableStatus").val(enableStatus);
}

// 执行用户编辑操作
function areaManagementEdit() {
	var validateResult = true;
	// easyui 表单验证
	$('#table_areaManagementEdit input').each(function() {
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
	var hospital = {};
	hospital.hospitalId = $("#areaManagementEdit_hospitalId").val();
	hospital.hospitalName = encodeURIComponent($("#areaManagementEdit_hospitalName").val());
	hospital.enableStatus = $("#areaManagementEdit_enableStatus").val();
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			hospitalStr : JSON.stringify(hospital)
		},
		url : 'modifyhospital',// 请求的action路径
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function() {
			var messgage = "修改成功!";
			listHospitalManagementInfo();
			$("#areaManagementEdit_message").html(messgage);
		}
	});
}


