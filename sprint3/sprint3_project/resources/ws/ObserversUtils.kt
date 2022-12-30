package ws

import unibo.comm22.utils.ColorsOut

object ObserversUtils {
    var atHome = true

    fun getLedStatusFromCoapUpdate(resource: String, value: String): LedState {

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

    fun getTrolleyStateFromCoapUpdate(resource: String, value: String): String {
        var res = ""

        if(resource.equals("pather")) {
            res = when(value) {
                "pather(halt_begin)" -> "STOPPED"
                "pather(halt_end)" -> "MOVING"
                else -> ""
            }
        } else if(resource.equals("transporttrolley")) {
            /*
            res = with(value) {
                when {
                    contains("transporttrolley(handleDepositRequest)") -> "Moving to INDOOR"
                    contains("transporttrolley(arrived_INDOOR)") -> "INDOOR Pickup"
                    contains("transporttrolley(pickupError)") -> "INDOOR Pickup FAIL"
                    contains("transporttrolley(depositing)") -> "Deposit"
                    contains("transporttrolley(depositDone)") -> "Moving to HOME"
                    contains("transporttrolley(arrived_HOME)") -> "HOME Idle"
                    contains("transporttrolley(arrived_") -> value.split("_")[1].replace(")", "")
                    contains("transporttrolley(moving_") -> "Moving to ${value.split("_")[1].replace(")", "")}"
                    else -> ""
                }
            }
            */
            res = with(value) {
                when {
                    contains("transporttrolley(arrived_INDOOR)") -> "PICKUP"
                    contains("transporttrolley(depositing)") -> "DEPOSIT"
                    contains("transporttrolley(arrived_") -> "IDLE"
                    contains("transporttrolley(moving_") -> "MOVING"
                    else -> ""
                }
            }
        }

        return  res
    }

    fun getContainersStateFromCoapUpdate(resource: String, value: String): Pair<String, Float> {
        ColorsOut.outappl("CONTAINERRRRRRRRRRRRRRRRRRRR: $value", ColorsOut.BgCyan)

        var material = ""
        var quantity = 0F


        if(value.contains("wasteservice(Plastic:")) {
            material = "PLASTIC"
        } else if(value.contains("wasteservice(Glass:")) {
            material = "GLASS"
        }
        ColorsOut.outappl("CONTAINERRRRRRRRRRRRRRRRRRRR: $material", ColorsOut.BgCyan)

        if(value.contains("wasteservice(Plastic:") || value.contains("wasteservice(Glass:")) {
            quantity = value.split(" ")[1].replace(")", "").toFloat()
        }

        return material to quantity
    }
}