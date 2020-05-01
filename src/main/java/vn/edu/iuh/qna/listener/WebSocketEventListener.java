package vn.edu.iuh.qna.listener;

import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

public class WebSocketEventListener {
	
	 @EventListener
	 public void handleWebSocketConnectListener(SessionConnectedEvent event) {
	 }
	 
	 @EventListener
	 public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
	 }
}
