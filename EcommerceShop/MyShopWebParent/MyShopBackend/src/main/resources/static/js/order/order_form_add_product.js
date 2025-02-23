var productDetailCount;

document.addEventListener('DOMContentLoaded', function() {
	productDetailCount = $(".hiddenProductId").length;
})

$("#linkAddProduct").on("click", function(e) {
	e.preventDefault();
	var linkAddlURL = $(this).attr("href");
	console.log(linkAddlURL);
	$("#addProductModal").modal("show").find(".modal-body").load(linkAddlURL);
});

$("#buttonAdd").on('click', function() {
	var productIds = [];
	$('.check-add:checked').each(function() {
		var pid = $(this).attr('pid');
		productIds.push(pid);
		var quantity = parseInt($("#quantity" + pid).val())
		addProduct(pid, quantity);
	});
	removeProduct(productIds);
})

function addProduct(productId, quantity) {
	getShippingCost(productId, quantity);
}

function getShippingCost(productId, quantity) {
	selectedCountry = $("#country option:selected");
	countryId = selectedCountry.val();

	state = $("#state").val();
	if (state.length == 0) {
		state = $("#city").val();
	}

	requestUrl = contextPath + "get_shipping_cost";
	params = { productId: productId, countryId: countryId, state: state };

	$.ajax({
		type: 'POST',
		url: requestUrl,
		data: params
	}).done(function(shippingCost) {
		getProductInfo(productId, shippingCost, quantity);
	}).fail(function(err) {
		shippingCost = 0.0;
		getProductInfo(productId, shippingCost, quantity);
	});
}



function getProductInfo(productId, shippingCost, quantity) {
	requestURL = contextPath + "api/products/" + productId;
	$.get(requestURL, function(productJson) {
		productName = productJson.name;
		mainImagePath = contextPath.substring(0, contextPath.length - 1) + productJson.image_path;
		productCost = $.number(parseFloat(productJson.cost) * quantity, 2);
		productPrice = $.number(parseFloat(productJson.price) * quantity, 2);

		console.log(productJson);

		htmlCode = generateProductCode(productId, productName, mainImagePath, productCost, productPrice, shippingCost, quantity);
		$("#productList").append(htmlCode);

		updateOrderAmounts();

	}).fail(function(err) {
	});
}

$('#buttonAdd').on('click', function() {
	var productId = [];
	var quantity = [];
	$('.check-add:checked').each(function() {
		var pid = $(this).attr('pid');
		productId.push(parseInt(pid));
		let quan = document.getElementById("quan" + pid).value;
		quantity.push(parseInt(quan));

		removeProduct(productId);
	});

})

function generateProductCode(productId, productName, mainImagePath, productCost, productPrice, shippingCost, quantity) {
	nextCount = productDetailCount + 1;
	productDetailCount++;
	rowId = "row" + nextCount;
	quantityId = "quantity" + nextCount;
	priceId = "price" + nextCount;
	subtotalId = "subtotal" + nextCount;
	blankLineId = "blankLine" + nextCount;

	htmlCode = `
		<div class="border rounded p-1" id="${rowId}">
		    <div class="row d-flex justify-content-between">
			<input type="hidden" name="detailId" value="0" />
			<input type="hidden" name="productId" value="${productId}" class="hiddenProductId" />
			<div class="row">
				<div class="col-1">
					<div class="divCount">${nextCount}</div>
					<div><a class="fas fa-trash icon-dark linkRemove"  rowNumber="${nextCount}"></a></div>				
				</div>
				<div class="col-11 col-sm-4">
					<img src="${mainImagePath}" class="img-fluid" />
					<div class="row m-2">
				       <b>${productName}</b>
			        </div>
				</div>
		
			<div class="col-12  col-sm-7 d-flex justify-content-center">
			<table>
				<tr>
					<td>Product Cost:</td>
					<td>
						<input type="text" required class="form-control m-1 cost-input"
							name="productDetailCost"
							rowNumber="${nextCount}" 
							value="${productCost}" style="max-width: 140px"/>
					</td>
				</tr>
				<tr>
					<td>Quantity:</td>
					<td>
						<input type="number" step="1" min="1" max="5" class="form-control m-1 quantity-input"
							name="quantity"
							id="${quantityId}"
							rowNumber="${nextCount}" 
							value="${quantity}" style="max-width: 140px"/>
					</td>
				</tr>	
				<tr>
					<td>Unit Price:</td>
					<td>
						<input type="text" required class="form-control m-1 price-input"
							name="productPrice"
							id="${priceId}"
							rowNumber="${nextCount}" 
							value="${productPrice}" style="max-width: 140px"/>
					</td>
				</tr>
				<tr>
					<td>Subtotal:</td>
					<td>
						<input type="text" readonly="readonly" class="form-control m-1 subtotal-output"
							name="productSubtotal"
							id="${subtotalId}" 
							value="${productPrice}" style="max-width: 140px"/>
					</td>
				</tr>				
				<tr>
					<td>Shipping Cost:</td>
					<td>
						<input type="text" required class="form-control m-1 ship-input"
							name="productShipCost" 
							value="${shippingCost}" style="max-width: 140px"/>
					</td>
				</tr>											
			</table>
			</div>
			</div>
			</div>
		</div>
		<div id="${blankLineId}"class="row">&nbsp;</div>	
	`;

	return htmlCode;
}


function removeProduct(ids) {
	for (let i = 0; i < ids.length; i++) {
		$('#product' + ids[i]).remove();
	}
}
