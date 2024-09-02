
$("#radioCOD").on("click", function () {
  $("#buttonSubmit").removeClass("d-none");
});


paypal.Buttons({
			enableStandardCardFields: true,
			createOrder: function (data, actions) {
				// setup a transaction
				return actions.order.create({

					intent: 'CAPTURE', // make payment immediately
					payer: {
						name: {
							given_name: "[[${customer.firstName}]]",
							surname: "[[${customer.lastName}]]"
						},

						address: {
							address_line_1: "[[${customer.addressLine1}]]",
							address_line_2: "[[${customer.addressLine2}]]",
							admin_area_1: "[[${customer.state}]]",
							admin_area_2: "[[${customer.city}]]",
							postal_code: "[[${customer.postalCode}]]",
							country_code: "[[${customer.country.code}]]"
						},

						email_address: "[[${customer.email}]]",

						phone: {
							phone_type: "MOBILE",
							phone_number: {
								national_number: "[[${customer.phoneNumber}]]"
							}
						}
					},

					purchase_units: [{
						amount: {
							value: "[[${checkoutInfo.paymentTotal4PayPal}]]",
							currency_code: "[[${currencyCode}]]"
						}
					}],

					application_context: {
						shipping_preference: "NO_SHIPPING"
					}
				});
			},

			onApprove: function (data, actions) {
				// buyer approved payment
				return actions.order.capture().then(function (details) {
					//console.log(details);
					orderId = details.id;
					validateOrder(orderId);
				});
			},

			onCancel: function (data) {
				// buyer cancelled payment
				alert('Payment cancelled by the buyer');
			},

			onError: function (err) {
				// error that prevents buyer from doing checkout
				showToast("Something wrong with your address information, so payment will not work.");
			}
}).render("#paypal-button-container");