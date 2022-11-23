package wsLed

import unibo.comm22.utils.ColorsOut
import ws.LedState

object LedUtils {
    var atHome = true
    lateinit var Led: it.unibo.radarSystem22.domain.interfaces.ILed

    fun getLedStatusFromCoap(resource: String, value: String): LedState {

        if(resource.equals("pather") && !atHome) {
            if(value.contains("halt_begin"))
                return LedState.ON
            else if(value.contains("halt_end"))
                return LedState.BLINKING
        } else if(resource.equals("transporttrolley")) {
            if(value.contains("moving") || value.contains("depositDone")) {
                atHome = false
                return LedState.BLINKING
            }
            else if(value.contains("arrived")) {
                if(value.contains("HOME")) {
                    atHome = true
                    return LedState.OFF
                }
                else
                    return LedState.ON
            }
        }

        return LedState.OFF
    }

    fun createLed(simulation: Boolean, ledGui: Boolean) {
        it.unibo.radarSystem22.domain.utils.DomainSystemConfig.simulation = simulation
        it.unibo.radarSystem22.domain.utils.DomainSystemConfig.ledGui = ledGui

        try {
            Led = it.unibo.radarSystem22.domain.DeviceFactory.createLed()
            ColorsOut.outappl(Led.toString(), ColorsOut.ANSI_RED_BACKGROUND)
            Led.turnOff()
        } catch (e: Exception) {
            ColorsOut.outappl("Led gui exception", ColorsOut.RED)
        }
    }

    fun turnOn() {
        try {
            Led.turnOn()
        } catch (e: Exception) {
            ColorsOut.outappl("Led gui exception", ColorsOut.RED)
        }
    }

    fun turnOff() {
        try {
            Led.turnOff()
        } catch (e: Exception) {
            ColorsOut.outappl("Led gui exception", ColorsOut.RED)
        }
    }

    fun printLedState(str: String) {
        ColorsOut.outappl(str, ColorsOut.WHITE_BACKGROUND)
    }
}