const fullname = $('#fullname');
const email = $('#email');
const phone = $('#phone');
const subject = $('#subject');
const message = $('#message');

const myForm = document.forms['myForm'];
myForm.addEventListener('submit', function(event) {
	event.preventDefault();
	// Get form data
	const formData = new FormData(event.target);
	// Convert form data to an object
	const data = {};
	formData.forEach((value, key) => {
		data[key] = value;
	});

	url = "api/contact/sendmail";
	$.post(url, data, function(response) {
		if (response == 'OK') {
			fullname.val('');
			email.val('');
			phone.val('');
			subject.val('');
			message.val('');

			showToast("Send successfully");
		}
		else {
			showToast('Fail to send email')
		}
	}).fail(function() {
		showToast("Could not connect to the server");
	});
})