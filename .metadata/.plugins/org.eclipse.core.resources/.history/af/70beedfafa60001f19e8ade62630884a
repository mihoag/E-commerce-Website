package com.hcmus.site.shopping_cart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hcmus.common.entity.CartItem;
import com.hcmus.common.entity.Customer;
import com.hcmus.common.entity.product.Product;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Integer>, PagingAndSortingRepository<CartItem, Integer>{
    
	 List<CartItem> findByCustomer(Customer customer);
	
	 CartItem findByCustomerAndProduct(Customer customer, Product product);
	
	 @Modifying
     @Query("UPDATE CartItem c SET c.quantity = ?1 WHERE c.customer.id = ?2 AND c.product.id = ?3")
     public void updateQuantity(Integer quantity, Integer customerId, Integer productId);


		@Query("delete from CartItem c where c.product.id= ?1 and c.customer.id= ?2")
		@Modifying
		 void deleteByCustomerAndProduct( Integer productId, Integer customerId);
		
		@Modifying
		@Query("DELETE CartItem c WHERE c.customer.id = ?1")
		 void deleteByCustomer(Integer customerId);
}
