package com.hcmus.admin.socket;

import java.io.Console;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerClient extends WebSocketClient {
    private static final int RECONNECT_INTERVAL_SECONDS = 100; // Time between reconnection attempts
    private AtomicBoolean reconnecting = new AtomicBoolean(false); // To prevent multiple threads
    private static final Logger logger = LoggerFactory.getLogger(ServerClient.class);
    
    public ServerClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
    	logger.info("Connected to Server");
        reconnecting.set(false); // Reset the flag once connection is successful
    }

    @Override
    public void onMessage(String message) {
    	logger.info("Received message from Server Client's side: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
    	logger.info("Disconnected from Server Client's side: " + reason);
        attemptReconnection();
    }

    @Override
    public void onError(Exception ex) {
        logger.error("Error: " + ex.getMessage());
        attemptReconnection();
    }

    // Method to send message to Server Client's side
    public void sendMessageToServerClientSide(String message) {
        send(message);  // Send the message to Server 2
        logger.info("Sent message to Server Client's side: " + message);
    }

    // Attempt reconnection to Server Client's side
    private void attemptReconnection() {
        if (reconnecting.compareAndSet(false, true)) {  // Ensure only one reconnection thread is active
            new Thread(() -> {
                while (!isOpen()) {
                	System.out.println(isOpen());
                    try {
                    	TimeUnit.SECONDS.sleep(RECONNECT_INTERVAL_SECONDS);
                    	logger.info("Attempting to reconnect to Server Client's side...");
                        this.reconnectBlocking(); // Blocking reconnect attempt
                        logger.info("Reconnected to Server Client's side");
                    } catch (Exception e) {
                        logger.error("Error: " + e.getMessage());
                    }
                }
            }).start();
        }
    }
}
