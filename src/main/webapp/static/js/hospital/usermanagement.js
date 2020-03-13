$(function() {
    // 获取有意向与当前医院签约的用户
    var listUserUrl = '/hoc/user/getfriendbyhospital';
    getList();

    function getList() {
        $.getJSON(listUserUrl,function (data) {
            if(data.success){
                var str = [];
                var tempHtml='';
                for(var i=0;i<100;i++){
                    if(Object.values(data.friendList)[i]!=undefined){
                        //str.push(Object.values(data.friendList)[i].user);
                        console.log(Object.values(data.friendList)[i].user);
                        str.push(Object.values(data.friendList)[i].user);
                    }
                }
                //console.log(typeof(str));
                str.map(function (item,index) {
                    console.log(item.userName);
                    console.log(item.email);
                // 拼接用户信息
                    tempHtml += '' + '<div class="row row-doctor">'
                        + '<div class="col-33">'
                        + item.userName
                        + '</div>'
                        + '<div class="col-33">'
                        + item.email
                        + '</div>'
                        + '<div class="col-33">'
                        + '<a href="#" class="status" data-id="'+item.userId
                        + '">待审核</a>'
                        + '</div>'
                        + '</div>';
                });
                $('.doctor-wrap').html(tempHtml);
            }
        })
    }

    $('.doctor-wrap').on('click','a',function (e) {
        var target = $(e.currentTarget);
        if(target.hasClass('status')) {
            changeItemStatus(e.currentTarget.dataset.id);
        }
    });

    function changeItemStatus(id) {
        var friend = {};
        friend.userId = id;
        $.confirm("确定吗？",function() {
            //审核用户签约
            $.ajax({
                url:'/hoc/user/verifyuser?userId='+id,
                type:'GET',
                success: function(data) {
                    if(data.success) {
                        $.toast('审核成功!');
                    }else {
                        $.toast('操作失败！');
                    }
                }
            });
        });
    }
});