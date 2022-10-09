import org.eclipse.californium.core.CoapHandler
import org.eclipse.californium.core.CoapResponse
import unibo.comm22.coap.CoapConnection
import unibo.comm22.utils.ColorsOut
import unibo.comm22.utils.CommUtils
import ws.WasteServiceStatusManager
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class CoapObserver : CoapHandler {

    class CoapTuple(val hostAddress: String, val resourcePath: String) {}

    private val contextsMap = HashMap<String, Pair<String, Int>>()
    private val actorsMap = HashMap<String, String>()
    private val activeConnections = HashMap<String, CoapConnection>()
    private val coapHistory: MutableList<String> = ArrayList()

    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

    private fun createCoapTuple(actor: String): CoapTuple {
        val context = actorsMap[actor] ?: throw Exception("Error")
        val pair = contextsMap[context] ?: throw Exception("Error")

        println(actorsMap[actor] + "/" + actor)

        return CoapTuple("${pair.first}:${pair.second}", actorsMap[actor] + "/" + actor)
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

    fun checkIfHystoryContains(entry: String): Boolean {
        lock.withLock {
            return coapHistory.contains(entry)
        }
    }

    fun checkIfHystoryContainsOrdered(list: List<String>): Boolean {
        var s1 = ""
        var s2 = ""

        for (e in list)
            s1 += e

        lock.withLock {
            for(e in coapHistory)
                s2 += e
        }

        return s2.contains(s1)
    }

    fun waitForSpecificHistoryEntry(entry: String) {
        ColorsOut.outappl("Waiting for $entry", ColorsOut.BLUE)
        var found = false
        lateinit var history: List<String>

        while (!found) {
            CommUtils.delay(300)

            if(getCoapHistory().contains(entry))
                found = true
        }
    }

    fun clearCoapHistory() {
        lock.withLock {
            coapHistory.clear()
        }
    }

    override fun onLoad(response: CoapResponse) {
        lock.withLock {
            ColorsOut.out(response.responseText, ColorsOut.ANSI_YELLOW)
            coapHistory.add(response.responseText)
            condition.signalAll()
        }
    }

    override fun onError() {
        TODO("Not yet implemented")
    }
}