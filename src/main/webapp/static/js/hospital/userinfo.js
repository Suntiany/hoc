$(function(){
    let userId = getQueryString('userId');
    let editUserUrl = '/hoc/user/adddoctor';
    let UserInfoUrl = '/hoc/user/getuserbyid?userId='+userId;
    // 获取此医院下的医生列表的URL
    var listUrl = '/hoc/hospitaladmin/getdoctorlistbyhospital?pageIndex=1&pageSize=999';
    getUserInfo(userId);
    getList();

    function getList() {
        // 从后台获取此医院的医生列表
        $.getJSON(listUrl, function(data) {
            if (data.success) {
                var doctorList = data.doctorList;
                console.log(doctorList);
                var tempAreaHtml = '';
                data.doctorList.map(function(item,index){
                    tempAreaHtml+='<option data-id="' +item.doctorId +'">'
                        +item.doctorName + '</option>'
                });
                $('#doctor').append(tempAreaHtml);
            }
        });
    }

    function getUserInfo(userId){
        $.getJSON(UserInfoUrl,function (data) {
            if(data.success){
                var user = data.user;
                console.log(user);
                if(data.doctor){
                    var doctor = data.doctor;
                }else{
                    var doctor = {
                        doctorId:-1,
                        doctorName:"暂未指派"
                    }
                }
                console.log(doctor.doctorName);
                $('#user-name').val(user.userName);
                $('#gender').val(user.gender);
                $('#user-email').val(user.email);
                $('#user-addr').val(user.addr);
                $('#user-phone').val(user.phone);
                $('#doctor').html('<option data-id="' + doctor.doctorId+ '">'
                        + doctor.doctorName + '</option>')
            }
        });
    }
    $('#submit').click(function () {
        var user = {};
        user.userId = userId;
        user.userName = $('#user-name').val();
        user.gender = $('#gender').val();
        user.phone = $('#user-phone').val();
        user.email = $('#user-email').val();
        user.addr = $('#user-addr').val();
        user.doctor = {
            doctorId: $('#doctor').find('option').not(function () {
                return !this.selected;
            }).data('id')
        }
        var formData = new FormData();
        formData.append('userStr',JSON.stringify(user));
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