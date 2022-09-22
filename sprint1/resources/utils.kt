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
    fun getCoordinate(location: String): Coordinate {
        return getMapCoord(location).get(0)
    }
}