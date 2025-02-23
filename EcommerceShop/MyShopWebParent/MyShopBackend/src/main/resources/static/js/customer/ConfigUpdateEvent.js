
var country = $("#country");
var dataListState = $("#listStates");

country.on('change', function() {
	loadStateByCountry();
})

function checkEmailUnique(form) {
	url = "api/customers/check_email";
	customerEmail = $("#email").val();


	params = { email: customerEmail };

	$.post(url, params, function(response) {
		if (response == "OK") {
			form.submit();
		} else if (response == "Duplicated") {
			showToast("There is another cusotmer having the email " + customerEmail);
		} else {
			showToast("Unknown response from server");
		}
	}).fail(function() {
		showToast("Could not connect to the server");
	});

	return false;
}


function loadStateByCountry() {
	var idCountry = country.val();

	url = "api/states/list_by_country/" + idCountry;

	$.get(url, function(responseJSON) {
		dataListState.empty();
		$.each(responseJSON, function(index, state) {
			//console.log(country)
			$("<option>").val(state.name).text(state.name).appendTo(dataListState);
		});
	}).done(function() {
		showToast("All states by country have been loaded");
	}).fail(function() {
		//console.log(e)
		showToast("ERROR: Could not connect to server or server encountered an error");
	});
}