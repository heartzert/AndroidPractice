package heartzert.test.all.archive

import android.util.Log
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by heartzert on 6/22/21.
 * Email: heartzert@163.com
 */

data class QEDBProject(
    var prj_name: String,
    var create_time: String,
    var no: Int
)

val projectList = mutableListOf(
    QEDBProject("", "2020-06-24 16:01:01", 10),
    QEDBProject("", "2020-06-24 16:02:01", 2),
    QEDBProject("", "2020-06-24 16:03:01", 4),
    QEDBProject("", "2020-06-24 16:04:01", 5),

    QEDBProject("", "2020-06-21 16:01:01",6),
    QEDBProject("2020-06-21_Copy", "2020-06-21 16:01:01",7),
    QEDBProject("2020-06-21_Copy02", "2020-06-21 16:01:01",8),
    QEDBProject("", "2020-06-21 16:01:01",9),
    QEDBProject("", "2020-06-21 16:01:01",1),
    null
)


object DraftRenameUtils {

    /**
     * 获取复制的工程名字
     */
    fun getCopyName(item: QEDBProject): String {
        return try {
            getCopyName(item.prj_name)
        } catch (e: Exception) {
            getNewName(item)
        }
    }

    private fun getCopyName(name: String): String {
        val list = projectList.map { it?.prj_name }
        var tmpName = name
        do {
            if (tmpName.contains("_Copy")) {
                if (tmpName.substringAfterLast("_Copy").isEmpty()) {
                    // 20210622_Copy
                    tmpName += "02"
                } else {
                    // 20210622_Copy02
                    tmpName = tailPlusOne(tmpName)
                }
            } else {
                tmpName += "_Copy"
            }
        } while (list.contains(tmpName))
        return tmpName
    }

    fun checkAndRename(list: List<QEDBProject>) {
        for (x in list) {
            if (x.prj_name.isBlank()) {
                x.prj_name = getNewName(x)
            }
        }

//        projectList.clear()
//        val list = projectList.sortedBy { it.no }.let {
//            it.subList(0, minOf(2, it.size))
//        }

        Log.d("==========wxz", "asdas : ");
    }

    private fun getNewName(item: QEDBProject): String {
        if (!item.prj_name.isNullOrBlank()) return item.prj_name
        return getRealName(getPureDigitalDate(item.create_time))
    }

    /*
    默认新建工程标题，取自当前日期，如20210522

    1）英式的日期：日/月/年；Day/Month/Year

    2）美式的日期：月/日/年；Month/Day/Year  3）公历的日期：年/月/日；

    根据系统时间进行判断具体使用格式；

    当天创建多个工程后，第2个工程命名增加数量单位 如：2021052202（单位无限制）

    如出现复制工程情况，在当前标题后增Copy（全球都使用英文，不区分语言） 如：2021052202_Copy 
    当对copy工程多次进行copy后，当copy次数>2时，增加数量单位 如：2021052202_Copy02 

    yyyymmdd02_xxxx02
    */
    private fun getRealName(name: String): String {
        val list = projectList.map { it?.prj_name }
        return if (list.contains(name)) {
            if (name.length == 8) {
                //20210622
                getRealName(name + "02")
            } else {
//                if (name.contains("_Copy")) {
//                    if (name.substringAfterLast("_Copy").isEmpty()) {
//                        // 20210622_Copy
//                        getRealName(name + "02")
//                    } else {
//                        // 20210622_Copy02
//                        getRealName(tailPlusOne(name))
//                    }
//                } else {
                    // 2021062202
                    getRealName(tailPlusOne(name))
//                }
            }
        } else {
            name
        }
    }

    //尾数加1
    private fun tailPlusOne(name: String): String {
        val number = name.takeLast(2)
        return (number.toInt() + 1).toString().let {
            name.substring(0, name.length - 2) + (if (it.length < 2) "0" else "") + it
        }
    }

    //获取纯数字的日期比如：20210622、06222021
    private fun getPureDigitalDate(createTime: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val originDate =
            DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault())
                .format(sdf.parse(createTime)!!)
        val originArray = originDate.split("/")
        val newDate = StringBuffer()
        //没有0则拼上
        for (x in originArray) {
            if (x.length < 2) {
                newDate.append("0$x")
            } else {
                newDate.append(x)
            }
        }
        return newDate.toString()
    }

}