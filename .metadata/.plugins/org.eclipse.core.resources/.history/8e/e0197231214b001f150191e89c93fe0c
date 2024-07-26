const messageEmail = document.getElementById("messageEmail");
const email = document.getElementById("email");
const formNewUser = document.forms['formNewUser']

function showMessageEmail(message)
{
	messageEmail.classList.remove("hidden");
	messageEmail.innerText = message;
}


async function fetchApiCheckEmail(userEmail)
{
      
}

async function checkEmailUnique(form) {
			var userEmail = $("#email").val();
	        var userId = $("#id").val();
	 
	         var params = {id : userId ,email : userEmail};
	 
             const url = '/MyshopAdmin/api/users/check_email';          
             $.post(url, params, async function (response) {
				  if (response == "OK") {
			      form.submit();
		     } else if (response == "Duplicated") {
			  showMessageEmail("The email is existed"); 
		     } 
			 }).fail(function () {
				 showMessageEmail("Fail to connect to server");
			 }); 
			return false;
}


formNewUser.addEventListener('submit', async function(event) {
        // Prevent the default form submission
        event.preventDefault();
        await checkEmailUnique(event.target);
});

email.addEventListener('blur',  function(event) {
        var emailText = event.target.value;
        const response = fetchApiCheckEmail(emailText);
        var params = {email : emailText};
             const url = '/MyshopAdmin/api/users/check_email';          
             $.post(url, params, async function (response) {
				  if (response == "OK") {
			       messageEmail.classList.add("hidden")
		    } else if (response == "Duplicated") {
			    showMessageEmail("The email is existed");
		    }
			}).fail(function () {
				 showMessageEmail("Fail to connect to server");	
			 });
});


