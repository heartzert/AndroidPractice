package heartzert.test.all.common

/**
 * Created by heartzert on 8/31/21.
 * Email: heartzert@163.com
 */
class DataSource {

    fun getData(): List<MData> {
        return (1..100).map {
            MData(it, "name${it}", "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic34.nipic.com%2F20131015%2F2531170_234846010000_2.jpg&refer=http%3A%2F%2Fpic34.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1632970749&t=521a3ad02e5d38d40e66a3f3ea190a12")
        }
    }
}

data class MData(
    val id: Int,
    val name: String,
    val image: String,
)