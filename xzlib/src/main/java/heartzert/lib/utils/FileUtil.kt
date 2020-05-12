package heartzert.lib.utils

import java.io.File

/**
 * Created by heartzert on 2019/11/15.
 * Email: heartzert@163.com
 */

/**
 * appendText后换行
 *
 * @param string
 */
fun File.appendTextln(string: String) {
    this.appendText(string + "\n")
}
