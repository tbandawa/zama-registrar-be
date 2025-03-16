package me.tbandawa.web.skyzmetro.config;

import me.tbandawa.web.skyzmetro.services.SocketTextHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final SocketTextHandler socketTextHandler;

    public WebSocketConfig(SocketTextHandler socketTextHandler) {
        this.socketTextHandler = socketTextHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketTextHandler, "/registrar");
    }
}
