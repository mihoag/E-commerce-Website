// Sales Report by Category
var data;
var chartOptions;

$(document).ready(function() {
	setupButtonEventHandlers("_category", loadSalesReportByDateForCategory);
});

function loadSalesReportByDateForCategory(period) {
	if (period == "custom") {
		startDate = $("#startDate_category").val();
		endDate = $("#endDate_category").val();

		requestURL = contextPath + "reports/category/" + startDate + "/" + endDate;
	} else {
		requestURL = contextPath + "reports/category/" + period;
	}

	$.get(requestURL, function(responseJSON) {

		//console.log(responseJSON);

		prepareChartDataForSalesReportByCategory(responseJSON);
		customizeChartForSalesReportByCategory();
		formatChartData(data, 1, 2);
		drawChartForSalesReportByCategory(period);
		setSalesAmount(period, '_category', "Total Products");
	});
}

function prepareChartDataForSalesReportByCategory(responseJSON) {
	data = new google.visualization.DataTable();
	data.addColumn('string', 'Category');
	data.addColumn('number', 'Gross Sales');
	data.addColumn('number', 'Net Sales');

	totalGrossSales = 0.0;
	totalNetSales = 0.0;
	totalItems = 0;

	$.each(responseJSON, function(index, reportItem) {
		data.addRows([[reportItem.identifier, reportItem.gross_sales, reportItem.net_sales]]);
		totalGrossSales += parseFloat(reportItem.gross_sales);
		totalNetSales += parseFloat(reportItem.net_sales);
		totalItems += parseInt(reportItem.products_count);
	});
}

function customizeChartForSalesReportByCategory() {
	chartOptions = {
		height: 360, legend: { position: 'right' }
	};
}

function drawChartForSalesReportByCategory() {
	var salesChart = new google.visualization.PieChart(document.getElementById('chart_sales_by_category'));
	salesChart.draw(data, chartOptions);
}