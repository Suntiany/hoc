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
                        console.log(typeof(Object.values(data.friendList)[0].hospitalFollow));
                        console.log(typeof(Object.values(data.friendList)[i].user));
                        //console.log(Object.values(data.friendList)[i].user);
                        var newUser = Object.values(data.friendList)[i].user;
                        console.log(newUser);
                        var key="hospitalFollow";
                        var value = Object.values(data.friendList)[i].hospitalFollow;
                        newUser[key] = value;
                        console.log("添加hospitalFollow后的User==================");
                        console.log(newUser);
                        console.log("=============================================");
                        str.push(newUser);
                    }
                }
                //console.log(typeof(str));
                str.map(function (item,index) {
                    //console.log(item.userName);
                    //console.log(item.email);
                // 拼接用户信息
                    tempHtml += '' + '<div class="row row-doctor">'
                        + '<div class="col-25">'
                        + item.userName
                        + '</div>'
                        + '<div class="col-38">'
                        + item.phone
                        + '</div>'
                        + '<div class="col-25">'
                        + '<a href="#" class="status" data-id="'+item.userId
                        + '">'+item.hospitalFollow+'</a>'
                        + '</div>'
                        + '<div>'+'<a href = # class="delete" data-id="'+item.userId+'">'+"解约"+'</a>'+'</div>'
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
        }else if(target.hasClass('delete')){
            //如果有class delete则可以点击和用户进行解约
            deleteUser(e.currentTarget.dataset.id);
        }
    });


    function deleteUser(id) {
        $.getJSON('/hoc/user/deleteUser?userId='+id,function (data) {
            if(data.success){
                window.location.reload();
                $.alert("单方面解约成功");
            }else{
                $.alert("删除失败");
            }
        })
    }

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
                        window.location.reload();
                        $.toast('审核成功!');
                    }else {
                        $.toast('操作失败！');
                    }
                }
            });
        });
    }
});