$(function() {
    var hospitalId = getQueryString('hospitalId');
    var hospitalInfoUrl = '/hoc/hospitaladmin/gethospitalmanagementinfo?hospitalId=' + hospitalId;
    $.getJSON(hospitalInfoUrl, function(data) {
        if (data.success) {
            $('#hospitalInfo')
            .attr('href', '/hoc/hospitaladmin/hospitaloperation?hospitalId=' + hospitalId);
    }
    });
});