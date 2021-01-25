package heartzert.test.all

import android.net.Uri
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

    @Test
    fun testUri() {
        val uri = Uri.parse("https://media4.giphy.com/media/3oEjHGr1Fhz0kyv8Ig/giphy-preview.gif?cid=9f0f6425b7fd2eb0c1c8a3c89ae84af6efd11ff4183c110c&rid=giphy-preview.gif")
        print(uri.path)
    }
}