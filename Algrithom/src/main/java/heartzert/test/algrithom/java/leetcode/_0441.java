package heartzert.test.algrithom.java.leetcode;

import android.util.LruCache;

/**
 * Created by heartzert on 2022/12/6.
 * Email: heartzert@163.com
 */
/*
你总共有 n 枚硬币，并计划将它们按阶梯状排列。对于一个由 k 行组成的阶梯，其第 i 行必须正好有 i 枚硬币。阶梯的最后一行 可能 是不完整的。

给你一个数字 n ，计算并返回可形成 完整阶梯行 的总行数。

 

示例 1：


输入：n = 5
输出：2
解释：因为第三行不完整，所以返回 2 。
示例 2：


输入：n = 8
输出：3
解释：因为第四行不完整，所以返回 3 。
 

提示：

1 <= n <= 231 - 1

来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/arranging-coins
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _0441 {

    public static void main(String[] args) {
        System.out.println(_0441.arrangeCoins(8));
    }
    /*
    等差数列求和公式Sn = (X1 + Xn) * n / 2
     */
    public static int arrangeCoins(int n) {
        int left = 0;
        int right = n;
        int mid;
        while (right >= left) {
            // 这里不用（left + right） / 2，防止整数溢出
            mid = left + (right - left) / 2;
            long res = (long) mid * (mid + 1) / 2;
            if (res == n) {
                return mid;
            } else if (res > n){
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left - 1;
    }
}
