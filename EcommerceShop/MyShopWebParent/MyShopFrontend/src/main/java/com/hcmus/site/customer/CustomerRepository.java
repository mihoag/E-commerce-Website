package com.hcmus.site.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.hcmus.common.entity.AuthenticationType;
import com.hcmus.common.entity.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c where c.email = ?1")
	public Customer findByEmail(String email);

	@Query("select c from Customer c where c.verificationCode = ?1")
	Customer findByVerificationCode(String verificationCode);

	@Query("Update Customer c set c.enabled = true, c.verificationCode = null where c.id = ?1")
	@Modifying
	public void enable(Integer id);

	@Query("UPDATE Customer c SET c.authenticationType = ?2 WHERE c.id = ?1")
	@Modifying
	public void updateAuthenticationType(Integer customerId, AuthenticationType type);

}
