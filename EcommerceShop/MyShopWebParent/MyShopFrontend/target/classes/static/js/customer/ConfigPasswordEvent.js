var messageConfirmPassword = $("#messageConfirmPassword")


function checkPasswordMatch(confirmPassword) {
	var password = $("#password").val();
	var confirmP = confirmPassword.value;


	console.log(password + " " + confirmP);

	if (password != confirmP) {
		messageConfirmPassword.removeClass("hidden");
		return false;
	}
	else {
		messageConfirmPassword.addClass("hidden");
		return true;
	}
}