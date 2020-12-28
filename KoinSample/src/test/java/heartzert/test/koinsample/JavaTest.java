package heartzert.test.koinsample;

import org.junit.Test;

/**
 * Created by heartzert on 2020/12/17.
 * Email: heartzert@163.com
 */
class JavaTest {

    @Test
    public static void main(String[] args) {
        String s = null;
        if (s == null) {
            System.out.println("String is null");
        }

        Boolean b = null;
        if (b != null && b) {
            System.out.println("Boolean is null");
        }

        Integer i = null;
        if (i != null && i > 30) {
            System.out.println("Integer is null");
        }
    }
}
