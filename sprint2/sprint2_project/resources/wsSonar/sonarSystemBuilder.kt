package wsSonar

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.sysUtil

object sonarSystemBuilder {

    lateinit var firstActorInPipe: ActorBasic

    fun createSonar(simulation: Boolean, log: Boolean) {
        SystemConfig.setTheConfiguration("SystemConfiguration")
        buildSonarPipe(simulation, log)
    }

    fun buildSonarPipe(simulation: Boolean, log: Boolean) {
        if(simulation)
            firstActorInPipe = sysUtil.getActor("sonaremittersimulator")!!
        else
            firstActorInPipe = sysUtil.getActor("sonaremitterconcrete")!!

        if(log)
            firstActorInPipe
                .subscribeLocalActor("wsdatacleaner")
                .subscribeLocalActor("wsdatalogger")
                .subscribeLocalActor("wsdistancefilter")
        else
            firstActorInPipe
                .subscribeLocalActor("wsdatacleaner")
                .subscribeLocalActor("wsdistancefilter")
    }
}