package heartzert.test.algrithom.java.offer;

import java.util.HashMap;

/**
 * Created by heartzert on 2020/6/18.
 * Email: heartzert@163.com
 */
/*
写一个函数，输入 n ，求斐波那契（Fibonacci）数列的第 n 项。斐波那契数列的定义如下：

F(0) = 0,   F(1) = 1
F(N) = F(N - 1) + F(N - 2), 其中 N > 1.
斐波那契数列由 0 和 1 开始，之后的斐波那契数就是由之前的两数相加而得出。

答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。

 

示例 1：

输入：n = 2
输出：1
示例 2：

输入：n = 5
输出：5
 

提示：

0 <= n <= 100
注意：本题与主站 509 题相同：https://leetcode-cn.com/problems/fibonacci-number/

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/fei-bo-na-qi-shu-lie-lcof
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _010 {

    HashMap<Integer, Long> map = new HashMap<>();

    public static void main(String[] args) {
        _010 a = new _010();
        System.out.println(a.fib(300));
        System.out.println(a.fib2(300));
    }

    /*
    自己的想法，因为之前做过一次，所以利用了存值递归计算
    时间复杂度O(n),因为每一个n只算一遍
    空间复杂度O(n),几乎每一个n都会存起来
     */
    public int fib(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        Long result = map.get(n);
        if (result != null) {
            return (int) (result % 1000000007L);
        }
        int value = fib(n - 1) + fib(n - 2);
        map.put(n, (long) value);
        return value % 1000000007;
    }

    /*
    尝试一下非递归
    时间复杂度O(n)，循环了n-1次
    空间复杂度O(1),只有临时变量
     */
    public int fib2(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int a = 0;
        int b = 1;
        int sum = 1;
        //由于是从0开始，所以要循环n-1次
        for (int i = 1; i <= n - 1; i++) {
           sum = (a+b) % 1000000007;
           a = b;
           b = sum;
        }
        return sum;
    }
}
