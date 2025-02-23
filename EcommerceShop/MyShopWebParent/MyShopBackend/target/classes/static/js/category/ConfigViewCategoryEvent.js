$('#deleteCate').on('show.bs.modal', async function(event) {
	var button = $(event.relatedTarget);
	var idCateDelete = button.data('id');
	var href = $('#buttonDelete').attr('href');
	href += "/" + idCateDelete;
	$('#buttonDelete').attr('href', href);
})

const message = document.getElementById("toastMessage");
$(document).ready(function() {
	// Show the toast
	console.log(message.innerText);
	if (message.innerText != '') {
		showToast(message.innerText);
	}
})