const message = document.getElementById("toastMessage");
const detailId = document.getElementById("detailId");
const detailEmail = document.getElementById("detailEmail");
const detailName = document.getElementById("detailName");
const detailRole = document.getElementById("detailRole");
const detailEnabled = document.getElementById("detailEnabled");
const detailPhoto = document.getElementById("detailPhoto");
var idUserDelete;
const buttonDelete = document.getElementById("buttonDelete");

function makeRoles(roles) {
	var str = "";
	roles.forEach(role => {
		str += role.name + "   ";
	});
	return str;
}

$(document).ready(function() {
	// Show the toast
	console.log(message.innerText);
	if (message.innerText != '') {
		showToast(message.innerText);
	}

	// Show detail user
	$('#detailUser').on('show.bs.modal', async function(event) {
		var button = $(event.relatedTarget);
		var id = button.data('id')

		$.get(`/MyshopAdmin/api/users/${id}`, function(responseJson) {

			console.log(responseJson);

			detailId.innerText = responseJson.id;
			detailEmail.innerText = responseJson.email;
			detailName.innerText = responseJson.last_name + " " + responseJson.first_name;
			detailPhoto.setAttribute("src", responseJson.photos_image_path);
			detailEnabled.innerText = responseJson.enabled;
			detailRole.innerText = makeRoles(responseJson.roles);

		}).fail(function() {
			alert("FAIL")
		}
		);
	})

	// Event show delete user
	$('#deleteUser').on('show.bs.modal', async function(event) {
		var button = $(event.relatedTarget);
		idUserDelete = button.data('id');
	})

	buttonDelete.addEventListener("click", function() {
		$.ajax({
			url: '/MyshopAdmin/api/users/' + idUserDelete,
			type: 'DELETE',
			contentType: 'text/html',
			success: function() {
				window.location.href = "/MyshopAdmin/users/";
			},
			error: function() {
				window.location.href = "/MyshopAdmin/users/";
			}
		});
	})
});