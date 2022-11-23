package wsWebSupport

import it.unibo.kactor.ActorBasic
import unibo.comm22.utils.ColorsOut
import unibo.comm22.ws.WsConnection

interface Bean {
    override fun toString(): String
}

class WSBean: Bean {
    override fun toString(): String {
        TODO("Not yet implemented")
    }
}

class TrolleyBean: Bean {
    override fun toString(): String {
        TODO("Not yet implemented")
    }
}

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
        this.owner = owner
        this.traceOn = trace
        this.hostName = hostName
        this.path = path
        this.port = port.toInt()

        try {
            connection = WsConnection.create("$hostName:$port/$path")
            ColorsOut.outappl("WsDispatcher | created connection to $hostName:$port/$path", ColorsOut.GREEN)
        } catch (e: Exception) {
            ColorsOut.outappl("WsDispatcher | connection error...", ColorsOut.RED)
            e.printStackTrace()
        }
    }

    fun dispatchGuiUpdate(type: String, bean: Bean) {
        if(type.equals("trolley"))
            updateTrolley(bean as TrolleyBean)
        else if(type.equals("wasteservice"))
            updateWS(bean as WSBean)
    }
    
    private fun updateTrolley(bean: TrolleyBean) {
        val msg = bean.toString()
        connection.forward(msg)
    }

    private fun updateWS(bean: WSBean) {
        val msg = bean.toString()
        connection.forward(msg)
    }
}