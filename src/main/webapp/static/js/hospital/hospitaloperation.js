$(function(){
    let hospitalId = getQueryString('hospitalId');
    let isEdit = hospitalId?true:false;
    let initUrl = '/hoc/hospitaladmin/gethospitalinitinfo';
    let registerHospitalUrl = '/hoc/hospitaladmin/registerhospital';
    let hospitalInfoUrl = '/hoc/hospitaladmin/gethospitalbyid?hospitalId='+hospitalId;
    let edithospitalUrl = '/hoc/hospitaladmin/modifyhospital';
    if(!isEdit){
        getHospitalInitInfo();
    }else{
        getHospitalInfo(hospitalId);
    }

    function getHospitalInfo(hospitalId){
        $.getJSON(hospitalInfoUrl,function (data) {
            if(data.success){
                var hospital = data.hospital;
                $('#hospital-name').val(hospital.hospitalName);
                $('#hospital-addr').val(hospital.hospitalAddr);
                $('#owner-name').val(hospital.ownerName);
                $('#hospital-phone').val(hospital.phone);
                $('#hospital-desc').val(hospital.hospitalDesc);
                var tempAreaHtml = '';
                data.areaList.map(function(item,index){
                    tempAreaHtml+='<option data-id="' +item.areaId +'">'
                        +item.areaName + '</option>'
                });
                $('#area').html(tempAreaHtml);
                $("#area option[data-id='" + hospital.area.areaId + "']").attr(
                    "selected", "selected");
            }
        });
    }
    function getHospitalInitInfo(){
        $.getJSON(initUrl,function (data) {
            if(data.success){
                var tempAreaHtml = '';
                data.areaList.map(function(item,index){
                    tempAreaHtml+='<option data-id="' +item.areaId +'">'
                        +item.areaName + '</option>';
                });
                $('#area').html(tempAreaHtml);
            }
        });
    }
    $('#submit').click(function () {
         var hospital = {};
         if(isEdit){
             hospital.hospitalId = hospitalId;
         }
        hospital.hospitalName = $('#hospital-name').val();
        hospital.hospitalAddr = $('#hospital-addr').val();
        hospital.phone = $('#hospital-phone').val();
        hospital.hospitalDesc = $('#hospital-desc').val();
        hospital.ownerName = $('#owner-name').val();
        hospital.area = {
             areaId: $('#area').find('option').not(function () {
                    return !this.selected;
             }).data('id')
         }
         var hospitalImg = $('#hospital-img')[0].files[0];
         var formData = new FormData();
         formData.append('hospitalImg',hospitalImg);
         formData.append('hospitalStr',JSON.stringify(hospital));
         var verifyCodeActual = $('#j_captcha').val();
         if(!verifyCodeActual){
             $.toast('请输入验证码');
             return;
         }
         formData.append('verifyCodeActual',verifyCodeActual);
         $.ajax({
             url:(isEdit?edithospitalUrl:registerHospitalUrl),
             type:'POST',
             data:formData,
             contentType:false,
             processData:false,
             cache:false,
             success:function (data) {
                 if(data.success){
                     $.toast('提交成功！');
                 }else{
                     $.toast('提交失败！'+data.errMsg);
                 }
                 $('#captcha_img').click();
             }
         })
    })
    $('#logback').click(function () {
        history.go(-1);
    })
})