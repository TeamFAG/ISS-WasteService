package it.unibo.WasteserviceStatusManager

import it.unibo.WasteserviceStatusManager.observers.LedObserver
import it.unibo.WasteserviceStatusManager.observers.TrolleyPositionObserver
import it.unibo.WasteserviceStatusManager.observers.TrolleyStateObserver
import it.unibo.WasteserviceStatusManager.observers.WasteserviceObserver
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.AbstractWebSocketHandler
import unibo.comm22.utils.ColorsOut

class WebSocketHandler: AbstractWebSocketHandler() {
    private val sessions = ArrayList<WebSocketSession>()
    private val guiStatusBean = GuiStatusBean()
    private val wasteserviceObserver = WasteserviceObserver(sessions, guiStatusBean)
    //private val trolleyStateObserver = TrolleyStateObserver(sessions)
    //private val trolleyPositionObserver = TrolleyPositionObserver(sessions)
    private val ledObserver = LedObserver(sessions, guiStatusBean)

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions.add(session)
        super.afterConnectionEstablished(session)

        ColorsOut.outappl(guiStatusBean.toJSON().toString(), ColorsOut.BLUE)

        sendToAll(TextMessage(guiStatusBean.toJSON().toString()))
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(session)
        super.afterConnectionClosed(session, status)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        sendToAll(message)
    }

    protected fun sendToAll(message: TextMessage) {
        val iterator = sessions.iterator()

        while(iterator.hasNext()) {
            iterator.next().sendMessage(message)
        }
    }
}