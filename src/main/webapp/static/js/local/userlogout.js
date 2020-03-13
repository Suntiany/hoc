$(function() {
    $('#log-out').click(function() {
        // 清除session
        $.ajax({
            url : "/hoc/user/userlogout",
            type : "post",
            async : false,
            cache : false,
            dataType : 'json',
            success : function(data) {
                if (data.success) {
                    // 清除成功后退出到登录界面
                    window.location.href = "/hoc/local/userlogin";
                    return false;
                }
            },
            error : function(data, error) {
                alert(error);
            }
        });
    });
});