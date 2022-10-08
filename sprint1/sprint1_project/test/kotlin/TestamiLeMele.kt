import org.junit.BeforeClass
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class TestamiLeMele {

    companion object {
        init {

        }

        @BeforeClass
        @JvmStatic
        fun bc() {
            println("Before class")
        }
    }

    @BeforeTest
    fun up() {
        println("Before test")
    }

    @AfterTest
    fun down() {
        println("After Test")
    }

    @Test
    fun t1() {
        println("t1")
    }

    @Test
    fun t2() {
        println("t2")
    }
}