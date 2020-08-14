package heartzert.test.algrithom.java.offer;

/**
 * Created by heartzert on 2020/6/19.
 * Email: heartzert@163.com
 */
/*

把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。
例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。

示例 1：

输入：[3,4,5,1,2]
输出：1
示例 2：

输入：[2,2,2,0,1]
输出：0
注意：本题与主站 154 题相同：https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array-ii/
 */
class _011 {

    public static void main(String[] args) {
        _011 a = new _011();
        int[] array = new int[]{3, 4, 5, 1, 2};
        int[] array1 = null;
        int[] array2 = new int[]{3};
        int[] array3 = new int[]{3, 2};
        int[] array4 = new int[]{3, 3, 3, 1, 2};
        int[] array5 = new int[]{3, 4, 5, 1, 1, 1, 1, 2};
        int[] array6 = new int[]{1, 1, 1, 1};
        int[] array7 = new int[]{1, 2, 3, 4};
        int[] array8 = new int[]{1, 1, 1, 0, 1};
        int[] array9 = new int[]{1, 0, 1, 1, 1};
        System.out.println(a.minArray4(array));
        System.out.println(a.minArray4(array1));
        System.out.println(a.minArray4(array2));
        System.out.println(a.minArray4(array3));
        System.out.println(a.minArray4(array4));
        System.out.println(a.minArray4(array5));
        System.out.println(a.minArray4(array6));
        System.out.println(a.minArray4(array7));
        System.out.println(a.minArray4(array8));
        System.out.println(a.minArray4(array9));
    }

    /*
    O(n)的解法就不用想了，肯定不是要考察的内容。
    所以直接就往二分去考虑，但是想了想二分只用中点没办法判断到底是在左边还是右边，于是思路断了。
    瞟了一眼答案，原来二分可以不止用一个点。。。。加上两头，三个点来判断，再加上旋转数组递增特性，就可以判断左右了。
    于是写出了如下代码。

    ps：这个题考虑logn的解法其实是hard难度了，怪不得我觉得很难（其实还是太是菜）。

    注意：
    1.感觉代码不够简洁，有优化余地。
    2.所有用到 -1的地方注意判断数组越界！
    3.没有提前写出特殊输入！！导致边界错误！！
    */
    /*
    特殊输入：
    1.数组为空
    2.数组只有1个数
    3.数组未旋转
    4.数组有多个相等最小数
    5.数组全部相等
    6.只有两个且倒序[3,2]
     */
    //这段代码跑不通，因为内部细节的思路完全错了，仅做保留鞭尸用
    public int minArray(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return -1;
        }
        if (numbers.length == 1) {
            return numbers[0];
        }
        int first = 0;
        int last = numbers.length - 1;
        int mid;
        if (numbers[last] <= numbers[first]) {
            while (true) {
                //原写法：(first + last) / 2
                //修改以防止越界问题，严谨一点
                mid = first + (last - first) / 2;
                //这里忘记考虑数组越界问题！！
                //
                //这里本来写的是return numbers[0]，在流程中，mid走到0时一定是first = 0，last = 1
                //而由于最小值是位置0的情况已经被排除了，所以一定是0与1相等才到达这里，所以理所应当的随便返回了一个值
                //但是！当只有两个数据时，也会到达这里！就不能随便返回一个了！
                //并且由于上面分析了“最小值是位置0的情况已经被排除了”，所以应该直接返回numbers[1]！！
                //
                //这里连续犯了两个错误，菜比
                //
                //其实最后还是有问题的，这道题没有想象中那么简单，各种特殊情况的处理比较难考虑到
                //建议学习原题目链接中Krahets大佬的分析方法。
                //https://leetcode-cn.com/problems/xuan-zhuan-shu-zu-de-zui-xiao-shu-zi-lcof/solution/mian-shi-ti-11-xuan-zhuan-shu-zu-de-zui-xiao-shu-3/#comment
                //
//                if (mid == 0) return numbers[1];
                if (first - last == 1){
                    return Math.min(numbers[first], numbers[last]);
                }
                if (numbers[mid] < numbers[mid - 1]) {
                    return numbers[mid];
                }
                //这里一开始是用的 >，导致在[2，2，2，0,1]这种数组中失效！！！
                //
                //这里用中值跟第一个值比较，是完全错误的！，中值与第一个值没有太大关系！
                //这个算法完全失败！记住要仔细分析题中数据结构的性质！
                if (numbers[mid] >= numbers[first]) {
                    first = mid;
                } else {
                    last = mid;
                }
            }
        } else {
            return numbers[0];
        }
    }

    /*
    大佬的解法
     */
    public int minArray2(int[] numbers) {
        int i = 0, j = numbers.length - 1;
        while (i < j) {
            int m = (i + j) / 2;
            if (numbers[m] > numbers[j]) i = m + 1;
            else if (numbers[m] < numbers[j]) j = m;
            else j--;
        }
        return numbers[i];
    }

    /*
    另一个大佬Howfar：
    我觉得numbers[m]==numbers[j]的时候直接遍历就行了，
    楼主的方法代码很短，但是不太直观，记不住，要分析不会错过最小值的过程太复杂了。
    实际上当相等的时候进行j--，也就是一个个往前去遍历，复杂度跟直接遍历没差别。
     */
    public int minArray3(int[] numbers) {
        int start = 0;
        int end = numbers.length - 1;
        while(start != end){
            int mid = start + (end - start) / 2;
            if(numbers[mid] > numbers[end]) start = mid + 1;
            else if(numbers[mid] < numbers[end]) end = mid;
            else {
                int result = numbers[start];
                for(int i = start;i <= end;i++){
                    if (numbers[i] < result) result = numbers[i];
                }
                return result;
            }
        }
        return numbers[start];
    }

    /*
    自己再试一遍
     */
    public int minArray4(int[] numbers) {
        if (numbers == null || numbers.length == 0) return -1;
        if (numbers.length == 1) return numbers[0];
        int start = 0;
        int end = numbers.length - 1;
        int mid;
        while (start < end) {
            mid = start + (end - start) / 2;
            if (numbers[mid] > numbers[end]) start = mid + 1;
            else if (numbers[mid] < numbers[end]) end = mid;
            //这一步可以简单理解为，如果相等，无法判断最小值在哪半边，所以直接遍历。
            else if (numbers[mid] == numbers[end]) end--;
        }
        return numbers[start];
    }

}
