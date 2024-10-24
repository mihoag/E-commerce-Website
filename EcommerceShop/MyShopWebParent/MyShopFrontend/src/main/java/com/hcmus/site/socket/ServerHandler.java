package com.hcmus.site.socket;

import java.net.URI;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.hcmus.common.entity.Constant;
import com.hcmus.common.entity.chat.MessageDTO;
import com.hcmus.common.entity.chat.RoleChat;

public class ServerHandler extends TextWebSocketHandler {
	private static Set<WebSocketSession> sessions = Collections.newSetFromMap(new ConcurrentHashMap<>());
	private ServerClient serverClient;
	Gson gson = new Gson();
	private static final Logger logger = LoggerFactory.getLogger(ServerClient.class);

	public ServerHandler() {
		// Initialize the client connection to Server Client's side
		try {
			URI uri = new URI(Constant.ADMIN_SOCKET_CONNECTION_URI); // Change to your Server Client's side
			serverClient = new ServerClient(uri);
			serverClient.connect();
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
		}
		
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
		logger.info("Client connected: " + session.getId());
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		MessageDTO dto = gson.fromJson(message.getPayload(), MessageDTO.class);
		// Forward the message to Server Client's side
		// receive from HTML client
		if (dto.getRole_chat() == RoleChat.CUSTOMER) {
			serverClient.sendMessageToServer2(message.getPayload());
		}
		// receive from Server ADMIN's side
		else if (dto.getRole_chat() == RoleChat.ADMIN) {
			// Broadcast the message to all connected HTML clients
			broadcastToClients(message.getPayload());
		}

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		sessions.remove(session);
	}

	private void broadcastToClients(String message) throws Exception {
		for (WebSocketSession session : sessions) {
			if (session.isOpen()) {
				session.sendMessage(new TextMessage(message));
			}
		}
	}
}
