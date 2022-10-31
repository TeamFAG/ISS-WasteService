import ws.Material
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.io.ObjectInputStream
import java.util.*

object utils {

    @JvmStatic
    fun getMapCoord(location: String): List<Coordinate> {
        var res = mutableListOf<Coordinate>()
        val positions: List<List<Int>>? = SystemConfig.positions.get(location.lowercase()) as List<List<Int>>?

        if (positions != null) {
            for(entry in positions) {
                res.add(Coordinate(entry.get(0), entry.get(1)))
            }
        }

        return res
    }

    @JvmStatic
    fun getClosestCoordinate(currPos: Coordinate, location: String): Coordinate {
        val list = getMapCoord(location)
        var best: Coordinate = list[0]

        if (list.size == 1)
            best = list[0]
        else {
            var bestDist = 1000
            for (coord in list){
                val dist = (kotlin.math.abs(currPos.x - coord.x) + kotlin.math.abs(currPos.y - coord.y))
                if (dist < bestDist) {
                    bestDist = dist
                    best = coord
                }
            }
        }
        return best
    }

    @JvmStatic
    fun getLocationFromMaterialType(material: Material): String {
        if(material.equals(Material.GLASS)) return "GLASSBOX"
        else return "PLASTICBOX"
    }
}