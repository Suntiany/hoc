$(function(){
    let UserInfoUrl = '/hoc/user/getuserbysession';
    let editUserUrl = '/hoc/user/modifyuser';
    getUserInfo();


    function getUserInfo(){
        $.getJSON(UserInfoUrl,function (data) {
            if(data.success){
                var user = data.user;
                var userAuth = data.userAuth;
                $('#user-name').val(user.userName);
                $('#gender').val(user.gender);
                $('#user-email').val(user.email);
                $('#user-addr').val(user.addr);
                $('#user-phone').val(user.phone);
                $('#account').val(userAuth.username);
                $('#password').val(userAuth.password);
                var tempAreaHtml = '';
                data.areaList.map(function(item,index){
                    tempAreaHtml+='<option data-id="' +item.areaId +'">'
                        +item.areaName + '</option>'
                });
                $('#area').html(tempAreaHtml);
                $("#area option[data-id='" + user.area.areaId + "']").attr(
                    "selected", "selected");
            }
        });
    }

    $('#submit').click(function () {
        var user = {};
        var userAuth = {};
        user.userName = $('#user-name').val();
        user.gender = $('#gender').val();
        user.phone = $('#user-phone').val();
        user.email = $('#user-email').val();
        user.addr = $('#user-addr').val();
        userAuth.username = $('#account').val();
        userAuth.password = $('#password').val();
        user.area = {
            areaId: $('#area').find('option').not(function () {
                return !this.selected;
            }).data('id')
        }
        var formData = new FormData();
        formData.append('userStr',JSON.stringify(user));
        formData.append('userAuthStr',JSON.stringify(userAuth));
        var verifyCodeActual = $('#j_captcha').val();
        if(!verifyCodeActual){
            $.toast('请输入验证码');
            return;
        }
        formData.append('verifyCodeActual',verifyCodeActual);
        $.ajax({
            url:editUserUrl,
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