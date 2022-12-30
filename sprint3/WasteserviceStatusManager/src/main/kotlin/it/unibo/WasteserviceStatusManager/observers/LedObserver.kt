package it.unibo.WasteserviceStatusManager.observers

import it.unibo.WasteserviceStatusManager.GuiStatusBean
import it.unibo.WasteserviceStatusManager.utils.SystemConfiguration
import org.eclipse.californium.core.CoapHandler
import org.eclipse.californium.core.CoapResponse
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import unibo.comm22.coap.CoapConnection
import unibo.comm22.utils.ColorsOut
import unibo.comm22.utils.CommUtils

class LedObserver(private val websocketList: ArrayList<WebSocketSession>, private val guiBean: GuiStatusBean): CoapHandler {

    init {
        SystemConfiguration.setTheConfiguration("SystemConfig")
        startCoapConnection("led")
    }

    override fun onLoad(response: CoapResponse) {
        val payload = response.responseText

        ColorsOut.outappl(payload, ColorsOut.GREEN)

        if(payload.isBlank()) {
            // print error
            // need reconnection?
        }

        // filtraggio messaggio
        if(payload.isNotBlank()) {
            // led(ON)
            sendLedUpdateToGui(payload.split("(")[1].replace(")", ""))
        }
    }

    override fun onError() {
        TODO("Not yet implemented")
    }

    private fun startCoapConnection(actor: String) {
        ColorsOut.outappl("WasteserviceObserver | creating coap connection", ColorsOut.BLUE)

        val context = SystemConfiguration.contexts[actor] as String
        val host = SystemConfiguration.hosts[actor] as String
        val port = SystemConfiguration.ports[actor] as Int

        val connection = CoapConnection("${host}:${port}", "${context}/${actor}")

        connection.observeResource(this)

        while (connection.request("") == "0") {
            ColorsOut.outappl("WasteserviceObserver | waiting for connection to wasteservice actor", ColorsOut.BLUE)
            CommUtils.delay(200)
        }
    }

    private fun sendLedUpdateToGui(state: String) {
        synchronized(guiBean) {
            guiBean.ledState = state
        }

        for(ws in websocketList) {
            ws.sendMessage(TextMessage("{\"led\": $state}"))
        }
    }
}