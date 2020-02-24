$(function() {
    getlist();
    function getlist(e) {
        $.ajax({
            url : "/hoc/hospitaladmin/gethospitallist",
            type : "get",
            dataType : "json",
            success : function(data) {
                if (data.success) {
                    handleList(data.hospitalList);
                    handleUser(data.user);
                }
            }
        });
    }
    function handleUser(data) {
        $('#user-name').text(data);
    }

    function handleList(data) {
        var html = '';
        data.map(function(item, index) {
            html += '<div class="row row-shop"><div class="col-40">'
                + item.hospitalName + '</div><div class="col-40">'
                + hospitalStatus(item.enableStatus)
                + '</div><div class="col-20">'
                + goHospital(item.enableStatus, item.hospitalId) + '</div></div>';

        });
        $('.shop-wrap').html(html);
    }

    function hospitalStatus(status) {
        if (status == 0) {
            return '审核中';
        } else if (status == -1) {
            return '医院非法';
        } else if (status == 1) {
            return '审核通过';
        }
    }

    function goHospital(status, id) {
        if (status == 1) {
            return '<a href="/hoc/hospitaladmin/hospitalmanagement?hospitalId=' + id
                + '">进入</a>';
        } else {
            return '';
        }
    }
});