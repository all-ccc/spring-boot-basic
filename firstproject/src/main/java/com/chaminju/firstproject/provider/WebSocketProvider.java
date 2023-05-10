package com.chaminju.firstproject.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class SessionGroup { // 여기서만 쓸 Class
    private String room;
    private WebSocketSession session;
}

@Component
public class WebSocketProvider extends TextWebSocketHandler {
// 원래는 handler 패키지 만들어서 해주는 게 좋음(provider 말고 handler 로)
    
    private List<SessionGroup> sessionList = new ArrayList<>();

    //* 연결 */
    @Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String room = session.getHandshakeHeaders().getFirst("room"); // Headers에 보내는 거랑 똑같음
        sessionList.add(new SessionGroup(room, session));
        System.out.println(room + " / " + session.getId() + "Web Socket Connected!");
	}

    //* 메세지 송수신 */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String room = session.getHandshakeHeaders().getFirst("room");

        for (SessionGroup sessionGroup : sessionList) {
            if (sessionGroup.getRoom().equals(room) && 
                !sessionGroup.getSession().equals(session)) {
                sessionGroup.getSession().sendMessage(message); // 해당하는 room에만 메세지 보내기
            }
        }
	}

    //* 연결 해제 */
    @Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println(session.getId() + "Web Socket Closed!");

        for (SessionGroup sessionGroup: sessionList) {
            if (sessionGroup.getSession().equals(session)) {
                sessionList.remove(sessionGroup);
            }
        } 
	}

}
