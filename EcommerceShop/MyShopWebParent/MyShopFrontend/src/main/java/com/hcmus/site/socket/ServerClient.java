package com.hcmus.site.socket;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerClient extends WebSocketClient {
	private static final int RECONNECT_INTERVAL_SECONDS = 5; // Time between reconnection attempts
	private AtomicBoolean reconnecting = new AtomicBoolean(false); // To prevent multiple threads
	private static final Logger logger = LoggerFactory.getLogger(ServerClient.class);

	public ServerClient(URI serverUri) {
		super(serverUri);
	}

	@Override
	public void onOpen(ServerHandshake handshake) {
		logger.info("Connected to Server");
		reconnecting.set(false);
	}

	@Override
	public void onMessage(String message) {
		logger.info("Received message from Server Admin's side: " + message);
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		logger.info("Disconnected from Server Admin's side: " + reason);
		attemptReconnection();
	}

	@Override
	public void onError(Exception ex) {
		logger.error("Error: " + ex.getMessage());
		attemptReconnection();
	}

	// Method to send message to Server Admin's side
	public void sendMessageToServer2(String message) {
		send(message); // Send the message to Server Admin's side
		logger.info("Sent message to Server Admin's side: " + message);
	}

	// Attempt reconnection to Server Client's side
	private void attemptReconnection() {
		if (reconnecting.compareAndSet(false, true)) { // Ensure only one reconnection thread is active
			new Thread(() -> {
				while (!isOpen()) {
					try {
						TimeUnit.SECONDS.sleep(RECONNECT_INTERVAL_SECONDS);
						logger.info("Attempting to reconnect to Server Admin's side...");
						this.reconnectBlocking(); // Blocking reconnect attempt
						logger.info("Reconnected to Server Admin's side");
						// reconnecting.set(false); // Reset flag after successful reconnection
						// break; // Exit the loop after successful reconnection
					} catch (Exception e) {
						logger.error("Error: " + e.getMessage());
					}
				}
			}).start();
		}
	}
}
