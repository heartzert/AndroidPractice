package heartzert.test.algrithom.java.leetcode;

/*
https://leetcode.cn/problems/sqrtx/
给你一个非负整数 x ，计算并返回 x 的 算术平方根 。

由于返回类型是整数，结果只保留 整数部分 ，小数部分将被 舍去 。

注意：不允许使用任何内置指数函数和算符，例如 pow(x, 0.5) 或者 x ** 0.5 。

 

示例 1：

输入：x = 4
输出：2
示例 2：

输入：x = 8
输出：2
解释：8 的算术平方根是 2.82842..., 由于返回类型是整数，小数部分将被舍去。
 

提示：

0 <= x <= 231 - 1

来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/sqrtx
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class _0069 {
    public static void main(String[] args) {
        System.out.println(mySqrt2(401));
    }

    public static int mySqrt(int x) {
        if (x == 1) {
            return 1;
        }
        int left = 0;
        int right = x;
        int ans = 0;
        while (left < right) {
            int mid = (right + left) / 2;
            long tmp = (long) mid * mid;
            if (tmp == x) {
                return mid;
            } else if (tmp > x) {
                right = mid;
            } else {
                ans = mid;
                left = mid + 1;
            }
        }
        return ans;
    }


    /*
    牛顿迭代法
     */
    public static int mySqrt2(int x) {
        if (x == 0) return 0;
        double n = x;
        double lastN = 0;
        while ((int) lastN != (int) n) {
            if (x / n == n) {
                return (int) n;
            } else {
                lastN = n;
                n = (x / n + n) / 2;
            }
        }
        return (int) n;
    }

}
