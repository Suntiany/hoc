$(function () {
    var getHealthInfo = '/hoc/user/gethealthinfobysession';
    var addHealthInfo = '/hoc/user/addhealthinfo';
    var deleteHealthInfo = '/hoc/user/deleteinfobysession';
    getList()
    function getList() {
        $.getJSON(getHealthInfo,function (data) {
            if(data.success){
                //console.log(data);
                var myChart = echarts.init(document.getElementById('main'),{render:'svg'});
                var option = generateStaticEchartPart();
                option.series = data.series;
                console.log(option.series);
                console.log(typeof(option.series));
                //option.xAxis = data.xAxis;
                //console.log(option.xAxis);
                //console.log(typeof(option.xAxis))
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        })
    }

    function generateStaticEchartPart() {
        var option = {
            title: {},
            tooltip: {},
            legend: {
                data: ['上血压', '下血压', '血糖', '心率']
            },
            grid: {
                left: '3%',
                right: '3%',
                bottom: '3%',
                containLabel: true
            },
            yAxis: {
            },
            xAxis: {
                data: ["Mon","Tue","Wed","Thu","Fri","Sat","Sun"]
            }

        };
        return option;
    }

    $('#submit').click(function () {
        var healthMonitor = {};
        healthMonitor.bloodPressureHigh = $('#pressure-high').val();
        healthMonitor.bloodPressureLow = $('#pressure-low').val();
        healthMonitor.bloodGlucose = $('#blood-sugar').val();
        healthMonitor.heartRate = $('#heart-rate').val();
        var formData = new FormData();
        formData.append('healthInfoStr',JSON.stringify(healthMonitor));
        var verifyCodeActual = $('#j_captcha').val();
        if(!verifyCodeActual){
            $.toast('请输入验证码');
            return;
        }
        formData.append('verifyCodeActual',verifyCodeActual);
        $.ajax({
            url:addHealthInfo,
            type:'POST',
            data:formData,
            contentType:false,
            processData:false,
            cache:false,
            success:function (data) {
                if(data.success){
                    window.location.reload();
                    $.toast('提交成功！');
                }else{
                    $.toast('提交失败！'+data.errMsg);
                }
                $('#captcha_img').click();
            }
        })
    })

    $('#delete').click(function () {
        $.getJSON(deleteHealthInfo,function (data) {
            if(data.success){
                window.location.reload();
                $.toast('清除成功！');
            }else{
                $.toast('清除失败，请重试！');
            }
        })
    })
});