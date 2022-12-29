package it.unibo.WasteserviceStatusManager

import org.json.JSONObject

class GuiStatusBean(plasticContainerState: Float = 0f, glassContainerState: Float = 0f, ledState: String = "OFF", trolleyPosition: String = "AT HOME") {

    private val statusGui = mutableMapOf(
        "plasticContainerState" to plasticContainerState,
        "glassContainerState" to glassContainerState,
        "ledState" to ledState,
        "trolleyPosition" to trolleyPosition
    )

    fun toJSON(): JSONObject = JSONObject(this.statusGui)
}