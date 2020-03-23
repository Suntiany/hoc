$(function(){
    let registerDoctorAccountUrl = '/hoc/doctor/registeraccount';


    $('#submit').click(function () {
        var doctorAuth = {};
        doctorAuth.username = $('#user-name').val();
        doctorAuth.password = $('#password').val();
        var formData = new FormData();
        formData.append('doctorAuthStr',JSON.stringify(doctorAuth));
        var verifyCodeActual = $('#j_captcha').val();
        if(!verifyCodeActual){
            $.toast('请输入验证码');
            return;
        }
        formData.append('verifyCodeActual',verifyCodeActual);
        $.ajax({
            url:registerDoctorAccountUrl,
            type:'POST',
            data:formData,
            contentType:false,
            processData:false,
            cache:false,
            success:function (data) {
                if(data.success){
                    $.toast('提交成功！');
                }else{
                    $.toast("医生姓名未找到");
                }
                $('#captcha_img').click();
            }
        })
    })
    $('#logback').click(function () {
        history.go(-1);
    })
})