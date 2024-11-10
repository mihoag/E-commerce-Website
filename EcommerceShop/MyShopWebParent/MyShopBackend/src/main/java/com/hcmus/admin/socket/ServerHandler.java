package com.hcmus.admin.socket;

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
import com.hcmus.chat.model.RoleChat;
import com.hcmus.chat.model.message;
import com.hcmus.common.entity.Constant;

public class ServerHandler extends TextWebSocketHandler {
	private static Set<WebSocketSession> sessions = Collections.newSetFromMap(new ConcurrentHashMap<>());
	private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
	private ServerClient serverClient;
	Gson gson = new Gson();

	public ServerHandler() {
		// Initialize the client connection to Server Client's side
		try {
			URI uri = new URI(Constant.CLIENT_SOCKET_CONNECTION_URI); // Change to your Server Client's side
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
		message mess = gson.fromJson(message.getPayload(), message.class);

		logger.info("Role chat: " + mess.getRole_chat());
		// receive from HTML client
		if (mess.getRole_chat() == RoleChat.ADMIN) {
			serverClient.sendMessageToServerClientSide(message.getPayload());
		}
		
		// receive from Server Client's side
		else if (mess.getRole_chat() == RoleChat.CUSTOMER) {
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
