$(function() {
    // 获取有意向与当前医院签约的用户
    var listUserUrl = '/hoc/user/getfriendsigned';
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
                        + '<div class="col-33">'
                        + item.userName
                        + '</div>'
                        + '<div class="col-33">'
                        + item.phone
                        + '</div>'
                        + '<div class="col-33">'+'<a href = "#" class="getuser" data-id="'+item.userId+'">'+"进入"+'</a>'+'</div>'
                        + '</div>';
                });
                $('.doctor-wrap').html(tempHtml);
            }
        })
    }

// 将class为doctor-wrap里面的a标签绑定上点击的事件
    $('.doctor-wrap')
        .on(
            'click',
            'a',
            function(e) {
                var target = $(e.currentTarget);
                if (target.hasClass('getuser')) {
                    // 如果有class getInfo则点击就进入店铺信息编辑页面，并带有doctorId参数
                    window.location.href = '/hoc/hospitaladmin/getuser?userId='
                        + e.currentTarget.dataset.id;
                }
            });
});