$(function(){
    let hospitalId = getQueryString('hospitalId');
    let hospitalInfoUrl = '/hoc/hospitaladmin/gethospitalbyid?hospitalId='+hospitalId;
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
})