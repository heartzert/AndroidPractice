package heartzert.test.all

import org.junit.Test
import kotlin.math.roundToInt

/**
 * Created by heartzert on 2020/3/9.
 * Email: heartzert@163.com
 */
class Test {

    /**
     * print animation items for xml
     */
    @Test
    fun testThrow() {
        //文件名
        val fileCommonName= "image_loading"
        //文件数
        val totalNum = 120
        //动画总时间
        val totalDuration = 100

        //位数，决定前缀0的个数
        val figuresNum = totalNum.toString().length
        val eachDuration = (totalDuration / totalNum.toFloat()).roundToInt()
        for (x in 0 until totalNum) {
            val s = "%${figuresNum}S".format(x).replace(" ", "0")
            println("<item android:drawable=\"@drawable/$fileCommonName$s\" android:duration=\"$eachDuration\"/>")
        }

    }
}