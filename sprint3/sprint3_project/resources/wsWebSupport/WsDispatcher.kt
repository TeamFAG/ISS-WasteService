package wsWebSupport

import SystemConfig
import it.unibo.kactor.ActorBasic
import unibo.comm22.utils.ColorsOut
import unibo.comm22.ws.WsConnection

object WsDispatcher {

    private lateinit var owner: ActorBasic
    private var port: Int = 0
    private lateinit var hostName: String
    private lateinit var path: String
    private lateinit var connection: WsConnection
    private var traceOn = false

    init {
        ColorsOut.outappl("WsDispatcher | init", ColorsOut.BLUE)
    }

    fun create(owner: ActorBasic, hostName: String, port: String, path: String, trace: Boolean = false) {
        //TODO make create async
        this.owner = owner
        this.traceOn = trace
        this.hostName = hostName
        this.path = path
        this.port = port.toInt()

        try {
            connection = WsConnection.create("$hostName:$port/$path")
            ColorsOut.outappl("WsDispatcher | created connection to $hostName:$port/$path", ColorsOut.GREEN)
            connection.forward("{maxPB: ${SystemConfig.MAXPB}, maxGB: ${SystemConfig.MAXGB}}")
        } catch (e: Exception) {
            ColorsOut.outappl("WsDispatcher | connection error...", ColorsOut.RED)
            e.printStackTrace()
        }
    }

    fun dispatchGuiUpdate(status: GuiStatus) {
        try {
            connection.forward(status.toJSON().toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}