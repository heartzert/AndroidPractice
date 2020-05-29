package heartzert.test.all.samples.reflaction

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import heartzert.test.all.R

class ReflactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reflaction)
        val b = object : TestReflactionInterface {
            override fun reflaction() {
                Log.d("========", "Reflaction!!!")
            }
        }
        val bklass = b.javaClass
        Log.d("========", "klass")

        val c = TestInnerObject()
        Log.d("========", "c.className=${c.javaClass.name}")
        val d = TestObject()
        Log.d("========", "d.className=${d.javaClass.name}")

        val classLoader = classLoader
        Log.d("========", "classLoader=${classLoader}")
        val cklass = classLoader.loadClass("heartzert.test.all.templates.reflaction.ReflactionActivity\$TestObject")
        Log.d("========", "cklassMethods = ${cklass.declaredMethods}")
        Log.d("========", "cklassMethods = ${cklass.declaredConstructors}")
    }

    interface TestReflactionInterface {
        fun reflaction()
    }

    inner class TestInnerObject() {

        val valInt = 0
        var varInt = 1

        constructor(a: Int? = null) : this()

        private fun privateTestFun(a: Int?, b: Int? = 0) {

        }

        @JvmOverloads
        fun testFun(a: Int?, b: Int? = 0) {

        }
    }

    class TestObject() {

        companion object {
            const val constObjectValInt = 0
            val objectInt = 1
        }
    }
}
