import org.json.JSONObject
import org.json.JSONTokener
import unibo.comm22.utils.ColorsOut
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileReader

object SystemConfig {

    // solamente inizializzazione per la variabile, tutti i cambiamenti nel json modificano questa mappa
    var positions = mutableMapOf(
        "home" to listOf(listOf(0, 0)),
        "indoor" to listOf(listOf(0, 4), listOf(1, 4), listOf(2, 4)),
        "glassbox" to listOf(listOf(4, 0), listOf(5, 0), listOf(6, 0)),
        "plasticbox" to listOf(listOf(6, 2), listOf(6, 3), listOf(6, 4))
    )

    var MAXPB = 100
    var MAXGB = 100

    var sonar = mutableMapOf(
        "simulation" to false,
        "log" to false,
        "min" to 0,
        "max" to 50,
        "dlimit" to 25
    )

    var guiEP = mutableMapOf(
        "host" to "localhost",
        "port" to "8085",
        "path" to "socket"
    )

    fun setTheConfiguration(fileName: String) {
        var file = "${fileName}.json"

        try {
            ColorsOut.out("setTheConfiguration from file: $file")
            val fileReader = FileReader(file)

            val tokener = JSONTokener(fileReader)
            val objects = JSONObject(tokener)
            val values = objects.toMap()

            positions = values.get("positions") as MutableMap<String, List<List<Int>>>
            MAXPB = values.get("MAXPB") as Int
            MAXGB = values.get("MAXGB") as Int
            sonar = values.get("sonar") as MutableMap<String, Any>
            guiEP = values.get("guiEP") as MutableMap<String, String>
        } catch (e: FileNotFoundException) {
            ColorsOut.outerr("setTheConfiguration ERROR " + e.message)
        }
    }
}