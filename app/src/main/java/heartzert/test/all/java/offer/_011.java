package heartzert.test.all.java.offer;

/**
 * Created by heartzert on 2020/6/18.
 * Email: heartzert@163.com
 */
/*

一只青蛙一次可以跳上1级台阶，也可以跳上2级台阶。求该青蛙跳上一个 n 级的台阶总共有多少种跳法。

答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。

示例 1：

输入：n = 2
输出：2
示例 2：

输入：n = 7
输出：21
提示：

0 <= n <= 100
注意：本题与主站 70 题相同：https://leetcode-cn.com/problems/climbing-stairs/

 */
class _011 {

    /*
    由于可能上1阶，也可能上2阶，所以在n阶台阶时，可能是由n-1上来，也有可能是n-2阶上来。
    所以f(n) = f(n-1) + f(n - 2) 即转换为上一个题目，斐波那契数列
     */
}
