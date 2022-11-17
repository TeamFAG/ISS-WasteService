package wsSonar

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.IApplMessage
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.delay

class sonarEmitterSimulator(name: String): ActorBasic(name) {

    var max = SystemConfig.sonar["max"] as Int + 10
    var min = SystemConfig.sonar["min"] as Int
    var curr = max
    var down = true

    init{
        println("\tsonarEmitterSimulator | Started.")
    }

    override suspend fun actorBody(msg: IApplMessage) {
        SystemConfig.setTheConfiguration("SystemConfiguration")
        val simulation = SystemConfig.sonar["simulation"] as Boolean
        println("\tsonarEmitterSimulator | simulation: $simulation")

        while(curr >= min && curr <= max && simulation) {
            val d = "distance($curr)"
            val ev = MsgUtil.buildEvent(name, "sonar", d)

            println("$tt $name | $d")

            emitLocalStreamEvent(ev)

            if(curr == min)
                down = false
            if(curr == max)
                down = true

            if(down) curr--
            else curr++

            delay(350)
        }
    }
}