import Coordinate
import SystemConfig
import unibo.comm22.utils.ColorsOut
import utils
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class TestUtilityMethods {

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
        val home = listOf(Coordinate(0, 0))
        val indoor = listOf(Coordinate(0, 4), Coordinate(1, 4), Coordinate(2, 4))
        val glass = listOf(Coordinate(4, 0), Coordinate(5, 0), Coordinate(6, 0))
        val plastic = listOf(Coordinate(6, 2), Coordinate(6, 3), Coordinate(6, 4))

        val listHome = utils.getMapCoord("home")
        val listIndoor = utils.getMapCoord("indoor")
        val listGlass = utils.getMapCoord("glassbox")
        val listPlastic = utils.getMapCoord("plasticbox")

        ColorsOut.out("Checking if coord list have the right number of elements.", ColorsOut.BLUE)

        assert(listHome.count() == home.count())
        assert(listIndoor.count() == indoor.count())
        assert(listGlass.count() == glass.count())
        assert(listPlastic.count() == plastic.count())

        ColorsOut.out("Checking if the coord list contains the right coordinates", ColorsOut.BLUE)

        for (i in 0 until listHome.count()) {
            assert(listHome[i].equals(home[i]))
        }

        for(i in 0 until listIndoor.count()) {
            assert(listIndoor[i].equals(indoor[i]))
        }

        for(i in 0 until listGlass.count()) {
            assert(listGlass[i].equals(glass[i]))
        }

        for (i in 0 until listPlastic.count()) {
            assert(listPlastic[i].equals(plastic[i]))
        }
    }

    @Test
    fun testGetClosestCoord() {
        ColorsOut.out("Checking indoor...", ColorsOut.BLUE)

        assert(utils.getClosestCoordinate(Coordinate(3, 3), "indoor").equals(Coordinate(2, 4)))
        assert(utils.getClosestCoordinate(Coordinate(1, 2), "indoor").equals(Coordinate(1, 4)))

        ColorsOut.out("Checking GlassBox...", ColorsOut.BLUE)

        assert(utils.getClosestCoordinate(Coordinate(3, 2), "glassbox").equals(Coordinate(4, 0)))
        assert(utils.getClosestCoordinate(Coordinate(5, 3), "glassbox").equals(Coordinate(5, 0)))
        assert(utils.getClosestCoordinate(Coordinate(6, 2), "glassbox").equals(Coordinate(6, 0)))

        ColorsOut.out("Checking PlasticBox...", ColorsOut.BLUE)

        assert(utils.getClosestCoordinate(Coordinate(4, 0), "plasticbox").equals(Coordinate(6, 2)))
        assert(utils.getClosestCoordinate(Coordinate(2, 3), "plasticbox").equals(Coordinate(6, 3)))
        assert(utils.getClosestCoordinate(Coordinate(4, 4), "plasticbox").equals(Coordinate(6, 4)))
    }
}