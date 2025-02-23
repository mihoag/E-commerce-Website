const messageCateName = document.getElementById("messageCateName");
const messageAlias = document.getElementById("messageAlias");
const nameInput = document.getElementById("name");
const aliasInput = document.getElementById("alias");
const formNewCategory = document.forms['formNewCategory']


function showMessage(element, message) {
	element.classList.remove("hidden");
	element.innerText = message;
}

async function checkNameUnique() {
	var cateName = $("#name").val();
	var id = $("#id").val();
	var params = { id: id, name: cateName };
	const url = '/MyshopAdmin/api/categories/check_name';
	$.post(url, params, async function(response) {
		if (response == "OK") {
			await checkAliasUnique();
		} else if (response == "Duplicated") {

		}
	}).fail(function() {

	});
}



async function checkAliasUnique() {
	var alias = $("#alias").val();
	var id = $("#id").val();
	var params = { id: id, alias: alias };
	const url = '/MyshopAdmin/api/categories/check_alias';
	$.post(url, params, async function(response) {
		if (response == "OK") {
			formNewCategory.submit();
		} else if (response == "Duplicated") {

		}
	}).fail(function() {

	});
}

nameInput.addEventListener('blur', function(event) {
	var cateName = $("#name").val();
	var id = $("#id").val();
	var params = { id: id, name: cateName };
	const url = '/MyshopAdmin/api/categories/check_name';
	$.post(url, params, async function(response) {
		if (response == "OK") {
			messageCateName.classList.add("hidden")
		} else if (response == "Duplicated") {
			showMessage(messageCateName, "The category name is existed");
		}
	}).fail(function() {
		showMessage(messageCateName, "Fail to connect to server");
	});
});

aliasInput.addEventListener('blur', function(event) {
	var alias = $("#alias").val();
	var id = $("#id").val();
	var params = { id: id, alias: alias };
	const url = '/MyshopAdmin/api/categories/check_alias';
	$.post(url, params, async function(response) {
		if (response == "OK") {
			messageAlias.classList.add("hidden")
		} else if (response == "Duplicated") {
			showMessage(messageAlias, "The alias is existed");
		}
	}).fail(function() {
		showMessage(messageAlias, "Fail to connect to server");
	});
});

formNewCategory.addEventListener('submit', async function(event) {
	// Prevent the default form submission
	event.preventDefault();
	await checkNameUnique();
});






