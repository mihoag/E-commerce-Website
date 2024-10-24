const toastLiveExample = $('#liveToast')
function showToast(value) {
	$("#toastMessage").text(value);
	const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample)
	toastBootstrap.show()
}