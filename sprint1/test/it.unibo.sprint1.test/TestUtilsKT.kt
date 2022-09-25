package it.unibo.sprint1.test

import Coordinate
import SystemConfig
import unibo.comm22.utils.ColorsOut
import utils
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertSame

class TestUtilsKT {

    @BeforeTest
    fun up() {
        ColorsOut.out("Loading config file...", ColorsOut.BLUE)
        SystemConfig.setTheConfiguration("SystemConfiguration")
    }

    @AfterTest
    fun down() {

    }

    @Test
    fun testGetMapCoord() {
        var home = listOf(Coordinate(0, 0))

        val listHome = utils.getMapCoord("home")
        var listIndoor = utils.getMapCoord("indoor")
        var listGlass = utils.getMapCoord("glassbox")
        var listPlastic = utils.getMapCoord("plasticbox")


    }

    @Test
    fun testGetClosestCoord() {

    }
}