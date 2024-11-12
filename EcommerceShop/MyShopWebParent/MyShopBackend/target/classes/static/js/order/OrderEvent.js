$(".link-detail").on("click", function(e) {
	e.preventDefault();
	var linkDetailURL = $(this).attr("href");
	console.log(linkDetailURL);
	$("#detailModal").modal("show").find(".modal-body").load(linkDetailURL);
});

$(".linkOrderDetail").on("click", function(e) {
	e.preventDefault();
	var linkDetailURL = $(this).attr("href");
	console.log(linkDetailURL);
	$("#detailModal").modal("show").find(".modal-body").load(linkDetailURL);
});

var href = '';
var status = '';
var orderId = '';
var iconNames = {
	'PICKED': 'fa-people-carry',
	'SHIPPING': 'fa-shipping-fast',
	'DELIVERED': 'fa-box-open',
	'RETURNED': 'fa-undo'
};

$('#updateStatusOrder').on('show.bs.modal', async function(event) {
	var button = $(event.relatedTarget);
	href = button.attr('href');
	status = button.attr('status');
	orderId = button.attr('orderId');
	console.log(href);
	console.log(status)
	console.log(orderId);
	$("#infoUpdateStatus").text('Are you sure to update the order with id ' + orderId + ' with status ' + status);
})

$('#buttonUpdate').on('click', function() {
	$.ajax({
		type: 'POST',
		url: href
	}).done(function(response) {
		showToast("Order updated successfully");
		updateStatusIconColor(response.orderId, response.status);
		//$('#updateStatusOrder').hide();
		setTimeout(() => {
			window.location.reload();
		}, 2000);

	}).fail(function(err) {
		showToast("Error updating order status");
	})
})

function updateStatusIconColor(orderId, status) {
	link = $("#link" + status + orderId);
	link.replaceWith(`<i  style="color: blue;"  class='fas " + ${iconNames[status]} + " icon-green'></i>`);
}

$('#deleteOrder').on('show.bs.modal', async function(event) {
	var button = $(event.relatedTarget);
	var idOrderDelete = button.data('id');
	var href = $('#buttonDelete').attr('href');
	href += "/" + idOrderDelete;
	$('#buttonDelete').attr('href', href);
})



