package it.unibo.WasteserviceStatusManager.observers

import it.unibo.WasteserviceStatusManager.GuiStatusBean
import it.unibo.WasteserviceStatusManager.utils.SystemConfiguration
import netscape.javascript.JSObject
import org.eclipse.californium.core.CoapHandler
import org.eclipse.californium.core.CoapResponse
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import unibo.comm22.coap.CoapConnection
import unibo.comm22.utils.ColorsOut
import unibo.comm22.utils.CommUtils

class WasteserviceObserver(private val websocketList: ArrayList<WebSocketSession>, private val guiBean: GuiStatusBean): CoapHandler {

    init {
        SystemConfiguration.setTheConfiguration("SystemConfig")
        startCoapConnection("wasteservice")
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
            with(payload) {
                when {
                    contains("Plastic") -> sendPlasticUpdateToGui(payload.split(" ")[1].replace(")", "").toFloat())
                    contains("Glass") -> sendGlassUpdateToGui(payload.split(" ")[1].replace(")", "").toFloat())
                    contains("max") -> {
                        val maxpb = payload.split("(")[1].split(" ")[1].replace(",", "").toFloat()
                        val maxgb = payload.split(" ")[3].replace(")", "").toFloat()

                        sendContainerInit(maxpb, maxgb)
                    }
                }
            }
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

    private fun sendGlassUpdateToGui(quantity: Float) {
        synchronized(guiBean) {
            guiBean.glass = quantity
        }

        for(ws in websocketList) {
            ws.sendMessage(TextMessage("{\"glass\": $quantity}"))
        }
    }

    private fun sendPlasticUpdateToGui(quantity: Float) {
        synchronized(guiBean) {
            guiBean.plastic = quantity
        }

        for(ws in websocketList) {
            ws.sendMessage(TextMessage("{\"plastic\": $quantity}"))
        }
    }

    private fun sendContainerInit(maxpb: Float, maxgb: Float) {
        synchronized(guiBean) {
            guiBean.maxgb = maxgb
            guiBean.maxpb = maxpb
        }

        for(ws in websocketList) {
            ws.sendMessage(TextMessage("{\"MAXGB\": $maxgb, \"MAXPB\": $maxpb}"))
        }
    }
}