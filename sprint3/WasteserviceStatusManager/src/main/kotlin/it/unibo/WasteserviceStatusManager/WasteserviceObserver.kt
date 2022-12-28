package it.unibo.WasteserviceStatusManager

import org.eclipse.californium.core.CoapHandler
import org.eclipse.californium.core.CoapResponse
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import unibo.comm22.coap.CoapConnection
import unibo.comm22.utils.ColorsOut
import unibo.comm22.utils.CommUtils

class WasteserviceObserver(private val websocketList: ArrayList<WebSocketSession>): CoapHandler {

    init {
        // ora ip porta e nome attore sono hardcodati, successivamente si usa file di conf
        startCoapConnection()
    }

    override fun onLoad(response: CoapResponse) {
        val payload = response.responseText

        if(payload.isBlank()) {
            // print error
            // need reconnection?
        }

        // filtraggio messaggio
        if(payload.isNotBlank() && payload.contains("cose")) {
            val glass = 10F
            val plastic = 10F

            sendUpdateToGui(glass, plastic)
        }
    }

    override fun onError() {
        TODO("Not yet implemented")
    }

    private fun startCoapConnection() {
        ColorsOut.outappl("WasteserviceObserver | creating coap connection", ColorsOut.BLUE)

        val connection = CoapConnection("localhost:8050", "ctxwasteservice_test/wasteservice")
        connection.observeResource(this)

        while (connection.request("") == "0") {
            ColorsOut.outappl("WasteserviceObserver | waiting for connection to wasteservice actor", ColorsOut.BLUE)
            CommUtils.delay(200)
        }
    }

    private fun sendUpdateToGui(glass: Float, plastic: Float) {
        for(ws in websocketList) {
            ws.sendMessage(TextMessage(""))
        }
    }
}