package it.unibo.sprint1.test

import org.eclipse.californium.core.CoapHandler
import org.eclipse.californium.core.CoapResponse
import unibo.comm22.coap.CoapConnection
import unibo.comm22.utils.ColorsOut
import unibo.comm22.utils.CommUtils

class ObserverForTest : CoapHandler {

    class CoapTuple(val hostAddress: String, val resourcePath: String) {}

    private val contextsMap = HashMap<String, Pair<String, Int>>()
    private val actorsMap = HashMap<String, String>()
    private val activeConnections = HashMap<String, CoapConnection>()

    fun addContext(name: String, socketAddress: Pair<String, Int>) {
        contextsMap[name] = socketAddress
    }

    fun addActor(actor: String, context: String) {
        actorsMap[actor] = context
    }

    private fun createCoapTuple(actor: String): CoapTuple {
        val context = actorsMap[actor] ?: throw Exception("A")
        val pair = contextsMap[context] ?: throw Exception("A")

        return CoapTuple("${pair.first}:${pair.second}", "actors/$actor")
    }

    fun createCoapConnection(actor: String) {
        ColorsOut.out("Creating connection to $actor Actor", ColorsOut.BLUE)
        val coapTuple = createCoapTuple(actor)
        val connection = CoapConnection(coapTuple.hostAddress, coapTuple.resourcePath)

        connection.observeResource(this)

        while (connection.request("") == "0") {
            ColorsOut.out("Wating for connection to $actor", ColorsOut.BLUE)
            CommUtils.delay(100)
        }

        activeConnections[actor] = connection
        ColorsOut.out("Created connection to $actor, $connection", ColorsOut.BLUE)
    }

    fun closeCoapConnection(actor: String) {
        val connection = activeConnections[actor] ?: throw Exception("Connection to $actor was already closed...")
        connection.close()
    }

    fun closeAllCoapConnections() {
        for(connEntry in activeConnections) {
            ColorsOut.out("Clonsing connection to ${connEntry.key}...")
            connEntry.value.close()
        }

        ColorsOut.out("Closed all connections.")
    }

    override fun onLoad(response: CoapResponse?) {
        TODO("Not yet implemented")
    }

    override fun onError() {
        TODO("Not yet implemented")
    }
}