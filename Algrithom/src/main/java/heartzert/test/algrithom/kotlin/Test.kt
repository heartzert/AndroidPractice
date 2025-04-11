package heartzert.test.algrithom.kotlin

/**
 * Created by heartzert on 2024/12/17.
 * Email: heartzert@163.com
 */

fun main() {
}

fun tt() {
    mytest(
        listOf(
            mutableListOf("a", "b", "c"),
            mutableListOf("1", "2", "3"),
            mutableListOf("x", "y", "z")
        )
    )

    mytest(
        listOf(
            mutableListOf("a", "b", "c"),
            mutableListOf("1", "2"),
            mutableListOf("x", "y")
        )
    )

    mytest(
        listOf(
            mutableListOf("a", "b"),
            mutableListOf("x", "y", "z")
        )
    )

    mytest(
        listOf(
            mutableListOf("a", "b"),
            mutableListOf("x", "y")
        )
    )

    mytest(
        listOf(
            mutableListOf("a", "b"),
            mutableListOf("x")
        )
    )

    mytest(
        listOf(
            mutableListOf("a", "b")
        )
    )

    mytest(
        listOf(
            mutableListOf("a")
        )
    )

    mytest(
        listOf(
            mutableListOf("a", "b", "c", "d"),
            mutableListOf(),
            mutableListOf(),
            mutableListOf(),
            mutableListOf("x", "y", "z", "w")
        )
    )
}

fun mytest(totalList: List<MutableList<String>>) {
    val result = mutableListOf<String>()
    for (i in totalList.indices) {
        val list1 = totalList[i]
        if (list1.isEmpty()) {
            continue
        }
        val list2 = totalList.getOrNull(i + 1)

        val combineFirst = list1.removeLastOrNull() ?: ""
        val combineLast = list2?.removeFirstOrNull() ?: ""
        result.addAll(list1)
        result.add(combineFirst + combineLast)
    }
    println(result)
}