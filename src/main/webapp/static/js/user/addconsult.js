$(function () {
    var addConsultUrl = "/hoc/user/addconsultbysession"
    $('#submit').click(function () {
        var consultInfo = $('#consult-info').val();
        var thumbnail = $('#small-img')[0].files[0];
        var formData = new FormData();
        formData.append("consultInfo",consultInfo);
        formData.append('thumbnail',thumbnail);
        var verifyCodeActual = $('#j_captcha').val();
        if(!verifyCodeActual){
            $.toast('请输入验证码');
            return;
        }
        formData.append('verifyCodeActual',verifyCodeActual);
        $.ajax({
            url:addConsultUrl,
            type:'POST',
            data:formData,
            contentType:false,
            processData:false,
            cache:false,
            success:function (data) {
                if(data.success){
                    $.toast('提交成功，可以在问诊记录等待医生解答');
                }else{
                    $.toast('提交失败！');
                }
            }
        })
    })
})