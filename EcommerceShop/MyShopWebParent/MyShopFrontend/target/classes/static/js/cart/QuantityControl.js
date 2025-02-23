var minusElements;
var plusElements;

document.addEventListener("DOMContentLoaded", function() {
	minusElements = document.getElementsByClassName("linkMinus");
	plusElements = document.getElementsByClassName("linkPlus");

	Array.from(minusElements).forEach(function(minusElement) {
		var id = minusElement.getAttribute("pid");

		minusElement.addEventListener('click', function() {
			let quantityId = "quantity" + id;

			let valueQuantity = document.getElementById(quantityId);
			let value = parseInt(valueQuantity.value);

			if (value == 1) {
				showToast("The minimum quantity is 1");
				return;
			}

			valueQuantity.value = value - 1;
		})
	});

	Array.from(plusElements).forEach(function(plusElement) {
		var id = plusElement.getAttribute("pid");

		plusElement.addEventListener('click', function() {
			let quantityId = "quantity" + id;
			let valueQuantity = document.getElementById(quantityId);

			let value = parseInt(valueQuantity.value);

			if (value == 5) {
				showToast("The maximum quantity is 5");
				return;
			}

			valueQuantity.value = value + 1;

		})
	});
})