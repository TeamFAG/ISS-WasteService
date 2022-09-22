package ws

object WasteServiceStatusManager {

    var storedPlastic: Float = 0F
    var storedGlass: Float = 0F

    fun checkIfDepositPossible(material: Material, quantity: Float): Boolean {
        if(material.equals(Material.GLASS) && storedGlass + quantity <= WasteServiceConstants.MAXGB)
            return true
        else if(material.equals(Material.PLASTIC) && storedPlastic + quantity <= WasteServiceConstants.MAXPB)
            return true

        return false
    }

    fun updateBox(material: Material, quantity: Float) {
        if(material.equals(Material.PLASTIC))
            storedPlastic += quantity
        else storedGlass += quantity
    }
}