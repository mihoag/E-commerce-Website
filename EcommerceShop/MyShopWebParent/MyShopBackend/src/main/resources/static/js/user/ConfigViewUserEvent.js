 const message = document.getElementById("message");
 
 $(document).ready(function() {
            // Show the toast
            if(message.innerText != '')
            {
			   $('#successToast').toast('show');

              // Set timeout to hide the toast after 3 seconds (3000 milliseconds)
               setTimeout(function() {
                 $('#successToast').toast('hide');
               }, 3000);
			}
           
        });