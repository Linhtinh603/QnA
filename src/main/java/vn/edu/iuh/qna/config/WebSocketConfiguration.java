package vn.edu.iuh.qna.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer{
	@Autowired
	private HandshakeInterceptor interceptors;
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		WebSocketMessageBrokerConfigurer.super.registerStompEndpoints(registry);
		registry.addEndpoint("/ws").withSockJS().setInterceptors(interceptors);
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		WebSocketMessageBrokerConfigurer.super.configureMessageBroker(registry);
		 registry.setApplicationDestinationPrefixes("/app");
		 registry.enableSimpleBroker("/topic");
	}
	
}
