package heartzert.test.all

import org.hamcrest.CoreMatchers.*
import org.junit.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.rules.*
import org.junit.runner.*
import org.junit.runners.*
import org.junit.runners.model.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest : TestRule  {

    //重写测试规则
    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                // evaluate前执行方法相当于@Before
                val methodName = description?.methodName // 获取测试方法的名字
                println(methodName + "测试开始！")

                base?.evaluate()  // 运行的测试方法

                // evaluate后执行方法相当于@After
                println(methodName + "测试结束！")
            }
        }
    }

    //自定义测试规则
    @Rule
    @JvmField
    var rule = this

    @Test
    fun addition_isCorrect() {
        assertEquals(true, null.testNull())
        assertEquals(false, "".testNull())
    }

    private fun String?.testNull(): Boolean {
        return this == null
    }

    @Test
    fun assertThatTest() {
        assertThat("不大对", 1, instanceOf(String::class.java))
        assertThat("不大对2", 1, equalTo(2))
        assertThat("不大对3", "21", startsWith("2"))
        assertThat("不大对4", 1, notNullValue())
        assertThat("不大对5", 1, `is`(2))
        //等等，还有很多方法，可以点进源码查看
    }

    //期望得到一个exception的test方法
    @Test(expected = NullPointerException::class)
    fun methodThrowException() {
        throw NullPointerException()
    }


    //期望得到一个exception的test方法
    @Test(expected = NullPointerException::class)
    fun methodThrowException2() {
        throw IllegalArgumentException()
    }

}

@RunWith(Parameterized::class)
class ParameterizedTest(var i: Int) {

    companion object {

        @JvmStatic
        @Parameterized.Parameters
        fun primeNumbers(): Collection<Int> = arrayListOf(1, 2, 3, 4)

    }

    @Test
    fun test() {
        assertEquals(1, i)
    }
}
