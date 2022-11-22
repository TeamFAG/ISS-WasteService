package it.unibo.wsdemoNoSTOMP;

import javax.imageio.ImageIO;
import javax.websocket.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URI;
import java.nio.ByteBuffer;

@ClientEndpoint
public class WebsocketClientEndpoint {
    Session userSession = null; //initialized by the method onOpen
    private IMessageHandler messageHandler;

    public WebsocketClientEndpoint(URI endpointURI) {
        try {
            WebSocketContainer container=
                    ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    //register message handler
    public void addMessageHandler(IMessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    //Send a message.
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    @OnOpen
    public void onOpen(Session userSession) {
        this.userSession = userSession;
    }

    //Callback hook for Connection close events.
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        this.userSession = null;
    }

    //Callback invoked when a client send a message.
    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }
    //Callback hook for images
    @OnMessage
    public void onMessage(ByteBuffer bytes) {
        try{
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes.array());
            //Dai bytes alla immagine e salvataggio in un file
            BufferedImage bImage2    = ImageIO.read(bis);
            ImageIO.write(bImage2, "jpg", new File("outputimage.jpg") );
        }catch( Exception e){ throw new RuntimeException(e); }
    }

}
