package com.hcmus.site.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hcmus.common.entity.Customer;
import com.hcmus.common.entity.order.Order;
import com.hcmus.site.Utility;
import com.hcmus.site.customer.CustomerService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/orders")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@Autowired
	private CustomerService customerService;

	@GetMapping
	public String listFirstPage(Model model, HttpServletRequest request) {
		return listOrdersByPage(model, request, 1, "orderTime", "desc", null);
	}

	@GetMapping("/page/{pageNum}")
	public String listOrdersByPage(Model model, HttpServletRequest request, @PathVariable(name = "pageNum") int pageNum,
			String sortField, String sortDir, String keyword) {
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		Customer customer = customerService.getCustomerByEmail(email);

		if (keyword == null) {
			keyword = "";
		}

		Page<Order> page = orderService.listForCustomerByPage(customer, pageNum, sortField, sortDir, keyword);
		List<Order> listOrders = page.getContent();

		model.addAttribute("orders", listOrders);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		model.addAttribute("keyword", keyword);
		model.addAttribute("totalElement", page.getTotalElements());
		model.addAttribute("sideBarFieldName", "orders");
		return "orders/orders_customer";
	}

	@GetMapping("/detail/{id}")
	public String viewOrderDetails(Model model, @PathVariable(name = "id") Integer id, HttpServletRequest request) {
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		Customer customer = customerService.getCustomerByEmail(email);

		Order order = orderService.getOrder(id, customer);

		model.addAttribute("order", order);

		return "orders/order_details_modal";
	}
}
