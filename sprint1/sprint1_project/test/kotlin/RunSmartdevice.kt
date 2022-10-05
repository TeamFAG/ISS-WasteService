import it.unibo.kactor.QakContext
import kotlinx.coroutines.runBlocking

class RunSmartdevice {
    fun main() = runBlocking {
        QakContext.createContexts(
            "127.0.0.1", this, "demo_wasteservice.pl", "sysRules.pl", "ctxsmartdevice"
        )
    }
}