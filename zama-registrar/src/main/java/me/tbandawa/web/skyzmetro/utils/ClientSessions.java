package me.tbandawa.web.skyzmetro.utils;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Component
public class ClientSessions {

    private final List<WebSocketSession> webSocketSessions =
            Collections.synchronizedList(new ArrayList<>());

    public void addWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSessions.add(webSocketSession);
    }

    public void removeWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSessions.remove(webSocketSession);
    }
}
