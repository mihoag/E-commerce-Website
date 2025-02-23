var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var btnSend = document.querySelector('#btnSend');
var images = document.querySelectorAll('img');
var loadingIndicator = document.getElementById('loadingIndicator');

document.addEventListener('DOMContentLoaded', function() {
	var chatBox = document.getElementById('messageArea');
	var mobileHeight = window.innerHeight;
	chatBox.style.height = mobileHeight - 150 + 'px';

	setTimeout(function() {
		var divMess = document.createElement('div');
		divMess.innerHTML = `
	    <div class="d-flex flex-row justify-content-start">
        <img
          src="https://cdn-images-1.medium.com/v2/resize:fit:1600/0*Z7BBg5UiOY2k7MbY.png"
          alt="avatar 1"
          style="width: 45px; height: 100%"
        />
        <div>
          <p
            class="small p-2 ms-3 mb-3 rounded-3"
            style="background-color: #f5f6f7"
          >
           Welcome to our AI-powered product recommendation service! 🎉 Just describe what you're looking for, and our smart chatbot will suggest the best products tailored to your needs. Let's find the perfect match for you! 😊
          </p>
         
        </div>
      </div>
	        `

		messageArea.appendChild(divMess);
		messageArea.scrollTop = messageArea.scrollHeight;
	}, 2000);
})

window.addEventListener('resize', function() {
	var chatBox = document.getElementById('messageArea');
	var mobileHeight = window.innerHeight;
	chatBox.style.height = mobileHeight - 150 + 'px';
});




function sendMessage(event) {
	var messageContent = messageInput.value.trim();
	if (messageContent) {
		var divMess = document.createElement('div');
		divMess.innerHTML = `
	    <div class="d-flex justify-content-between">
                
                <p class="small mb-1 text-muted"></p>
                <p class="small mb-1">You</p>
              </div>
              <div class="d-flex flex-row justify-content-end"> 
                <div>
                  <p
                    class="small p-2 ms-3 mb-3 rounded-3"
                    style="background-color: #f5f6f7"
                  >
                    ${messageContent}
                  </p>
                </div>
                <img
                  src="https://openvldoostende.be/wp-content/uploads/2023/01/abstract-user-icon-1.jpg"
                  alt="avatar 1"
                  style="width: 30px; height: 100%"
                />
              </div>
	        `
		messageInput.value = '';
		messageArea.appendChild(divMess);
		messageArea.scrollTop = messageArea.scrollHeight;
		recieveMessage(messageContent);
	}
	event.preventDefault();
}


function generateResponse(data) {
	var host = window.location.host;
	var pathName = window.location.pathname;
	const protocol = window.location.protocol;
	
	// Context path (typically the root path after the host)
	var contextPath = pathName.substring(0, pathName.indexOf("/", 1));

	let str = ``;
	for (let i = 0; i < data.length; i++) {
		str += `  <div class="card mb-3 product-in-cart">
                  <div class="card-body">
                    <div class="d-flex justify-content-between">
                      <div class="col-5 d-flex flex-row align-items-center">
                        <div>
                          <img
                            src="${data[i].image}"
                            class="img-fluid rounded-3" alt="Shopping item" style="width: 65px;">
                         </div>
                         <div class="ms-3">
                          <h6>${data[i].name.length < 25 ? data[i].name.length : data[i].name.substring(0,25) + "..."}</h6>
                         </div>
                        </div>
                        <div class="col-2 d-flex flex-row align-items-center justify-content-between">    
                          <a  href = ${protocol + "//" +  host + contextPath + data[i].uri} target = "_blank">Detail</a> 
                        </div>
                     </div>
                    </div>
                  </div>
                `

	}
	return `
              <div class="d-flex flex-row justify-content-start">
        <img
          src="https://cdn-images-1.medium.com/v2/resize:fit:1600/0*Z7BBg5UiOY2k7MbY.png"
          alt="avatar 1"
          style="width: 45px; height: 100%"
        />
        <div>
          <p
            class="small p-2 ms-3 mb-3 rounded-3"
            style="background-color: #f5f6f7"
          > Here's are some recommended products with your description <br/>
            ${str}
          </p>
         
        </div>
      </div>
          `
}

function recieveMessage(description) {
	const query = {
		description: description, // your product description
		top_k: 5 // number of top recommendations you want
	};
	loadingIndicator.classList.remove('hidden')
	fetch(AI_SERVER_URI + '/recommend-products', {
		method: 'POST', // HTTP method
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(query) // convert the query object to a JSON string
	})
		.then(response => response.json()) // parse JSON response
		.then(data => {
			loadingIndicator.classList.add('hidden')
			//console.log('Recommended Products:', data);
			// You can update the UI or handle the response here
			let res = generateResponse(data);
			var divMess = document.createElement('div');
			divMess.innerHTML = res;
			messageArea.appendChild(divMess);
			messageArea.scrollTop = messageArea.scrollHeight;

		})
		.catch(error => {
			console.error('Error:', error);
		});
}

//messageForm.addEventListener('submit', sendMessage, true)
btnSend.addEventListener('click', sendMessage)