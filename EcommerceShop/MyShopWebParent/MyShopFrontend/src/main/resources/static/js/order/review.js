
addEventHandlerForWriteReviewButton();

function addEventHandlerForWriteReviewButton() {

	$('.write-review').on("click", function() {
		var writeRv = $(this);
		var pid = $(this).attr('pid');
		var idSubmit = '#submit' + pid;
		var submitBtn = $(idSubmit);

		submitBtn.toggleClass('d-none');

		var reviewId = '#review' + pid;
		$(reviewId).toggleClass('d-none');

		submitBtn.on('click', function() {
			var headline = $('#headline' + pid).val();
			var content = $('#comment' + pid).val();
			$('#rating' + pid).keydown(function(e) {
				e.preventDefault(); // Prevent typing
			});

			var rating = $('#rating' + pid).val();
			if (validation(headline, content)) {
				var params = { orderDetailId: pid, headline: headline, comment: content, rating: rating };
				const url = '/Myshop/api/review';
				$.post(url, params, async function(response) {
					showToast(response);
					writeRv.addClass('d-none');
					submitBtn.addClass('d-none');
					$(reviewId).addClass('d-none');
				}).fail(function(e) {

				});
			}
		})
	});
}



function validation(headline, content) {
	if (headline == '' || content == '') {
		showToast('All fields must not be empty')
		return false;
	}
	return true;
}
