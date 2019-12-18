package heartzert.test.all

import org.junit.Test

import org.junit.Assert.*
import java.lang.Exception

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {

        null.testNull()
        "".testNull()
    }

    fun String?.testNull(): Boolean {
        return this == null
    }

    @Test
    fun testThrow() {
        for (x in 0 until 120) {
            val s = "%3S".format(x).replace(" ", "0")
            println("<item android:drawable=\"@drawable/image_loading$s\" android:duration=\"100\"/>")
        }

    }
}
