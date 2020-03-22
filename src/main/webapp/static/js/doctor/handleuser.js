$(function() {
    var userId = getQueryString('userId');
    var userInfoUrl = '/hoc/user/getuserbyid?userId=' + userId;
    $.getJSON(userInfoUrl, function(data) {
        if (data.success) {
            $('#userInfo')
                .attr('href', '/hoc/doctor/userinfo?userId=' + userId);
            $('#userStatus')
                .attr('href','/hoc/doctor/userstatus?userId=' + userId);
            $('#userConsult')
                .attr('href','/hoc/doctor/userconsult?userId='+userId);
        }
    });
});