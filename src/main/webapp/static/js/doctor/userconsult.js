$(function() {
    var userId = getQueryString('userId');
    var listUserUrl = '/hoc/user/getconsultbydoctor?userId='+userId;
    getList();
    function getList() {
        $.getJSON(listUserUrl,function (data) {
            if(data.success){
                var consultationList = data.consultationList;
                console.log(consultationList);
                consultationList.map(function (item,index) {
                    var cardHTML = '<div class="card"'+'id='+item.consultId+ '>' +"病历："+
                        '<div valign="bottom" class="card-header color-white no-border no-padding">'+
                        '<img class="card-cover" id="doctor-img"' + "src=" +item.medicalRecord+'>'+
                        '</div>'+
                        '<div class="card-header">'+"问诊时间："+item.createTime + '</div>' +
                        '<div class="card-content">' +
                        '<div class="card-content-inner">' +"症状："+
                        item.symptom +
                        '</div>' +
                        '</div>' +
                        '<div class="card-footer no-border">' +"医生建议："+item.comment+'<br>'+"问诊单状态："+item.status+'</div>'+
                        '</div>' +
                         goConsultReply(item.consultId);
                    $('.card-container').append(cardHTML);
                })

            }
        })
    }
    $('.card-container').on(
        'click',
        '.card',
        function(e) {
            var consultId = $(e.currentTarget).attr('id');
            console.log(consultId);
            window.location.href = '/hoc/doctor/dealconsult?consultId='
                + consultId;
        });



    function goConsultReply(consultId) {
        return '<a href="/hoc/user/consultreply?consultId=' + consultId
            + '" style="font-size: 20px;padding-left: 75%">更多回复</a>';
    }

    // function dealConsult(consultId) {
    //     return  '<a href="/hoc/doctor/dealconsult?consultId=' + consultId
    //         +'" onclick="window.location.href">诊断处理</a>';
    //
    // }

})