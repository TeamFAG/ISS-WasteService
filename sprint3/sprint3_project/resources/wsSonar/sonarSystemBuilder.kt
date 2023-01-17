package wsSonar

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.sysUtil
import unibo.comm22.utils.ColorsOut

object sonarSystemBuilder {

    lateinit var firstActorInPipe: ActorBasic

    fun createSonar(simulation: Boolean, log: Boolean) {
        SystemConfig.setTheConfiguration("SystemConfiguration")

        ColorsOut.outappl("sonarSystemBuilder | create the sonar, simulation is $simulation", ColorsOut.YELLOW)

        buildSonarPipe(simulation, log)
    }

    private fun buildSonarPipe(simulation: Boolean, log: Boolean) {
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