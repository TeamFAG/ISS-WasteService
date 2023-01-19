package it.unibo.WasteserviceStatusManager

import it.unibo.WasteserviceStatusManager.observers.LedObserver
import it.unibo.WasteserviceStatusManager.observers.TrolleyPositionObserver
import it.unibo.WasteserviceStatusManager.observers.TrolleyStateObserver
import it.unibo.WasteserviceStatusManager.observers.WasteserviceObserver
import it.unibo.WasteserviceStatusManager.utils.SystemConfiguration
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.AbstractWebSocketHandler
import unibo.comm22.utils.ColorsOut

class WebSocketHandler: AbstractWebSocketHandler() {
    private val sessions = ArrayList<WebSocketSession>()
    private val guiStatusBean: GuiStatusBean
    private lateinit var wasteserviceObserver: WasteserviceObserver
    private lateinit var trolleyStateObserver: TrolleyStateObserver
    private lateinit var ledObserver: LedObserver
    private lateinit var trolleyPositionObserver: TrolleyPositionObserver

    init {
        SystemConfiguration.setTheConfiguration("SystemConfig")
        guiStatusBean = GuiStatusBean()

        for (obs in SystemConfiguration.activeObserver) {
            if(obs.value)
                when(obs.key) {
                    "wasteservice" -> wasteserviceObserver = WasteserviceObserver(sessions, guiStatusBean)
                    "led" -> ledObserver = LedObserver(sessions, guiStatusBean)
                    "trolleystate" -> trolleyStateObserver = TrolleyStateObserver(sessions, guiStatusBean)
                    "trolleyposition" -> trolleyPositionObserver = TrolleyPositionObserver(sessions, guiStatusBean)
                }
        }
    }

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