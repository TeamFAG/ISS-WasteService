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
        "plasticbox" to listOf(listOf(6, 3), listOf(6, 4), listOf(6, 5))
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
        } catch (e: FileNotFoundException) {
            ColorsOut.outerr("setTheConfiguration ERROR " + e.message)
        }
    }
}