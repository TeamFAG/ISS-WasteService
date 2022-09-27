package it.unibo.sprint1.test

import org.eclipse.californium.core.CoapHandler
import org.eclipse.californium.core.CoapResponse
import unibo.comm22.coap.CoapConnection
import unibo.comm22.utils.ColorsOut
import unibo.comm22.utils.CommUtils
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class ObserverForTest : CoapHandler {

    class CoapTuple(val hostAddress: String, val resourcePath: String) {}

    private val contextsMap = HashMap<String, Pair<String, Int>>()
    private val actorsMap = HashMap<String, String>()
    private val activeConnections = HashMap<String, CoapConnection>()
    private val coapHistory: MutableList<String> = ArrayList()

    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

    private fun createCoapTuple(actor: String): CoapTuple {
        val context = actorsMap[actor] ?: throw Exception("A")
        val pair = contextsMap[context] ?: throw Exception("A")

        return CoapTuple("${pair.first}:${pair.second}", "actors/$actor")
    }

    fun addContext(name: String, socketAddress: Pair<String, Int>) {
        contextsMap[name] = socketAddress
    }

    fun addActor(actor: String, context: String) {
        actorsMap[actor] = context
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

    fun getCoapHistory(): List<String> {
        lock.withLock {
            return coapHistory
        }
    }

    fun clearCoapHistory() {
        lock.withLock {
            coapHistory.clear()
        }
    }

    override fun onLoad(response: CoapResponse) {
        lock.withLock {
            coapHistory.add(response.responseText)
            condition.signalAll()
        }
    }

    override fun onError() {
        TODO("Not yet implemented")
    }
}