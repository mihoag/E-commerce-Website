
$("#radioCOD").on("click", function() {
	$("#buttonSubmit").removeClass("d-none");
});


function validateOrder(orderId) {
	$("#orderId").val(orderId);
	$("#paypalForm").submit();
}


paypal.Buttons({
	enableStandardCardFields: true,
	createOrder: function(data, actions) {
		// setup a transaction
		return actions.order.create({

			intent: 'CAPTURE', // make payment immediately
			payer: {
				name: {
					given_name: givenName,
					surname: surName
				},

				address: {
					address_line_1: addressLine1,
					address_line_2: addressLine2,
					admin_area_1: adminArea1,
					admin_area_2: adminArea2,
					postal_code: postalCode,
					country_code: countryCode
				},

				email_address: emailAddress,

				phone: {
					phone_type: "MOBILE",
					phone_number: {
						national_number: nationalNumber
					}
				}
			},

			purchase_units: [{
				amount: {
					value: valueTotal,
					currency_code: currencyCode
				}
			}],

			application_context: {
				shipping_preference: "NO_SHIPPING"
			}
		});
	},

	onApprove: function(data, actions) {
		// buyer approved payment
		return actions.order.capture().then(function(details) {
			console.log(details);
			orderId = details.id;
			validateOrder(orderId);
		});
	},

	onCancel: function(data) {
		// buyer cancelled payment
		showToast('Payment cancelled by the buyer');
	},

	onError: function(err) {
		// error that prevents buyer from doing checkout
		showToast("Something wrong with your address information, so payment will not work.");
	}
}).render("#paypal-button-container");