const sideBar = document.getElementById("sidenav-main");
const navigationDropdown = document.getElementById("navigationDropdown");
const functionButtons = document.getElementById("functionButtons");

function updateScreenWidth() {
	const width = window.innerWidth;
	if (width < 768) {
		sideBar.classList.add("hidden");
		navigationDropdown.classList.remove("hidden");

		if (functionButtons != null) {
			functionButtons.classList.add("flex-column");
		}
	}
	else {
		sideBar.classList.remove("hidden");
		navigationDropdown.classList.add("hidden");

		if (functionButtons) {
			functionButtons.classList.remove("flex-column");
		}
	}
}

// Initial screen width
updateScreenWidth();

// Event listener for window resize
window.addEventListener('resize', updateScreenWidth);