$('#deleteBrand').on('show.bs.modal', async function(event) {
	var button = $(event.relatedTarget);
	var idBrandDelete = button.data('id');
	var href = $('#buttonDelete').attr('href');
	href += "/" + idBrandDelete;
	$('#buttonDelete').attr('href', href);
})

const message = document.getElementById("toastMessage");


$(document).ready(function() {
	// Show the toast
	if (message.innerText != '') {
		showToast(message.innerText);
	}
})
