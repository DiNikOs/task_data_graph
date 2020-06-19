google.charts.load('current', {	callback: function () {
        var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
        var options = {
            title: 'График стоимости активов от даты',
            vAxis: {title: 'Стоимость'},
            hAxis: {title: 'Дата'},
            curveType: 'none',
            seriesType: 'bars',
            legend: { position: 'bottom' }
        };
        drawChart();
        // Auto draw for Timer
        setInterval(drawChart, 2000);

        function drawChart() {
            $.getJSON({
                url: 'api/v0/data',
                type: 'get'
            }).done(function (jsonData) {
                let headers =[];
                let rowArr =[];
                let mas = [];
                let i = 0;
                let masTmp = [];
                $(jsonData).each(function (index, item) {
                    let j = 0;
                    masTmp = headers;
                    headers = [];
                    let parts = item.created.split('-');
                    let tmp = new Date(parts[0], parts[1]-1, parts[2]);
                    let dataTime = item.created;
                    if (i < 1) {
                        mas.push('Data');
                        $(jsonData).each(function (index, item) {
                            let tmp = item.name;
                            mas.push(tmp);
                            masTmp.push(tmp);
                        });
                        rowArr.push(mas);
                        mas = [];
                    }
                    mas[i] = [];
                    headers.push(tmp);
                    mas[i][j] = new Date(item.created);
                    $(jsonData).each(function (index, item) {
                        j++;
                        if (dataTime == item.created) {
                            tmp = item.counts;
                            mas[i][j] = item.counts;
                        } else {
                            tmp = 0;
                            mas[i][j] = 0;
                        }
                        headers.push(tmp)
                    });
                    i++;
                        let one;
                        let tri = _.isEqual(masTmp, headers);
                        if (!tri){
                        $(rowArr).each(function (index, item) {
                            one = _.isEqual(item, headers);
                            if (one) {
                                return false;
                                }
                            });
                            if (!one) {
                                rowArr.push(headers);
                            }
                        }
                });
                if (rowArr.length==0){
                    console.log('Data Row before:', rowArr.length);
                    let date = new Date();
                    rowArr = [
                        ['Data','NO DATE'],
                        [new Date(date.getFullYear(), date.getMonth(), date.getDay()), 0]
                    ]
                }
                let data = new google.visualization.arrayToDataTable(rowArr);
                chart.draw(data, options);
            }).fail(function (jqXHR, textStatus, errorThrown) {
                console.log(textStatus, errorThrown);
            });
        }
    },'packages':['corechart']});
// google.charts.load('current', {'packages':['corechart']});
// google.charts.setOnLoadCallback(drawVisualization);
// function drawVisualization() {
// 	// Some raw data (not necessarily accurate)
// 	var data = google.visualization.arrayToDataTable([
// 		['Month', 'Газпром', 'Автоваз', 'Сбербанк'],
// 		['01.01.2019', 2000, 2500, 0],
// 		['05.01.2019', 0, 0, 10000],
// 		['10.01.2019', 2500, 0, 0],
// 		['07.10.2019', 0, 2100, 0]
// 	]);
// 	var options = {
// 		title : 'График стоимости от даты',
// 		vAxis: {title: 'Стоимость'},
// 		hAxis: {title: 'Дата'},
// 		seriesType: 'bars',
// 		series: {5: {type: 'line'}}        };
//
// 	var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
// 	chart.draw(data, options);
// }