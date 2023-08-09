package heartzert.test.algrithom.java.offer;


import heartzert.lib.utils.tools.PrintUtil;

/**
 * Created by heartzert on 2020/6/29.
 * Email: heartzert@163.com
 */
/*
输入数字 n，按顺序打印出从 1 到最大的 n 位十进制数。比如输入 3，则打印出 1、2、3 一直到最大的 3 位数 999。

示例 1:

输入: n = 1
输出: [1,2,3,4,5,6,7,8,9]
 

说明：

用返回一个整数列表来代替打印
n 为正整数

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/da-yin-cong-1dao-zui-da-de-nwei-shu-lcof
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _017 {

    public static void main(String[] args) {
        _017 a = new _017();
       PrintUtil.printIntArray(a.printNumbers(1));
    }

    public int[] printNumbers(int n) {
        int max = 1;
        for (int i = 1; i <= n; i++) {
            max *= 10;
        }
        int[] res = new int[max - 1];
        for (int i = 1; i <= max - 1; i++) {
            res[i - 1] = i;
        }
        return res;
    }

    //此题很诡异，并没有什么知识点考察，看了高票答案，原来是为了判题方便，把题目改废了。
    //本题应该是要考察大数下的这个问题的解决方案，
    //而题目限制在了返回一个int数组，而数组的大小最大只能是Integer.MAX_VALUE，所以题目就变味了。
    //
    // 现在把题目改成直接打印，而不是返回int数组再做一遍。
    public void printNumbers2(int n) {
        //由于题目给的n范围是正整数
        //而即使是long，最多也只有20位数。所以只能用String来处理。
        for (int i = 1; i <= n; i++) {

        }
    }
}
