package it.unibo.sprint1.test

import unibo.comm22.interfaces.Interaction2021
import unibo.comm22.tcp.TcpClientSupport
import unibo.comm22.utils.ColorsOut

class ConnTcp(address: String, port: Int) : Interaction2021{

    private var conn: Interaction2021

    init {
        conn = TcpClientSupport.connect(address, port, 10)
        ColorsOut.out("ConnTcp createConnection DONE: $conn", ColorsOut.CYAN)
    }

    override fun forward(msg: String?) {
        try {
            conn.forward(msg)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun request(msg: String?): String {
        forward(msg)
        return receiveMsg()
    }

    fun requestWithoutResponse(msg: String?) {
        forward(msg)
    }

    override fun reply(msg: String?) {
        forward(msg)
    }

    override fun receiveMsg(): String {
        return conn.receiveMsg()
    }

    override fun close() {
        conn.close()
    }
}