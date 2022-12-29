package it.unibo.WasteserviceStatusManager

import java.io.ByteArrayInputStream
import java.io.File
import java.net.URI
import java.nio.ByteBuffer
import javax.imageio.ImageIO
import javax.websocket.*

@ClientEndpoint
class WebSocketClientEndpoint(endpoint: URI) {
    private var userSession: Session? = null
    private lateinit var messageHandler: IMessageHandler

    init {
        try {
            val container = ContainerProvider.getWebSocketContainer()
            container.connectToServer(this, endpoint)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun addMessageHandler(msgHandler: IMessageHandler) {
        this.messageHandler = msgHandler
    }

    fun sendMessage(message: String) {
        this.userSession?.asyncRemote?.sendText(message)
    }

    @OnOpen
    fun onOpen(userSession: Session) {
        this.userSession = userSession
    }

    @OnClose
    fun onClose(userSession: Session, reason: CloseReason) {
        this.userSession = null
    }

    @OnMessage
    fun onMessage(message: String) {
        this.messageHandler.handleMessage(message)
    }

    @OnMessage
    fun onMessage(bytes: ByteBuffer) {
        try {
            val bis = ByteArrayInputStream(bytes.array())
            val bImage2 = ImageIO.read(bis)
            ImageIO.write(bImage2, "jpg", File("outputimage.jpg"))
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}