package heartzert.test.algrithom.java.leetcode;

/**
 * Created by heartzert on 2022/12/5.
 * Email: heartzert@163.com
 */
/*
https://leetcode.cn/problems/maximum-product-of-three-numbers/

给你一个整型数组 nums ，在数组中找出由三个数组成的最大乘积，并输出这个乘积。

 

示例 1：

输入：nums = [1,2,3]
输出：6
示例 2：

输入：nums = [1,2,3,4]
输出：24
示例 3：

输入：nums = [-1,-2,-3]
输出：-6
 

提示：

3 <= nums.length <= 104
-1000 <= nums[i] <= 1000

来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/maximum-product-of-three-numbers
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _0628 {

    public int maximumProduct(int[] nums) {
        int max1 = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        int max3 = Integer.MIN_VALUE;
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        if (nums.length < 3) return 0;
        for (int num : nums) {
            if (num < min1) {
                min2 = min1;
                min1 = num;
            } else if (num < min2) {
                min2 = num;
            }
            if (num > max1) {
                max3 = max2;
                max2 = max1;
                max1 = num;
            } else if (num > max2) {
                max3 = max2;
                max2 = num;
            } else if (num > max3) {
                max3 = num;
            }
        }
        return Math.max(max1 * max2 * max3, min1 * min2 * max1);
    }
}
