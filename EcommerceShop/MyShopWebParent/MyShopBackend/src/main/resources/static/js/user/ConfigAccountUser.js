const messageConfirmPassword = document.getElementById("messageConfirmPassword")
function checkMatchPassword(confirmPassword) {
	console.log(confirmPassword.value)
	if (confirmPassword.value != $("#password").val()) {
		messageConfirmPassword.classList.remove("hidden");
	} else {
		messageConfirmPassword.classList.add("hidden");
	}
}