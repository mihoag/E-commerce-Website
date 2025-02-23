$(".link-detail").on("click", function(e) {
	e.preventDefault();
	var linkDetailURL = $(this).attr("href");
	//console.log(linkDetailURL);
	$("#detailModal").modal("show").find(".modal-body").load(linkDetailURL);
});


$(".link-update").on("click", function(e) {
	e.preventDefault();
	var linkDetailURL = $(this).attr("href");
	//console.log(linkDetailURL);
	$("#updateModal").modal("show").find(".modal-content").load(linkDetailURL);
});

$('#deleteCustomer').on('show.bs.modal', async function(event) {
	var button = $(event.relatedTarget);
	var idCustomerDelete = button.data('id');
	var href = $('#buttonDelete').attr('href');
	href += "/" + idCustomerDelete;
	$('#buttonDelete').attr('href', href);
})

const message = document.getElementById("toastMessage");
$(document).ready(function() {
	// Show the toast
	if (message.innerText != '') {
		showToast(message.innerText);
	}
})


