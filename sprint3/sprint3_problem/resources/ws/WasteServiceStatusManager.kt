package ws

import unibo.comm22.utils.ColorsOut

object WasteServiceStatusManager {

    var storedPlastic: Float = 0F
    var storedGlass: Float = 0F

    fun checkIfDepositPossible(material: Material, quantity: Float): Boolean {
        if(material.equals(Material.GLASS) && storedGlass + quantity <= SystemConfig.MAXGB)
            return true
        else if(material.equals(Material.PLASTIC) && storedPlastic + quantity <= SystemConfig.MAXPB)
            return true

        return false
    }

    fun updateBox(material: Material, quantity: Float) {
        if(material.equals(Material.PLASTIC))
            storedPlastic += quantity
        else storedGlass += quantity

        ColorsOut.outappl("Plastic: ${storedPlastic}", ColorsOut.YELLOW)
        ColorsOut.outappl("Glass: ${storedGlass}", ColorsOut.YELLOW)
    }

    fun getStoredQuantity(material: Material): Float {
        if(material.equals(Material.PLASTIC))
            return storedPlastic
        else return storedGlass
    }
}