var minusElements;
var plusElements;

decimalSeparator = decimalPointType == 'COMMA' ? ',' : '.';
thousandsSeparator = thousandsPointType == 'COMMA' ? ',' : '.';


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

			subtotal = $("#subtotal" + id);
			subtotalPrice = (value - 1) * (parseFloat($("#subtotal" + id).text().replace(",", ""))) / value;
			updateSubTotal(subtotal, subtotalPrice);

			updateQuantity(id, value - 1);

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

			subtotal = $("#subtotal" + id);
			subtotalPrice = (value + 1) * (parseFloat($("#subtotal" + id).text().replace(",", ""))) / value;
			updateSubTotal(subtotal, subtotalPrice);

			valueQuantity.value = value + 1;
			updateQuantity(id, value + 1);
		})
	});
})

function updateQuantity(productId, quantity) {
	url = "api/cart/update/" + productId + "/" + quantity;
	$.ajax({
		type: "GET",
		url: url,
	}).done(function(response) {
		updateTotalPrice(response)
	}).fail(function() {
		showToast("Error while ajusting product quantity in shopping cart.");
	});
}

function updateSubTotal(element, total) {
	element.text(formatCurrency(total));
}

function updateTotalPrice(total) {
	$("#total").text(formatCurrency(total));
}


function formatCurrency(amount) {
	return $.number(amount, decimalDigits, decimalSeparator, thousandsSeparator);
}

function deleteProduct(productId) {
	url = "api/cart/delete/" + productId;
	$.ajax({
		type: "GET",
		url: url,
	}).done(function(response) {
		showToast("The product with id " + productId + " was removed successfully")
		updateTotalPrice(response);
		$("#item" + productId).remove();
		updateRemainedItemsQuantity();
	}).fail(function() {
		showToast("Error while ajusting product quantity in shopping cart.");
	});
}

function updateRemainedItemsQuantity() {
	var elements = document.getElementsByClassName("product-in-cart");

	const length = elements.length;

	$("#totalItem").text(length);

	if (length == 0) {
		$("#list-items").remove();
		$("#noItemMessage").show();
	}
}

function removeItem(item) {
	var id = item.getAttribute("pid");
	deleteProduct(id);
}