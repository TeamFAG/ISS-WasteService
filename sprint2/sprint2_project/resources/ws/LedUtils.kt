package ws

import kotlinx.coroutines.delay
import unibo.comm22.utils.ColorsOut

object LedUtils {

    fun getLedStatusFromCoap(resource: String, value: String): LedState {

        if(resource.equals("pather")) {
            if(value.contains("halt_begin"))
                return LedState.ON
            else if(value.contains("halt_end"))
                return LedState.BLINKING
        } else if(resource.equals("transporttrolley")) {
            if(value.contains("moving") || value.contains("depositDone"))
                return LedState.BLINKING
            else if(value.contains("arrived")) {
                if(value.contains("HOME"))
                    return LedState.OFF
                else
                    return LedState.ON
            }
        }

        return LedState.OFF
    }

    fun printLedState(str: String) {
        ColorsOut.outappl(str, ColorsOut.WHITE_BACKGROUND)
    }
}