const buttonLoadCountriesForStates = $("#buttonLoadCountriesForStates")
const dropDownCountriesForStates = $("#dropDownCountriesForStates")
const dropDownStates = $("#dropDownStates")
const labelStateName = $("#labelStateName")
const fieldStateName = $("#fieldStateName")
const buttonAddState = $("#buttonAddState")
const buttonUpdateState = $("#buttonUpdateState")
const buttonDeleteState = $("#buttonDeleteState")

buttonLoadCountriesForStates.on('click', function() {
	loadCountriesForStates();
})

dropDownStates.on("change", function() {
	changeFormStateToSelectedState();
});



buttonAddState.click(function() {
	if (buttonAddState.val() == "Add") {
		addState();
	} else {
		changeFormStateToNew();
	}
});

buttonUpdateState.click(function() {
	updateState();
});

buttonDeleteState.click(function() {
	deleteState();
});

function loadCountriesForStates() {
	url = "/MyshopAdmin/api/country";
	$.get(url, function(responseJSON) {
		dropDownCountriesForStates.empty();
		$.each(responseJSON, function(index, country) {
			//console.log(country)
			optionValue = country.id + "-" + country.code;
			$("<option>").val(optionValue).text(country.name).appendTo(dropDownCountriesForStates);
		});
	}).done(function() {

		showToast("All countries have been loaded");
	}).fail(function() {
		showToast("ERROR: Could not connect to server or server encountered an error");
	});
}

dropDownCountriesForStates.on('change', function() {
	loadStateByCountry();
})

function loadStateByCountry() {
	var countryId = dropDownCountriesForStates.val().split("-")[0];
	url = "/MyshopAdmin/api/states/list_by_country/" + countryId;
	$.get(url, function(responseJSON) {
		dropDownStates.empty();
		console.log(responseJSON);
		$.each(responseJSON, function(index, state) {
			//console.log(country)
			optionValue = state.id;
			$("<option>").val(optionValue).text(state.name).appendTo(dropDownStates);
		});
	}).done(function() {
		showToast("All states by country have been loaded");
	}).fail(function() {
		//console.log(e)
		showToast("ERROR: Could not connect to server or server encountered an error");
	});
}




function validateFormState() {
	formState = document.getElementById("formState");
	if (!formState.checkValidity()) {
		formState.reportValidity();
		return false;
	}

	return true;
}

function selectNewlyAddedState(stateId, stateName) {
	$("<option>").val(stateId).text(stateName).appendTo(dropDownStates);

	$("#dropDownStates option[value='" + stateId + "']").prop("selected", true);

	fieldStateName.val("").focus();
}

function changeFormStateToNew() {
	buttonAddState.val("Add");
	labelStateName.text("State/Province Name:");

	buttonUpdateState.prop("disabled", true);
	buttonDeleteState.prop("disabled", true);

	fieldStateName.val("").focus();
}

function changeFormStateToSelectedState() {
	buttonAddState.prop("value", "New");
	buttonUpdateState.prop("disabled", false);
	buttonDeleteState.prop("disabled", false);

	labelStateName.text("Selected State/Province:");

	selectedStateName = $("#dropDownStates option:selected").text();
	fieldStateName.val(selectedStateName);

}

function deleteState() {
	var stateId = dropDownStates.val();

	url = "/MyshopAdmin/api/states/delete/" + stateId;

	$.ajax({
		type: 'GET',
		url: url,
	}).done(function() {
		$("#dropDownStates option[value='" + stateId + "']").remove();
		changeFormStateToNew();
		showToast("The state has been deleted");
	}).fail(function() {
		showToast("ERROR: Could not connect to server or server encountered an error");
	});
}

function updateState() {
	if (!validateFormState()) return;

	var url = "/MyshopAdmin/api/states/save";
	stateId = dropDownStates.val();
	stateName = fieldStateName.val();

	selectedCountry = $("#dropDownCountriesForStates option:selected");
	countryId = selectedCountry.val().split("-")[0];
	countryName = selectedCountry.text();

	jsonData = { id: stateId, name: stateName, country: { id: countryId, name: countryName } };

	$.ajax({
		type: 'POST',
		url: url,
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(stateId) {
		$("#dropDownStates option:selected").text(stateName);
		showToast("The state has been updated");
		changeFormStateToNew();
	}).fail(function() {
		showToast("ERROR: Could not connect to server or server encountered an error");
	});
}

function addState() {
	if (!validateFormState()) return;

	var url = "/MyshopAdmin/api/states/save";
	stateName = fieldStateName.val();

	selectedCountry = $("#dropDownCountriesForStates option:selected");
	countryId = selectedCountry.val().split("-")[0];
	countryName = selectedCountry.text();

	jsonData = { name: stateName, country: { id: countryId, name: countryName } };

	$.ajax({
		type: 'POST',
		url: url,
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(stateId) {
		selectNewlyAddedState(stateId, stateName);
		showToast("The new state has been added");
	}).fail(function() {
		showToast("ERROR: Could not connect to server or server encountered an error");
	});

}
