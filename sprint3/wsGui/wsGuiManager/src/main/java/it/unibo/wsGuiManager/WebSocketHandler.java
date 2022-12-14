package it.unibo.wsGuiManager;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WebSocketHandler extends AbstractWebSocketHandler {
    private final List<WebSocketSession> sessions=
            new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(
            WebSocketSession session) throws Exception{
        sessions.add(session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed( WebSocketSession session,
                                       CloseStatus status) throws Exception{
        sessions.remove(session);
        super.afterConnectionClosed(session, status);
    }
    @Override
    protected void handleTextMessage(WebSocketSession session,
                                     TextMessage message) throws IOException {
        sendToAll(message);
    }
    protected void sendToAll(TextMessage message) throws IOException{
        Iterator<WebSocketSession> iter = sessions.iterator();
        while( iter.hasNext() ){
            iter.next().sendMessage(message);
        }
    }
}
