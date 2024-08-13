  const formSearch  = document.forms['formSearch']
  $("#dropdownCategory").on("change", function () {
				formSearch.submit();
			});
			
 $(".link-detail").on("click", function (e) {
	e.preventDefault();
	var linkDetailURL = $(this).attr("href");
	//console.log(linkDetailURL);
	$("#detailModal").modal("show").find(".modal-content").load(linkDetailURL);
});

   $('#deleteProduct').on('show.bs.modal', async function (event) {
        var button = $(event.relatedTarget);
        var idBrandDelete = button.data('id');  
        var href = $('#buttonDelete').attr('href');
        href += "/" + idBrandDelete;
        $('#buttonDelete').attr('href',href);
    })
    
    