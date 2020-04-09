$(function() {
    // 获取有意向与当前医院签约的用户
    var listUserUrl = '/hoc/user/getconsultbyuser';
    getList();
    function getList() {
        $.getJSON(listUserUrl,function (data) {
            if(data.success){
                var consultationList = data.consultationList;
                console.log(consultationList);
                consultationList.map(function (item,index) {
                    var cardHTML = '<div class="card">' +"病历："+
                        '<div valign="bottom" class="card-header color-white no-border no-padding">'+
                            '<img class="card-cover" id="doctor-img"' + "src=" +item.medicalRecord+'>'+
                        '</div>'+
                        '<div class="card-header">'+"问诊时间："+item.createTime + '</div>' +
                        '<div class="card-content">' +
                        '<div class="card-content-inner">' +"症状："+
                        item.symptom +
                        '</div>' +
                        '<div class="card-footer no-border">' +"医生建议："+item.comment+'<br>'+"问诊单状态："+item.status+'<br>'+goConsultReply(item.consultId)+'</div>'+
                        '</div>';
                    $('.card-container').append(cardHTML);
                })

            }
        })
    }


    function goConsultReply(consultId) {
        return '<a href="/hoc/user/consultreply?consultId=' + consultId
            + '">更多回复</a>';

    }
})