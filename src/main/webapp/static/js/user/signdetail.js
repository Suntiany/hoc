$(function(){

    // 获取签约情况
    var listUrl = '/hoc/user/getfriendbyuser';
    getFriendInfo();


    function getFriendInfo(){
        $.getJSON(listUrl,function (data) {
            if(data.success){
                var str = [];
                var tempHtml='';
                    if(Object.values(data.friendList)[0]!=undefined){
                        //str.push(Object.values(data.friendList)[i].user);
                        //console.log(typeof(Object.values(data.friendList)[0].hospitalFollow));
                        //console.log(typeof(Object.values(data.friendList)[0].user));
                        //console.log(Object.values(data.friendList)[0].user);
                        var user = Object.values(data.friendList)[0].user;
                        var hospital = Object.values(data.friendList)[0].hospital;
                        var hospitalFollow = Object.values(data.friendList)[0].hospitalFollow;
                        var createTime = Object.values(data.friendList)[0].createTime;
                        console.log(user);
                        console.log(hospital);
                        console.log(hospitalFollow);
                        $('#user-name').val(user.userName);
                        $('#hospital-name').val(hospital.hospitalName);
                        $('#hospital-follow').val(hospitalFollow);
                        $('#create-time').val(createTime);
                    }
                }else{
                $.toast('未查询到任何签约信息');
            }
        });
    }

    $('#logback').click(function () {
        history.go(-1);
    })
})