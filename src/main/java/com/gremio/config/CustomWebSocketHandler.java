package com.gremio.config;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.logging.Logger;

public class CustomWebSocketHandler extends TextWebSocketHandler {
    private static final Logger LOGGER = Logger.getLogger(WebSocketConfig.class.getName());
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // Handle connection established
        LOGGER.info("Connection established");
    }
    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        // Handle text message received
        LOGGER.info("Message received: " + message.getPayload());
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // Handle connection closed
        LOGGER.info("Connection closed");
    }
}