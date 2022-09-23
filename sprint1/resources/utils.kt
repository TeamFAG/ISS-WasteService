import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.io.ObjectInputStream
import java.util.*

object utils {

    var configurationFile: String = ""

    @JvmName("setConfigurationFile1")
    fun setConfigurationFile(fileName: String) {
        // val inps = ObjectInputStream(FileInputStream("${fname}.bin"))
        configurationFile = "${fileName}.properties"
    }

    @JvmStatic
    fun getMapCoord(location: String): List<Coordinate> {
        var prop = Properties()
        var res: MutableList<Coordinate> = mutableListOf<Coordinate>()

        try {
            var input: InputStream = FileInputStream(configurationFile)
            prop.load(input)
            println(location)
            var str = prop.getProperty(location.uppercase())

            if(str.length > 3) {
                res.add(Coordinate(str.split(" ")[0].toInt(), str.split(" ")[1].toInt()))
                res.add(Coordinate(str.split(" ")[2].toInt(), str.split(" ")[3].toInt()))
                res.add(Coordinate(str.split(" ")[4].toInt(), str.split(" ")[5].toInt()))
            } else
                res.add(Coordinate(str.split(" ")[0].toInt(), str.split(" ")[1].toInt()))

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return res
    }

    @JvmStatic
    fun getClosestCoordinate(currPos: Coordinate, location: String): Coordinate {
        var list = getMapCoord(location)
        var best: Coordinate = list[0]

        if (list.size == 1)
            best = list[0]
        else {
            var bestDist = 1000
            for (coord in list){
                var dist = (kotlin.math.abs(currPos.x - coord.x) + kotlin.math.abs(currPos.y - coord.y))
                if (dist < bestDist) {
                    bestDist = dist
                    best = coord
                }
            }
        }
        return best
    }

}