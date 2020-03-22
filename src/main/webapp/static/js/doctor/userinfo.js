$(function(){
    let userId = getQueryString('userId');
    let UserInfoUrl = '/hoc/user/getuserbyid?userId='+userId;
    getUserInfo(userId);


    function getUserInfo(userId){
        $.getJSON(UserInfoUrl,function (data) {
            if(data.success){
                var user = data.user;
                var area = user.area;
                $('#user-name').val(user.userName);
                $('#gender').val(user.gender);
                $('#user-email').val(user.email);
                $('#user-addr').val(user.addr);
                $('#user-phone').val(user.phone);
                $('#area').val(area.areaName);
            }
        });
    }

    $('#logback').click(function () {
        history.go(-1);
    })
})