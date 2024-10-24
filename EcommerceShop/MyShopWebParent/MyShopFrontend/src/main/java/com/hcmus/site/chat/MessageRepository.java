package com.hcmus.site.chat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcmus.common.entity.chat.message;
import com.hcmus.common.entity.Customer;

@Repository
public interface MessageRepository extends JpaRepository<message, Integer>{
	public  List<message> findByCustomer(Customer customer);
}
