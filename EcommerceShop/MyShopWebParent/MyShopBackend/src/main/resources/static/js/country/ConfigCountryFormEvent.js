const buttonLoadCountries = $("#buttonLoadCountries");
const dropDownCountries = $("#dropDownCountries");
const fieldCountryName = $("#fieldCountryName");
const fieldCountryCode = $("#fieldCountryCode");
const buttonAddCountry = $("#buttonAddCountry");
const buttonUpdateCountry = $("#buttonUpdateCountry");
const buttonDeleteCountry = $("#buttonDeleteCountry");
const toastLiveExample = $('#liveToast')
const labelCountryName = $("#labelCountryName")

buttonLoadCountries.on('click', async function() {
	loadCountries();
})

buttonAddCountry.on('click', async function() {
	if (buttonAddCountry.val() == 'Add') {
		addCountry();
	}
	else {
		changeFormStateToNewCountry();
	}
})

buttonDeleteCountry.on('click', async function() {
	deleteCountry();
})

dropDownCountries.on("change", function() {
	changeFormStateToSelectedCountry();
});

buttonUpdateCountry.on('click', async function() {
	updateCountry();
})

function changeFormStateToNewCountry() {
	buttonAddCountry.val("Add");
	labelCountryName.text("Country Name:");

	buttonUpdateCountry.prop("disabled", true);
	buttonDeleteCountry.prop("disabled", true);

	fieldCountryCode.val("");
	fieldCountryName.val("").focus();
}

function changeFormStateToSelectedCountry() {
	buttonAddCountry.prop("value", "New");
	buttonUpdateCountry.prop("disabled", false);
	buttonDeleteCountry.prop("disabled", false);

	labelCountryName.text("Selected Country:");

	var selectedCountryName = $("#dropDownCountries option:selected").text();
	fieldCountryName.val(selectedCountryName);

	countryCode = dropDownCountries.val().split("-")[1];
	fieldCountryCode.val(countryCode);
}


function loadCountries() {
	url = "/MyshopAdmin/api/country";
	$.get(url, function(responseJSON) {
		dropDownCountries.empty();

		$.each(responseJSON, function(index, country) {
			//console.log(country)
			optionValue = country.id + "-" + country.code;
			$("<option>").val(optionValue).text(country.name).appendTo(dropDownCountries);
		});
	}).done(function() {

		showToast("All countries have been loaded");
	}).fail(function() {
		showToast("ERROR: Could not connect to server or server encountered an error");
	});
}


function validateFormCountry() {
	var formCountry = document.getElementById("formCountry");
	if (!formCountry.checkValidity()) {
		formCountry.reportValidity();
		return false;
	}
	return true;
}


function addCountry() {

	if (!validateFormCountry()) {
		return;
	}
	url = "/MyshopAdmin/api/country/save";
	countryName = fieldCountryName.val();
	countryCode = fieldCountryCode.val();

	jsonData = { name: countryName, code: countryCode };

	$.ajax({
		type: 'POST',
		url: url,
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(countryId) {
		selectNewlyAddedCountry(countryId, countryCode, countryName);
		showToast("The new country has been added");
	}).fail(function() {
		showToast("ERROR: Could not connect to server or server encountered an error");
	});
}


function updateCountry() {
	if (!validateFormCountry()) {
		return;
	}
	url = "/MyshopAdmin/api/country/save";
	countryName = fieldCountryName.val();
	countryCode = fieldCountryCode.val();
	countryId = dropDownCountries.val().split("-")[0];

	jsonData = { id: countryId, name: countryName, code: countryCode };

	$.ajax({
		type: 'POST',
		url: url,
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(countryId) {
		selectNewlyAddedCountry(countryId, countryCode, countryName);
		showToast("The new country has been updated");
	}).fail(function() {
		showToast("ERROR: Could not connect to server or server encountered an error");
	});
}

function deleteCountry() {
	var countryId = dropDownCountries.val().split("-")[0];
	url = "/MyshopAdmin/api/country/delete/" + countryId;
	$.ajax({
		type: 'GET',
		url: url,
	}).done(function() {
		$("#dropDownCountries option[value='" + optionValue + "']").remove();
		changeFormStateToNewCountry();
		showToast("The new country has been deleted");
	}).fail(function() {
		showToast("ERROR: Could not connect to server or server encountered an error");
	});
}


function removeDeletedCountry() {
	fieldCountryCode.val("");
	fieldCountryName.val("").focus();
}

function selectNewlyAddedCountry(countryId, countryCode, countryName) {
	optionValue = countryId + "-" + countryCode;
	$("<option>").val(optionValue).text(countryName).appendTo(dropDownCountries);
	$("#dropDownCountries option[value='" + optionValue + "']").prop("selected", true);
	fieldCountryCode.val("");
	fieldCountryName.val("").focus();
}

function showToast(value) {
	$("#toastMessage").text(value);
	const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
	toastBootstrap.show()
}