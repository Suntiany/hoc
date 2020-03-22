$(function() {
    getlist();
    function getlist(e) {
        $.ajax({
            url : "/hoc/doctor/getuserlist",
            type : "get",
            dataType : "json",
            success : function(data) {
                if (data.success) {
                    handleList(data.userList);
                    handleUser(data.doctor);
                }
            }
        });
    }

    function handleUser(data) {
        $('#user-name').text(data.doctorName);
    }
    function handleList(data) {
        var html = '';
        data.map(function(item, index) {
            html += '<div class="row row-shop"><div class="col-40">'
                + item.userName + '</div><div class="col-40">'
                + userStatus(item.enableStatus)
                + '</div><div class="col-20">'
                + goUserInfo(item.enableStatus, item.userId) + '</div></div>';

        });
        $('.shop-wrap').html(html);
    }

    function userStatus(status) {
      if(status == -1) {
            return '账号停用';
        } else if (status == 1) {
            return '账号正常';
        }
    }

    function goUserInfo(status, id) {
        if (status == 1) {
            return '<a href="/hoc/doctor/handleuser?userId=' + id
                + '">进入</a>';
        } else {
            return '';
        }
    }
});