function getDate(dateStr) {
	var parts = dateStr.split("-");
	var dt = new Date(parseInt(parts[2], 10),
		parseInt(parts[1], 10) - 1,
		parseInt(parts[0], 10));
	return dt;
}

function generateDate(from, to) {
	var dates = []
	var currentlyDate = new Date(from.getTime())
	while (currentlyDate.getTime() != to.getTime()) {
		dates.push(('0' + currentlyDate.getDate()).slice(-2) + '-' + ('0' + (currentlyDate.getMonth() + 1)).slice(-2) + '-' + currentlyDate.getFullYear())
		currentlyDate.setDate(currentlyDate.getDate() + 1)
	}
	dates.push(('0' + currentlyDate.getDate()).slice(-2) + '-' + ('0' + (currentlyDate.getMonth() + 1)).slice(-2) + '-' + currentlyDate.getFullYear())
	return dates;
}

function generatePoint(dates, report) {
	var fullyReport = []
	for (var i = 0; i < dates.length; ++i) {
		var date = dates[i];
		var result = report.find(function (el) {
			return el.date == date;
		})
		if (result) {
			fullyReport.push(result.number)
		} else {
			fullyReport.push(0)
		}
	}
	return fullyReport
}

var from = getDate(fromDateStr)
var to = getDate(toDateStr)
var dates = generateDate(from, to)

function getRandomColor() {
	var letters = '0123456789ABCDEF'.split('');
	var color = '#';
	for (var i = 0; i < 6; i++) {
		color += letters[Math.floor(Math.random() * 16)];
	}
	return color;
}


function generateColors(size) {
	colors = []

	for (var i = 0; i < size; ++i) {
		var color = getRandomColor();
		var flag = colors.find(c => c == color);

		if (!flag) {
			colors.push(color)
		} else {
			--i;
		}
	}
	return colors;
}

function producerData(report) {
	var colors = generateColors(report.length)
	data = {
		datasets: [{
			data: report.map(i => i.data),
			backgroundColor: colors,
		}],
		labels: report.map(i => i.label)
	};
	return data;
}
function producerChart(reportData, elementId, title) {
	var ctx = document.getElementById(elementId);
	//options
	var options = {
		responsive: true,
		title: {
			display: true,
			position: "top",
			text: title,
			fontSize: 18,
			fontColor: "#111"
		},
		legend: {
			display: true,
			position: "bottom",
			labels: {
				fontColor: "#333",
				fontSize: 16
			}
		}
	};
	var data = producerData(reportData)
	var myPieChart = new Chart(ctx, {
		type: 'pie',
		data: data,
		options: options
	});
}

producerChart(reportHaveAnswer,'reportHaveAnswer','Câu hỏi có câu trả lời')
producerChart(reportByCategory,'reportByCategory','Câu hỏi theo thể loại')
producerChart(reportByStatus,'reportByStatus','Tài khoản hoạt động')

$(function () {
	$("#form").submit(function (event) {
		var fromStr = $('input[name="from"]').val()
		var toStr = $('input[name="to"]').val()
		var from = new Date(fromStr)
		var to = new Date(toStr)
		var delta = to.getTime() - from.getTime();
		if (delta <= 0) {
			toastr.error('Dữ liệu ngày không hợp lệ', 'Có lỗi xảy ra!');
			return false;
		} else {
			var date = delta / 1000 / 60 / 60 / 24;
			if (date > 30) {
				toastr.error('Khoảnh cách thời gian tối đa 1 tháng(31 ngày)', 'Có lỗi xảy ra!');
				return false;
			}
		}
		return true;
	})
})