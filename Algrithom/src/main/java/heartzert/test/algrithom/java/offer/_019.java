package heartzert.test.algrithom.java.offer;

/**
 * Created by heartzert on 2020/6/30.
 * Email: heartzert@163.com
 */
/*
困难难度！
请实现一个函数用来匹配包含'. '和'*'的正则表达式。
模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（含0次）。
在本题中，匹配是指字符串的所有字符匹配整个模式。
例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但与"aa.a"和"ab*a"均不匹配。

示例 1:

输入:
s = "aa"
p = "a"
输出: false
解释: "a" 无法匹配 "aa" 整个字符串。
示例 2:

输入:
s = "aa"
p = "a*"
输出: true
解释: 因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。因此，字符串 "aa" 可被视为 'a' 重复了一次。
示例 3:

输入:
s = "ab"
p = ".*"
输出: true
解释: ".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。
示例 4:

输入:
s = "aab"
p = "c*a*b"
输出: true
解释: 因为 '*' 表示零个或多个，这里 'c' 为 0 个, 'a' 被重复一次。因此可以匹配字符串 "aab"。
示例 5:

输入:
s = "mississippi"
p = "mis*is*p*."
输出: false
s 可能为空，且只包含从 a-z 的小写字母。
p 可能为空，且只包含从 a-z 的小写字母以及字符 . 和 *，无连续的 '*'。
注意：本题与主站 10 题相同：https://leetcode-cn.com/problems/regular-expression-matching/

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/zheng-ze-biao-da-shi-pi-pei-lcof
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _019 {

    /*
    测试用例：
    s = "aa"
    p = "a"
    输出: false

    s = "aa"
    p = "a*"
    输出: true

    s = "ab"
    p = ".*"
    输出: true
·
    s = "aab"
    p = "c*a*b"
    输出: true

    s = "mississippi"
    p = "mis*is*p*."
    输出: false

    s="aa"
    p="aa."
    输出：false

    s=""
    p=".*.*.*.*.*.*.*"
    输出：true


    emmmmm
    s="aaa"
    p="a*a"

    s="aaba"
    p=".*a"
    p=".*ba"

    这种没有考虑到。。。。。。。

    参考了一下答案，为什么从后往前判断这种就可以成功？？
    从后往前到底有什么魔力

    s="aa"
    p="aa*a"
    这个从后往前貌似也不行，见鬼
     */
    public static void main(String[] args) {
        _019 a = new _019();
        System.out.println(a.isMatch("aa", "a"));//f
        System.out.println(a.isMatch("aa", "a*"));//t
        System.out.println(a.isMatch("ab", ".*"));//t
        System.out.println(a.isMatch("aab", "c*a*b"));//t
        System.out.println(a.isMatch("mississippi", "mis*is*p*."));//f
        System.out.println(a.isMatch("aa", "aa."));//f
        System.out.println(a.isMatch("", ".*.*.*.*.*.*.*"));//t
        System.out.println(a.isMatch("aaa", "a*a"));//t
        System.out.println(a.isMatch("aaba", ".*a"));//t
        System.out.println(a.isMatch("aaba", ".*ba"));//t
        System.out.println(a.isMatch("aa", "aa*a"));//t
    }

    /*
    解释一下我的思路：
    用两个指针分别指向原字符串与模式串，并且将(x*)看做一个字符，x可以为[a-z]和.
    当指针指向的字符相同时，若模式串中为单字符，则两个指针均后移一位；若模式串是*组合，那么只移动原字符串指针。
    当指针指向的字符不同时，若模式串中为单字符，直接返回错误；若模式串是*组合，则移动模式串指针。

    此思路的错误点：当遇到*组合时，可以使用*组合匹配，也可以直接舍弃*组合，匹配下一个字符。
    这两条路任意一个成功，都算是匹配成功，而我的思路中缺少了直接舍弃*组合这一条路。
     */
    public boolean isMatch(String s, String p) {
        int i = s.length() - 1, j = p.length() - 1;
        while (i >= 0 && j >= 0) {
            boolean beforeStar = p.charAt(j) == '*';
            if (beforeStar) j--;
            boolean isPoint = p.charAt(j) == '.';
            if (s.charAt(i) == p.charAt(j) || isPoint) {
                i--;
                if (beforeStar) {
                    j++;
                } else {
                    j--;
                }
            } else {
                if (beforeStar) {
                    j--;
                } else {
                    return false;
                }
            }
        }
        if (i <0 && j < 0) {
            return true;
        }
        if (i < 0) {
            //如果剩余p串是奇数，一定不匹配
            if ((j & 1) == 0) {
                return false;
            }

            while (j >= 0) {
                if (p.charAt(j) == '*') {
                    j -= 2;
                } else {
                    break;
                }
            }
            return j < 0;
        }
        return false;
    }

    /*
    根据上面提出的问题，重来
     */
//    public boolean isMatch2(String s, String p) {
//        if (p.)
//    }
//
//    private boolean match(String s, String p, int i, int j) {
//
//    }
}
