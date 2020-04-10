$(function() {
    var consultId = getQueryString('consultId');
    var replyUrl = '/hoc/user/reply?consultId='+consultId;
    var listReplyUrl = '/hoc/user/listreply?consultId='+consultId;
    getList();
    function getList() {
        $.getJSON(listReplyUrl,function (data) {
            if(data.success){
                var replyList = data.replyList;
                console.log(replyList);
                replyList.map(function (item,index) {
                    var cardHTML = '<div class="card"'+'id='+item.replyId+ '>' +
                        '<div class="card-content">' + '<p>'+"创建时间："+item.createTime+'<br>'+'<br>'+"内容："+item.message+'<br>'+'<br>'+"留言角色："+getRole(item.replyType)+'</p>'+
                        '</div>' ;
                    $('.card-container').append(cardHTML);
                })

            }
        })
    }

    $('#submit').click(function () {
        var message = $('#message').val();
        var formData = new FormData();
        formData.append("consultId",consultId);
        formData.append('message',message);
        formData.append("messageType",1);
        var verifyCodeActual = $('#j_captcha').val();
        if(!verifyCodeActual){
            $.toast('请输入验证码');
            return;
        }
        formData.append('verifyCodeActual',verifyCodeActual);
        $.ajax({
            url:replyUrl,
            type:'POST',
            data:formData,
            contentType:false,
            processData:false,
            cache:false,
            success:function (data) {
                if(data.success){
                    $.toast('留言成功');
                    window.location.reload();
                }else{
                    $.toast('留言失败！');
                }
            }
        })
    })


function getRole(replyType) {
    if(replyType==1){
        return "用户";
    }else if(replyType==2){
        return "医生";
    }
}
})