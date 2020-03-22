$(function() {
    var consultId = getQueryString('consultId');
    var listUserUrl = '/hoc/doctor/getconsultbyconsultid?consultId='+consultId;
    var dealConsultUrl = '/hoc/user/dealconsultbyconsultid?consultId='+consultId;
    getList();
    function getList() {
        $.getJSON(listUserUrl,function (data) {
            if(data.success){
                var consultation = data.consultation;
                console.log(consultation);
                $('#consult-info').val(consultation.symptom);
                $('#comment').val(consultation.comment);
                $('#create-time').val(consultation.createTime);
                $('#status').val(consultation.status);
            }
        })
    }

    $('#submit').click(function () {
        var comment = $('#comment').val();
        var formData = new FormData();
        formData.append("comment",comment);
        var verifyCodeActual = $('#j_captcha').val();
        if(!verifyCodeActual){
            $.toast('请输入验证码');
            return;
        }
        formData.append('verifyCodeActual',verifyCodeActual);
        $.ajax({
            url:dealConsultUrl,
            type:'POST',
            data:formData,
            contentType:false,
            processData:false,
            cache:false,
            success:function (data) {
                if(data.success){
                    window.location.reload();
                    $.toast('提交成功');
                }else{
                    $.toast('提交失败！');
                }
            }
        })
    })


    $('#logback').click(function () {
        history.go(-1);
    })
})