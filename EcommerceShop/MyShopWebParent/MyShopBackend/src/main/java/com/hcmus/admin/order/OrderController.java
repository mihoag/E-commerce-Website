package com.hcmus.admin.order;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hcmus.admin.product.ProductService;
import com.hcmus.admin.security.MyShopUserDetails;
import com.hcmus.admin.setting.SettingService;
import com.hcmus.common.entity.Country;
import com.hcmus.common.entity.order.Order;
import com.hcmus.common.entity.order.OrderDetail;
import com.hcmus.common.entity.order.OrderStatus;
import com.hcmus.common.entity.order.OrderTrack;
import com.hcmus.common.entity.product.Product;
import com.hcmus.common.entity.setting.Setting;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/orders")
public class OrderController {
	
	private String defaultRedirectURL = "redirect:/orders/page/1?sortField=orderTime&sortDir=desc";
	
	@Autowired
	private OrderService orderSerice;
	@Autowired private SettingService settingService;
	@Autowired
	private  ProductService productService;
	
	
	@GetMapping("/page/{pageNum}")
	public String listByPage(@PathVariable("pageNum") int pageNum, @Param("sortField") String sortField,
			@Param("sortDir") String sortDir, @Param("keyword") String keyword, Model model, @AuthenticationPrincipal MyShopUserDetails loggedUser,HttpServletRequest request) {
		
		     if(keyword == null)
		     {
		    	 keyword = "";
		     }
		     
		     loadCurrencySetting(request);	
		     
		     Page<Order> pageOrder = orderSerice.listByPage(pageNum, sortField, sortDir, keyword);
		     
		     List<Order> lsOrders = pageOrder.getContent();
		     String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		     
		     model.addAttribute("orders", lsOrders);
		     model.addAttribute("sideBarFieldName", "orders");
			 model.addAttribute("currentPage", pageNum);
			 model.addAttribute("totalPages", pageOrder.getTotalPages());
			 model.addAttribute("sortField", sortField);
		     model.addAttribute("sortDir", sortDir);
			 model.addAttribute("reverseSortDir", reverseSortDir);
			 model.addAttribute("keyword", keyword);
		     model.addAttribute("totalElement", pageOrder.getTotalElements());
		     
		 	if (!loggedUser.hasRole("Admin") && !loggedUser.hasRole("Salesperson") && loggedUser.hasRole("Shipper")) {
				return "order/orders_shipper";
			}
		     
		     return "order/orders";	
	}
	
	@GetMapping("/delete/{id}")
	public String deleteOrder(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		try {
			orderSerice.delete(id);;
			ra.addFlashAttribute("message", "The order ID " + id + " has been deleted.");
		} catch (OrderNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
		}
		
		return defaultRedirectURL;
	}
	
	
	
	@GetMapping("/detail/{id}")
	public String detailOrder(@PathVariable("id") Integer id, HttpServletRequest request,@AuthenticationPrincipal MyShopUserDetails loggedUser,  Model model) throws OrderNotFoundException
	{
		
	
		    Order order = orderSerice.get(id);
		    loadCurrencySetting(request);	
		    
            boolean isVisibleForAdminOrSalesperson = false;
			
			if (loggedUser.hasRole("Admin") || loggedUser.hasRole("Salesperson")) {
				isVisibleForAdminOrSalesperson = true;
			}
			
			model.addAttribute("isVisibleForAdminOrSalesperson", isVisibleForAdminOrSalesperson);
		    model.addAttribute("order", order);
		
		    return "order/order_detail_modal";
	}
	

	@GetMapping("/edit/{id}")
	public String editOrder(@PathVariable("id") Integer id, Model model, RedirectAttributes ra,
			HttpServletRequest request) {
		try {
			Order order = orderSerice.get(id);;
			
			List<Country> listCountries = orderSerice.listAllCountries();
			
			model.addAttribute("title", "Edit Order (ID: " + id + ")");
			model.addAttribute("order", order);
			model.addAttribute("listCountries", listCountries);
			
			return "order/order_form";
			
		} catch (OrderNotFoundException ex) {
			ra.addFlashAttribute("message", ex.getMessage());
			return defaultRedirectURL;
		}
	}
	
	@GetMapping("/notproducts/{id}")
	public String showProductNotInOrders(@PathVariable("id") Integer id, Model model)
	{
		List<Product> listProducts = productService.listProductNotInOrder(id);
		model.addAttribute("listProducts", listProducts);
		return "order/orders_products_form";
	}
	
	

	@PostMapping("/save")
	public String saveOrder(Order order, HttpServletRequest request, RedirectAttributes ra) {
		String countryName = request.getParameter("countryName");
		order.setCountry(countryName);
		updateProductDetails(order, request);
		updateOrderTracks(order, request);
		orderSerice.save(order);		
		ra.addFlashAttribute("message", "The order ID " + order.getId() + " has been updated successfully");
		return defaultRedirectURL;
	}

	
	
	
	private void updateOrderTracks(Order order, HttpServletRequest request) {
		String[] trackIds = request.getParameterValues("trackId");
		String[] trackStatuses = request.getParameterValues("trackStatus");
		String[] trackDates = request.getParameterValues("trackDate");
		String[] trackNotes = request.getParameterValues("trackNotes");
		
		List<OrderTrack> orderTracks = order.getOrderTracks();
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		if(trackIds != null)
		{
			for (int i = 0; i < trackIds.length; i++) {
				OrderTrack trackRecord = new OrderTrack();
				
				Integer trackId = Integer.parseInt(trackIds[i]);
				if (trackId > 0) {
					trackRecord.setId(trackId);
				}
				
				trackRecord.setOrder(order);
				trackRecord.setStatus(OrderStatus.valueOf(trackStatuses[i]));
				trackRecord.setNotes(trackNotes[i]);
				
				try {
					trackRecord.setUpdatedTime(dateFormatter.parse(trackDates[i]));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				orderTracks.add(trackRecord);
			}
		}
	}

	private void updateProductDetails(Order order, HttpServletRequest request) {
		String[] detailIds = request.getParameterValues("detailId");
		String[] productIds = request.getParameterValues("productId");
		String[] productPrices = request.getParameterValues("productPrice");
		String[] productDetailCosts = request.getParameterValues("productDetailCost");
		String[] quantities = request.getParameterValues("quantity");
		String[] productSubtotals = request.getParameterValues("productSubtotal");
		String[] productShipCosts = request.getParameterValues("productShipCost");
		
		Set<OrderDetail> orderDetails = order.getOrderDetails();
		
		for (int i = 0; i < detailIds.length; i++) {
			System.out.println("Detail ID: " + detailIds[i]);
			System.out.println("\t Prodouct ID: " + productIds[i]);
			System.out.println("\t Cost: " + productDetailCosts[i]);
			System.out.println("\t Quantity: " + quantities[i]);
			System.out.println("\t Subtotal: " + productSubtotals[i]);
			System.out.println("\t Ship cost: " + productShipCosts[i]);
			
			OrderDetail orderDetail = new OrderDetail();
			Integer detailId = Integer.parseInt(detailIds[i]);
			if (detailId > 0) {
				orderDetail.setId(detailId);
			}
			
			orderDetail.setOrder(order);
			orderDetail.setProduct(new Product(Integer.parseInt(productIds[i])));
			orderDetail.setProductCost(Float.parseFloat(productDetailCosts[i]));
			orderDetail.setSubtotal(Float.parseFloat(productSubtotals[i]));
			orderDetail.setShippingCost(Float.parseFloat(productShipCosts[i]));
			orderDetail.setQuantity(Integer.parseInt(quantities[i]));
			orderDetail.setUnitPrice(Float.parseFloat(productPrices[i]));
			
			orderDetails.add(orderDetail);
			
		}
		
	}
	
	@GetMapping("/**")
	public String listFirstPage(Model model)
	{
		return defaultRedirectURL;
	}
	
	private void loadCurrencySetting(HttpServletRequest request) {
		List<Setting> currencySettings = settingService.getCurrencySettings();
		
		for (Setting setting : currencySettings) {
			request.setAttribute(setting.getKey(), setting.getValue());
		}	
	}	
}
