package com.hcmus.admin.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hcmus.common.entity.User;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {
	
   @Query("select u from User u where u.email = ?1")	
   public User  getUserByEmail(String email);
  
   @Query("update User u set u.enabled = ?2 where u.id = ?1")
   @Modifying
   public void updateEnabledStatus(int id, boolean enabled);
   
   @Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' ', u.lastName) LIKE %?1%")
   public Page<User> findAll(String keyword, Pageable pageable);
}
