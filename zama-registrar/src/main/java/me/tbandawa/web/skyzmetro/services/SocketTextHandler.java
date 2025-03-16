package me.tbandawa.web.skyzmetro.services;

import me.tbandawa.web.skyzmetro.utils.ClientSessions;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketTextHandler extends TextWebSocketHandler {

    private final ClientSessions clientSessions;

    public SocketTextHandler(ClientSessions clientSessions) {
        this.clientSessions = clientSessions;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        clientSessions.addWebSocketSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        clientSessions.removeWebSocketSession(session);
    }
}
