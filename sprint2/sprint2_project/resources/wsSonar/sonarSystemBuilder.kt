package wsSonar

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.sysUtil

object sonarSystemBuilder {

    lateinit var firstActorInPipe: ActorBasic

    fun createSonar(simulation: Boolean=true, log: Boolean=false) {
        SystemConfig.setTheConfiguration("SystemConfiguration")
        buildSonarPipe(simulation, log)
    }

    fun buildSonarPipe(simulation: Boolean, log: Boolean) {
        if(simulation)
            firstActorInPipe = sysUtil.getActor("sonaremittersimulator")!!
        else
            firstActorInPipe = sysUtil.getActor("REALSONARRRRR")!!

        firstActorInPipe
            .subscribeLocalActor("wsdatacleaner")
            .subscribeLocalActor("wsdistancefilter")
    }
}