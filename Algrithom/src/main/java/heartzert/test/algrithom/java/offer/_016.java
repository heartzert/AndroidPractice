package heartzert.test.algrithom.java.offer;

import java.util.logging.XMLFormatter;

/**
 * Created by heartzert on 2020/6/28.
 * Email: heartzert@163.com
 */
/*
实现函数double Power(double base, int exponent)，求base的exponent次方。不得使用库函数，同时不需要考虑大数问题。

 

示例 1:

输入: 2.00000, 10
输出: 1024.00000
示例 2:

输入: 2.10000, 3
输出: 9.26100
示例 3:

输入: 2.00000, -2
输出: 0.25000
解释: 2-2 = 1/22 = 1/4 = 0.25
 

说明:

-100.0 < x < 100.0
n 是 32 位有符号整数，其数值范围是 [−2^31, 2^31 − 1] 。
注意：本题与主站 50 题相同：https://leetcode-cn.com/problems/powx-n/

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/shu-zhi-de-zheng-shu-ci-fang-lcof
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _016 {

    public static void main(String[] args) {
        _016 a = new _016();
        double x = 2.0;
        int n = 10;

        System.out.println(a.myPow(x, n));
        System.out.println(a.myPow2(x, n));
        System.out.println(a.myPow3(x, n));
        System.out.println(Math.pow(x, n));
    }

    /*
    只能想到这种方法，但是看到题目n的范围，这个方法肯定是不对的。

    其实看到n那么大、2^31次方，就应该想到把n转换为二进制来处理了。

    起码前面==0和<0的地方思路对了。（doge）
     */
    public double myPow(double x, int n) {
        if (n == 0) {
            return 1;
        }
        long m = n;
        if (m < 0) {
            //这里有取反操作，而题目中范围内，n最小值取反后会超出int最大值，所以将n存到long中处理。
            //仔细看好题目边界条件！
            m = -m;
            x = 1 / x;
        }
        double sum = 1;
        for (int i = 1; i <= m; i++) {
            sum *= x;
        }
        return sum;
    }

    /*
    二进制法。
    将n表示为2进制即x^m=x^(2^0a) * x^(2^1b) * x^(2^2c) * .... * x^(2^31d)
    其中a...d表示n的二级制表示在该位的值。

    时间复杂度：O(logn)
     */
    public double myPow2(double x, int n) {
        if (n == 0) {
            return 1;
        }
        long m = n;
        if (m < 0) {
            m = -m;
            x = 1 / x;
        }
        double sum = 1;
        while (m != 0) {
            if ((m & 1) == 1) {
                //这里如果x是整数，可以用一个标志位i做左移操作。
                sum *= x;
            }
            m = m >>> 1;
            //由于double不能用左移，题目也不让用库函数，所以只能在每次循环内计算x^2i
            x *= x;
        }
        return sum;
    }

    /*
    x^m = (x^2)^(m/2) n为偶数
    x^m = x(x^2)^(m/2) n为奇数

    啊哈写完代码发现跟上面那个思路写出来一模一样，而我觉得上面那个思路比较好理解
     */
    public double myPow3(double x, int n) {
        if (n == 0) {
            return 1;
        }
        long m = n;
        if (m < 0) {
            m = -m;
            x = 1 / x;
        }
        double res = 1;
        while (m != 0) {
            //如果是奇数，则需要多乘一个x^(2^i)
            //10^7 = (10^2)^3 * 10 = (10^4)^1 * 10 * 10^2
            //此时x = 10^4 = 10^(2^2) ; res = 10 * 10^2
            if ((m & 1) == 1) {
                res *= x;
            }
            x *= x;
            m = m >>> 1;
        }
        //最后把x与多乘的x相乘即是最后的答案。
        return res;
    }
}
