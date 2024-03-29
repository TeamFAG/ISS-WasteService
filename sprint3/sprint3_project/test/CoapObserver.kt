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
    private var coapHistory: MutableList<String> = ArrayList()

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

    fun removeHistoryEntry(entry: String) {
        lock.withLock {
            coapHistory.remove(coapHistory.find { en -> en.contains(entry) })
        }
    }

    fun removeHistoryInterval(from: Int, to: Int) {
        lock.withLock {
            var tempList = mutableListOf<String>()

            for(i in 0 until coapHistory.count()) {
                if(i in from..to)
                    continue
                else
                    tempList.add(coapHistory[i])
            }

            coapHistory = tempList
        }
    }

    fun filterDefinitelyHistory(entry: String) {
        lock.withLock {
            coapHistory = coapHistory.filter { e -> e.contains("led") } as MutableList<String>
            coapHistory.forEach { e -> e.replace("\t", "") }
        }
    }

    fun getHistoryIndex(entry: String): Int {
        lock.withLock {
            return coapHistory.indexOf(entry)
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

    fun waitForSpecificHistoryEntry(entry: String): String {
        ColorsOut.outappl("Waiting for $entry", ColorsOut.BLUE)
        var found = false
        var res = ""

        while (!found) {
            CommUtils.delay(300)

            for(e in getCoapHistory()) {
                if(e.contains(entry)) {
                    found = true
                    res = e
                    break
                }
            }
        }

        return res
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