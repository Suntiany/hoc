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
                    var cardHTML = '<div class="card">' +
                        '<div class="card-header">'+"问诊时间："+item.createTime + '</div>' +
                        '<div class="card-content">' +
                        '<div class="card-content-inner">' +"症状："+
                        item.symptom +
                        '</div>' +
                        '</div>' +
                        '<div class="card-footer">' +"医生建议："+item.comment+'<br>'+"问诊单状态："+item.status+'</div>'+
                        '</div>';
                    $('.card-container').append(cardHTML);
                })

            }
        })
    }
})