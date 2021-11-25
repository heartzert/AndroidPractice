package heartzert.test.all.kotlinSamples

/**
 * Created by heartzert on 2021/11/18.
 * Email: heartzert@163.com
 */
class InvokeTest {

    //Kotlin 提供了 invoke 约定，可以让对象向函数一样直接调用
    operator fun invoke(block: InvokeTest.() -> Unit) {
        block(this)

        //或者也可以省略this
        block()
    }

    fun test() {
        println("test")
    }
}

fun main() {
    val invokeTest = InvokeTest()

    invokeTest {
        test()
        test()
        test()
    }
}