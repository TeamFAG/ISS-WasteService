import it.unibo.kactor.QakContext
import kotlinx.coroutines.runBlocking

class RunTrolley {
    fun main() = runBlocking {
        QakContext.createContexts(
            "localhost", this, "demo_wasteservice.pl", "sysRules.pl", "ctxtrolley"
        )
    }
}