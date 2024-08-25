const dropDownCountries = $("#country");
const dropDownStates = $("#listStates")

console.log(dropDownCountries)
dropDownCountries.on('change', function()
{
	loadStateByCountry();
})


function loadStateByCountry()
{
   var countryId = dropDownCountries.val();
   url =  "/MyshopAdmin/api/states/list_by_country/" + countryId;
	$.get(url, function(responseJSON) {
		dropDownStates.empty();
		$.each(responseJSON, function(index, state) {
			$("<option>").val(state.name).text(state.name).appendTo(dropDownStates);
		});
	}).done(function() {
		showToast("All states by country have been loaded");
	}).fail(function() {
		//console.log(e)
		showToast("ERROR: Could not connect to server or server encountered an error");
	});
}
