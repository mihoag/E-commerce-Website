var returnModal;
var modalTitle;
var fieldNote;
var orderId;
var divReason;
var divMessage;
var firstButton;
var secondButton;

$(document).ready(function() {
	returnModal = $("#returnOrderModal");
	modalTitle = $("#returnOrderModalTitle");
	fieldNote = $("#returnNote");
	divReason = $("#divReason");
	divMessage = $("#divMessage");
	firstButton = $("#firstButton");
	secondButton = $("#secondButton");

	handleReturnOrderLink();
});

function showReturnModalDialog(link) {
	divMessage.hide();
	divReason.show();
	firstButton.show();
	secondButton.text("Cancel");
	fieldNote.val("");

	orderId = link.attr("orderId");
	modalTitle.text("Return Order ID #" + orderId);
	returnModal.modal("show");
}

function showMessageModal(message) {
	divReason.hide();
	firstButton.hide();
	secondButton.text("Close");
	divMessage.text(message);

	divMessage.show();
}

function handleReturnOrderLink() {
	$(".linkReturnOrder").on("click", function(e) {
		e.preventDefault();
		showReturnModalDialog($(this));
	});
}

function submitReturnOrderForm() {
	reason = $("input[name='returnReason']:checked").val();
	note = fieldNote.val();

	sendReturnOrderRequest(reason, note);

	return false;
}

function sendReturnOrderRequest(reason, note) {
	requestURL = "/Myshop/orders/return";
	requestBody = { orderId: orderId, reason: reason, note: note };

	$.ajax({
		type: "POST",
		url: requestURL,
		data: JSON.stringify(requestBody),
		contentType: 'application/json'
	}).done(function(returnResponse) {
		showToast("Return request has been sent");
		updateStatusTextAndHideReturnButton(returnResponse.orderId);
	}).fail(function(err) {
		showToast(err.responseText);
	});

}

function updateStatusTextAndHideReturnButton(orderId) {
	$(".textOrderStatus" + orderId).each(function(index) {
		$(this).text("RETURN_REQUESTED");
	})

	$(".linkReturn" + orderId).each(function(index) {
		$(this).hide();
	})
}