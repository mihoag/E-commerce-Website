package com.hcmus.admin.order;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hcmus.admin.product.ProductRepository;
import com.hcmus.admin.setting.country.CountryRepository;
import com.hcmus.common.entity.Country;
import com.hcmus.common.entity.order.Order;
import com.hcmus.common.entity.order.OrderStatus;
import com.hcmus.common.entity.order.OrderTrack;

@Service
public class OrderService {
	private static final int ORDERS_PER_PAGE = 10;
	
	@Autowired private OrderRepository orderRepo;
	
	@Autowired private CountryRepository countryRepo;
	
	@Autowired private ProductRepository productRepo;
	
	public Page<Order> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
		
		Sort sort = null;
		
		if ("destination".equals(sortField)) {
			sort = Sort.by("country").and(Sort.by("state")).and(Sort.by("city"));
		} else {
			sort = Sort.by(sortField);
		}
		
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, ORDERS_PER_PAGE, sort);
		
		Page<Order> page = null;
		
		if (keyword != null) {
			page = orderRepo.findAll(keyword, pageable);
		} else {
			page = orderRepo.findAll(pageable);
		}
		
		return page;	
	}
	
	public Order get(Integer id) throws OrderNotFoundException {
		try {
			return orderRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new OrderNotFoundException("Could not find any orders with ID " + id);
		}
	}
	
	public void delete(Integer id) throws OrderNotFoundException {
		try {
			orderRepo.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			throw new OrderNotFoundException("Could not find any orders with ID " + id); 
		}	
	}

	public List<Country> listAllCountries() {
		return countryRepo.listAllAsc();
	}

	public void save(Order orderInForm) {
		Order orderInDB = orderRepo.findById(orderInForm.getId()).get();
		orderInForm.setOrderTime(orderInDB.getOrderTime());
		orderInForm.setCustomer(orderInDB.getCustomer());
		orderRepo.save(orderInForm);
	}	
	
	public void updateStatus(Integer orderId, String status) {
		Order orderInDB = orderRepo.findById(orderId).get();
		OrderStatus statusToUpdate = OrderStatus.valueOf(status);
		
		if (!orderInDB.hasStatus(statusToUpdate)) {
			List<OrderTrack> orderTracks = orderInDB.getOrderTracks();
			
			OrderTrack track = new OrderTrack();
			track.setOrder(orderInDB);
			track.setStatus(statusToUpdate);
			track.setUpdatedTime(new Date());
			track.setNotes(statusToUpdate.defaultDescription());
			
			orderTracks.add(track);
			
			orderInDB.setStatus(statusToUpdate);
			
			orderRepo.save(orderInDB);
		}
	}
	
	
	

}
