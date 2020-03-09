$(function() {
    var hospitalId = getQueryString('hospitalId');
    var hospitalInfoUrl = '/hoc/hospitaladmin/gethospitalmanagementinfo?hospitalId=' + hospitalId;
    $.getJSON(hospitalInfoUrl, function(data) {
        if (data.redirect) {
            window.location.href = data.url;
        } else {
            if (data.hospitalId != undefined && data.hospitalId != null) {
                hospitalIdId = data.hospitalId;
            }
            $('#hospitalInfo')
            .attr('href', '/hoc/hospitaladmin/hospitaloperation?hospitalId=' + hospitalId);
    }
    });
});