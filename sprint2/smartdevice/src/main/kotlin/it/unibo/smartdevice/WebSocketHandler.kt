package it.unibo.smartdevice

import org.springframework.web.socket.BinaryMessage
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.AbstractWebSocketHandler

class WebSocketHandler: AbstractWebSocketHandler() {

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        session.sendMessage(message)
    }

    override fun handleBinaryMessage(session: WebSocketSession, message: BinaryMessage) {
        session.sendMessage(message)
    }
}