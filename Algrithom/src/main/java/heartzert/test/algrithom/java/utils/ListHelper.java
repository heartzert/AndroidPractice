package heartzert.test.algrithom.java.utils;

import java.util.List;

/**
 * Created by heartzert on 2022/12/8.
 * Email: heartzert@163.com
 */
public class ListHelper {
    public static <T> void printList(List<T> list) {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (T i : list) {
            stringBuilder.append(i).append(", ");
        }
        stringBuilder.append("]");
        System.out.println(stringBuilder.toString());
    }
}
