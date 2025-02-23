package com.hcmus.site.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.hcmus.common.entity.Address;
import com.hcmus.common.entity.Customer;
import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
	List<Address> findByCustomer(Customer customer);

	@Query("select a from Address a where a.id = ?1 and a.customer.id= ?2")
	Address findByIdAndCustomer(Integer id, Integer customerId);

	@Modifying
	@Query("Update Address a set a.defaultForShipping = true where a.id = ?1")
	void setDefaultAddress(Integer id);

	@Modifying
	@Query("Delete from Address a where a.id = ?1 and a.customer.id = ?2")
	void deleteByIdAndCustomer(Integer id, Integer customerId);

	@Query("Select a from Address a where a.customer.id = ?1  and a.defaultForShipping = true")
	Address findDefaultByCustomer(Integer customerId);

	@Modifying
	@Query("Update Address a set a.defaultForShipping = false where a.id != ?1 and a.customer.id =?2")
	void setNotDefaultForOthers(Integer id, Integer customerId);

}
