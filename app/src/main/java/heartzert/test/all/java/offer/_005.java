package heartzert.test.all.java.offer;

/**
 * Created by heartzert on 2020/5/22.
 * Email: heartzert@163.com
 */
/*

请实现一个函数，把字符串 s 中的每个空格替换成"%20"。

示例 1：

输入：s = "We are happy."
输出："We%20are%20happy."


限制：

0 <= s 的长度 <= 10000
 */
public class _005 {

    public static void main(String[] args) {
        System.out.println(execute("asda asdasd asd as das da sd    s s s "));
        System.out.println(execute2("asda asdasd asd as das da sd    s s s "));
    }

    //????????????
    static private String execute(String s) {
        return s.replace(" ", "%20");
    }

    //其实就是要求自己实现replace函数
    static private String execute2(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                sb.append('%');
                sb.append('2');
                sb.append('0');
            } else {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }
}
