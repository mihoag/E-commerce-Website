
dropdownBrands = $("#brand");
dropdownCategories = $("#category");
const newForm = document.forms['newForm'];
const nameInput = document.getElementById("name");
const messageName = document.getElementById("messageName");

$(document).ready(function() {

	$("#shortDescription").richText();
	$("#fullDescription").richText();

	dropdownBrands.change(function() {
		dropdownCategories.empty();
		getCategories();
	});

	//getCategoriesForNewForm();

});

function getCategories() {
	brandId = dropdownBrands.val();
	var url = '/MyshopAdmin/api/brands/' + brandId + "/categories";

	$.get(url, function(responseJson) {
		console.log(responseJson);
		$.each(responseJson, function(index, category) {
			$("<option>").val(category.id).text(category.name).appendTo(dropdownCategories);
		});
	});
}

async function checkNameUnique() {
	var productname = $("#name").val();
	var id = $("#id").val();
	var params = { id: id, name: productname };
	const url = '/MyshopAdmin/api/products/checkname';
	$.post(url, params, async function(response) {
		if (response == "OK") {
			// console.log(response);
			newForm.submit();
		} else if (response == "Duplicate") {
			showMessage(messageName, "The product name is existed");
		}
	}).fail(function() {

	});
}

nameInput.addEventListener('blur', function(event) {
	var name = $("#name").val();
	var id = $("#id").val();
	var params = { id: id, name: name };
	const url = '/MyshopAdmin/api/products/checkname';
	$.post(url, params, async function(response) {
		console.log(response)
		if (response == "OK") {
			messageName.classList.add("hidden")
		} else if (response == "Duplicate") {
			showMessage(messageName, "The product name is existed");
		}
	}).fail(function() {
		showMessage(messageName, "Fail to connect to server");
	});
});


function showMessage(element, message) {
	element.classList.remove("hidden");
	element.innerText = message;
}

newForm.addEventListener('submit', async function(event) {
	// Prevent the default form submission
	//console.log("ok");
	event.preventDefault();
	await checkNameUnique();
});





