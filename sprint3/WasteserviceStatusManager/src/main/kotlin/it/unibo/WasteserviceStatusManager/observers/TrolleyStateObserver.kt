package it.unibo.WasteserviceStatusManager.observers

import it.unibo.WasteserviceStatusManager.GuiStatusBean
import org.eclipse.californium.core.CoapHandler
import org.eclipse.californium.core.CoapResponse
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import unibo.comm22.coap.CoapConnection
import unibo.comm22.utils.ColorsOut
import unibo.comm22.utils.CommUtils

class TrolleyStateObserver(private val websocketList: ArrayList<WebSocketSession>): CoapHandler {

    init {
        startCoapConnection()
    }

    override fun onLoad(response: CoapResponse?) {
        TODO("Not yet implemented")
    }

    override fun onError() {
        TODO("Not yet implemented")
    }

    private fun startCoapConnection() {
        ColorsOut.outappl("WasteserviceObserver | creating coap connection", ColorsOut.BLUE)

        val connection = CoapConnection("localhost:8050", "trolleystateobserver/led")
        connection.observeResource(this)

        while (connection.request("") == "0") {
            ColorsOut.outappl("WasteserviceObserver | waiting for connection to wasteservice actor", ColorsOut.BLUE)
            CommUtils.delay(200)
        }
    }

    private fun sendUpdateToGui(glass: Float, plastic: Float) {
        val bean = GuiStatusBean(plastic, glass, "ON", "AFFFFFFF")
        for(ws in websocketList) {
            ws.sendMessage(TextMessage(bean.toJSON().toString()))
        }
    }
}