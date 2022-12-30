package it.unibo.WasteserviceStatusManager

import org.json.JSONObject

class GuiStatusBean(plasticContainerState: Float = 0f, glassContainerState: Float = 0f, ledState: String = "OFF", trolleyPosition: String = "HOME", trolleyState: String = "IDLE", maxpb: Float = 100F, maxgb: Float = 100F) {

    var bean = "bean"
    var plastic = plasticContainerState
    var glass = glassContainerState
    var trolleyState = trolleyState
    var trolleyPosition = trolleyPosition
    var ledState = ledState
    var maxpb = maxpb
    var maxgb = maxgb

    private val statusGui = mutableMapOf(
        "bean" to bean,
        "plastic" to plastic,
        "glass" to glass,
        "ledstate" to this.ledState,
        "trolleyposition" to this.trolleyPosition,
        "trolleystate" to this.trolleyState,
        "MAXPB" to this.maxpb,
        "MAXGB" to this.maxgb
    )

    fun toJSON(): JSONObject = JSONObject(this.statusGui)
}