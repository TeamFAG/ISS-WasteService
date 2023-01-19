package it.unibo.WasteserviceStatusManager.utils

import org.json.JSONObject
import org.json.JSONTokener
import unibo.comm22.utils.ColorsOut
import java.io.FileNotFoundException
import java.io.FileReader

object SystemConfiguration {

    var actors = mutableMapOf(
        "wasteservice" to "wasteservice",
        "led" to "led",
        "trolleystateobserver" to "trolleystateobserver",
        "transporttrolley" to "transporttrolley",
    )

    var contexts = mutableMapOf(
        "wasteservice" to "ctwasteservice_test",
        "led" to "ctxwasteservice_test",
        "trolleystateobserver" to "ctxwasteservice_test",
        "transporttrolley" to "ctxwasteservice_test",
    )

    var hosts = mutableMapOf(
        "wasteservice" to "localhost",
        "led" to "localhost",
        "trolleystateobserver" to "localhost",
        "transporttrolley" to "localhost",
    )

    var ports = mutableMapOf(
        "wasteservice" to 8050,
        "led" to 8050,
        "trolleystateobserver" to 8050,
        "transporttrolley" to 8050,
    )

    var activeObserver = mutableMapOf(
        "wasteservice" to true,
        "led" to true,
        "trolleystate" to true,
        "trolleyposition" to true,
    )

    public fun setTheConfiguration(fileName: String) {
        var file = "${fileName}.json"

        try {
            ColorsOut.outappl("setting the configuration from file: $file", ColorsOut.BLUE)

            val fileReader = FileReader(file)
            val tokener = JSONTokener(fileReader)
            val objects = JSONObject(tokener)
            val values = objects.toMap()

            actors = values.get("actors") as MutableMap<String, String>
            contexts = values.get("contexts") as MutableMap<String, String>
            hosts = values.get("hosts") as MutableMap<String, String>
            ports = values.get("ports") as MutableMap<String, Int>
            activeObserver = values.get("activeObserver") as MutableMap<String, Boolean>
        } catch (e: FileNotFoundException) {
            ColorsOut.outerr("setTheConfiguration ERROR ${e.message}")
        }
    }
}