$(".link-add").on("click", function(e) {
	e.preventDefault();
	var linkAddlURL = $(this).attr("href");
	//console.log(linkDetailURL);
	$("#formModal").modal("show").find(".modal-body").load(linkAddlURL);
});

$(".link-edit").on("click", function(e) {
	e.preventDefault();
	var linkEditlURL = $(this).attr("href");
	//console.log(linkDetailURL);
	$("#formModal").modal("show").find(".modal-body").load(linkEditlURL);
});

