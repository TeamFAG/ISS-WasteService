package wsWebSupport

import org.json.JSONObject
import ws.LedState
import ws.TrolleyPosition

class GuiStatus(plasticContainerState: Float = 0f, glassContainerState: Float = 0f,
                ledState: ws.LedState = ws.LedState.OFF, trolleyPosition: String = "AT HOME") {

    private val statusGui = mutableMapOf(
        "plasticContainerState" to plasticContainerState,
        "glassContainerState" to glassContainerState,
        "ledState" to ledState,
        "trolleyPosition" to trolleyPosition
    )

    fun toJSON(): JSONObject {
        return JSONObject(this.statusGui)
    }
}