const messageBrandName = document.getElementById("messageBrandName");
const nameInput = document.getElementById("name");
const formNewBrand = document.forms['formNewBrand']


function showMessage(element, message) {
	element.classList.remove("hidden");
	element.innerText = message;
}

async function checkNameUnique() {
	var brandName = $("#name").val();
	var id = $("#id").val();
	var params = { id: id, name: brandName };
	const url = '/MyshopAdmin/api/brands/check_name';
	$.post(url, params, async function(response) {
		if (response == "OK") {
			formNewBrand.submit();
		} else if (response == "Duplicate") {

		}
	}).fail(function() {

	});
}

nameInput.addEventListener('blur', function(event) {
	var brandName = $("#name").val();
	var id = $("#id").val();
	var params = { id: id, name: brandName };
	const url = '/MyshopAdmin/api/brands/check_name';
	$.post(url, params, async function(response) {
		console.log(response)
		if (response == "OK") {
			messageBrandName.classList.add("hidden")
		} else if (response == "Duplicate") {
			showMessage(messageBrandName, "The brand name is existed");
		}
	}).fail(function() {
		showMessage(messageBrandName, "Fail to connect to server");
	});
});

formNewBrand.addEventListener('submit', async function(event) {
	// Prevent the default form submission
	event.preventDefault();
	await checkNameUnique();
});






