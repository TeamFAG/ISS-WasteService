package it.unibo.wsGuiManager;

import java.net.URI;
import java.net.URISyntaxException;

public class TestClient {

    public static void main(String[] args) throws URISyntaxException {
        // 1) open websocket
        WebsocketClientEndpoint clientEndPoint;
        clientEndPoint = new WebsocketClientEndpoint(new URI("ws://localhost:8085"));

        // 2) add listener
        clientEndPoint.addMessageHandler(new IMessageHandler() {
            public void handleMessage(String message) {
                System.out.println(message);
            }
        });

        // 3) send message to websocket
        clientEndPoint.sendMessage("hello from Java client");



    }
}
