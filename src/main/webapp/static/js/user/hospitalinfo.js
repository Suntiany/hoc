$(function(){
    let hospitalId = getQueryString('hospitalId');
    let hospitalInfoUrl = '/hoc/hospitaladmin/gethospitalbyid?hospitalId='+hospitalId;
    let signHospitalUrl = '/hoc/user/signbyhospitalid?hospitalId='+hospitalId;
    let deleteFriendshipUrl = '/hoc/user/deletefriend?hospitalId='+hospitalId;
    getHospitalInfo();
    function getHospitalInfo(hospitalId){
        $.getJSON(hospitalInfoUrl,function (data) {
            if(data.success){
                var hospital = data.hospital;
                console.log(hospital.hospitalName);
                console.log(hospital.phone);
                console.log(hospital.hospitalDesc);
                $('#hospitalName').text("机构名称："+hospital.hospitalName);
                $('#hospitalPhone').text("联系电话："+hospital.phone);
                $('#hospitalDesc').text(hospital.hospitalDesc);
            }
        });
    }
    $(document).on('click','.create-actions', function () {
        var buttons1 = [
            {
                text: '请选择',
                label: true
            },
            {
                text: '签约',
                bold: true,
                color: 'danger',
                onClick: function() {
                    $.getJSON(signHospitalUrl,function (data) {
                        if(data.success){
                            $.alert("您的签约申请已提交，请耐心等待");
                        }else{
                            $.alert("已提交过申请，可以点击申请详情查看签约情况");
                        }
                    })
                }
            },
            {
                text: '解约',
                onClick: function() {
                    $.getJSON(deleteFriendshipUrl,function (data) {
                        if(data.success){
                            $.alert("单方面解约成功");
                        }else{
                            $.alert("当前用户并未签约本医院或已提交但是未审核");
                        }
                    })
                }
            }
        ];
        var buttons2 = [
            {
                text: '取消',
                bg: 'danger'
            }
        ];
        var groups = [buttons1, buttons2];
        $.actions(groups);
    });
})