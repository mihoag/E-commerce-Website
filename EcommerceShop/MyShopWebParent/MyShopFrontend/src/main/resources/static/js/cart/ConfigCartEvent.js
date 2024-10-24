var cartItems = document.getElementsByClassName("cart-item");

document.addEventListener('DOMContentLoaded', function() {
	Array.from(cartItems).forEach(function(cartItem) {

		console.log(cartItem);
		var id = cartItem.getAttribute("pid");
		console.log(id);
		cartItem.addEventListener('click', function() {
			var quantityValue = document.getElementById("quantity" + id).value;
			addToCart(id, quantityValue);
		})

	})
});

function addToCart(productId, quantity) {
	url = "../api/cart/add/" + productId + "/" + quantity;
	$.ajax({
		type: "GET",
		url: url,
	}).done(function(response) {
		showToast(response);
	}).fail(function() {
		showToast("Error while adding product to shopping cart.");
	});
}