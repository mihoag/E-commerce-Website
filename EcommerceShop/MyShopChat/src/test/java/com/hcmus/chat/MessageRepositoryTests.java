package com.hcmus.chat;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hcmus.chat.model.RoleChat;
import com.hcmus.chat.model.message;
import com.hcmus.chat.repository.MessageRepository;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class MessageRepositoryTests {

	@Autowired
	private MessageRepository messageRepo;
	
	@Test
	public void testAddMessage()
	{
		message mess = new message("Hi", 1, "Hoang", "", 2, "", new Date().toString(), RoleChat.CUSTOMER);
		message savedMess = messageRepo.save(mess);
		System.out.println(savedMess);
	}
	
	
	@Test
	public void testFindByCustomerId() {
		message mess = messageRepo.findById(new ObjectId("6732e4a40880073c145bf7fd")).get();
		System.out.println(mess);
	}
}
