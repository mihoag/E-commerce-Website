package com.hcmus.chat.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hcmus.chat.model.message;

@Repository
public interface MessageRepository extends MongoRepository<message, ObjectId>{
	
	List<message> findByCustomerId(Integer customerId);
}
