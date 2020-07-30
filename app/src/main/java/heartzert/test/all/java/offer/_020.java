package heartzert.test.all.java.offer;

import java.util.ArrayList;

/**
 * Created by heartzert on 2020/7/14.
 * Email: heartzert@163.com
 */
/*
请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
例如，字符串"+100"、"5e2"、"-123"、"3.1416"、"0123"都表示数值，
但"12e"、"1a3.14"、"1.2.3"、"+-5"、"-1E-16"及"12e+5.4"都不是。

注意：本题与主站 65 题相同：https://leetcode-cn.com/problems/valid-number/

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/biao-shi-shu-zhi-de-zi-fu-chuan-lcof
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class _020 {
    ArrayList<Character> arrayList = new ArrayList<>();

    _020() {
        arrayList.add('0');
        arrayList.add('1');
        arrayList.add('2');
        arrayList.add('3');
        arrayList.add('4');
        arrayList.add('5');
        arrayList.add('6');
        arrayList.add('7');
        arrayList.add('8');
        arrayList.add('9');
    }

    public static void main(String[] args) {
        _020 a = new _020();
        System.out.println("a".split("\\.")[0]);
        System.out.println("+100" + a.isNumber("+100"));
        System.out.println("-123" + a.isNumber("-123"));
        System.out.println("3.1416" + a.isNumber("3.1416"));
        System.out.println("0123" + a.isNumber("0123"));
        System.out.println("5e2" + a.isNumber("5e2"));
        System.out.println("12e" + a.isNumber("12e"));
        System.out.println("1a3.14" + a.isNumber("1a3.14"));
        System.out.println("1.2.3" + a.isNumber("1.2.3"));
        System.out.println("+-5" + a.isNumber("+-5"));
        System.out.println("-1E-16" + a.isNumber("-1E-16"));
        System.out.println("12e+5.4" + a.isNumber("12e+5.4"));
    }

    /*
    全部都忘记判空了，憨批
     */
    public boolean isNumber(String s) {
        if (s == null || s.isEmpty()) return false;
        String[] s1 = s.split("e");
        if (s1.length > 2) return false;
        if (s1.length == 1) {
            s1 = s.split("E");
        }
        if (s1.length > 2) return false;
        if (s1.length == 2) {
            if (!isRealNumber(s1[1], false)) return false;
        }
        String[] s2 = s1[0].split(".");
        if (s2.length > 2) return false;
        if (s2.length == 2) {
            if (!isRealNumber(s2[1], true)) return false;
        }
        if (isRealNumber(s2[0], false)) return false;
        return true;
    }

    public boolean isNumber2(String s) {
        return false;
    }

    private boolean isRealNumber(String s, Boolean isSmall) {
        if (s.startsWith("+") || s.startsWith("-")) {
            if (isSmall) return false;
            else s = s.substring(1);

        }
        for (char x : s.toCharArray()) {
            if (!arrayList.contains(x)) return false;
        }
        return true;
    }
}
