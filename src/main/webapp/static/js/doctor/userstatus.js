$(function () {
    let userId = getQueryString('userId');
    var getHealthInfo = '/hoc/user/gethealthinfobyuserid?userId='+userId;
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

});