function getDate(dateStr){
	var parts = dateStr.split("-");
	var dt = new Date(parseInt(parts[2], 10),
			parseInt(parts[1], 10) - 1,
			parseInt(parts[0], 10));
	return dt;
}

function generateDate(from,to){
	var dates = []
	var currentlyDate = new Date(from.getTime())
	while(currentlyDate.getTime()!=to.getTime()){
		dates.push(('0'+currentlyDate.getDate()).slice(-2)+'-'+('0'+(currentlyDate.getMonth()+1)).slice(-2)+'-'+currentlyDate.getFullYear())
		currentlyDate.setDate(currentlyDate.getDate()+1)
	}
	dates.push(('0'+currentlyDate.getDate()).slice(-2)+'-'+('0'+(currentlyDate.getMonth()+1)).slice(-2)+'-'+currentlyDate.getFullYear())
	return dates;
}

function generatePoint(dates,report){
	var fullyReport = []
	for(var i=0;i<dates.length;++i){
		var date = dates[i];
		var result = report.find(function(el){
			return el.date == date;
		})
		if(result){
			fullyReport.push(result.number)
		} else {
			fullyReport.push(0)
		}
	}
	return fullyReport
}

var from = getDate(fromDateStr)
var to = getDate(toDateStr)
var dates = generateDate(from,to)

var canvas = document.getElementById('report');
new Chart(canvas, {
	type : 'line',
	data : {
		labels : dates,
		datasets : [ {
			label : 'Câu hỏi',
			yAxisID : 'A',
			data :generatePoint(dates,reports.question),
			 borderColor: "#3e95cd",
		}, {
			label : 'Trả lời',
			yAxisID : 'B',
			data : generatePoint(dates,reports.answer),
			borderColor: "#8e5ea2",
		} ]
	},
	options : {
		scales : {
			yAxes : [ {
				id : 'A',
				type : 'linear',
				position : 'left',
				ticks: {
		                min: 0
		         },
		         scaleLabel: {
		             display: true,
		             labelString: 'Số câu hỏi'
		         }
			}, {
				id : 'B',
				type : 'linear',
				position : 'right',
				scaleLabel: {
		             display: true,
		             labelString: 'Số câu trả lời'
		         }
			} ]
		}
	}
});