package heartzert.test.algrithom.java.offer;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by heartzert on 2020/5/21.
 * Email: heartzert@163.com
 */
/*

找出数组中重复的数字。


在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。
请找出数组中任意一个重复的数字。

示例 1：

输入：
[2, 3, 1, 0, 2, 5, 3]
输出：2 或 3


限制：

2 <= n <= 100000
 */
public class _003 {

    public static void main(String[] args) {
        int[] f = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 2, 3, 4};
        System.out.println(excute2(f));
    }

    //空间复杂度O(1),时间复杂度O(n)
    static int excute(int[] nums) {
        HashSet set = new HashSet<Integer>();
        for (int i : nums) {
            if (set.contains(i)) {
                return i;
            } else {
                set.add(i);
            }
        }
        return 0;
    }

    //官方题解 空间复杂度O(n),时间复杂度O(n)
    static int excute2(int[] nums) {
        Set set = new HashSet<Integer>();
        //循环外部创建对象，避免频繁创建。
        int value = -1;
        for (int a : nums) {
            //利用add的返回值，减少contains判断
            //当成功插入时，返回true
            //当插入失败，即已经存在时，返回false
            if (!set.add(value)) {
                value = a;
                break;
            }
        }
        return value;
    }


    /*
    来自：我真的敲不动了

    我们可以构建这个一个特殊的哈希表，下标0~n-1依次对应数值0~n-1有没有出现过，如果数字x第一次出现，
    那么下标x 的位置的元素应该为该位置的相反值，标识这个数字x出现过，当我们下一次再遇到x，判断一下下标x的值是否<0，
    如果是的话表示之前已经访问过，我们就找到了这个重复的数 因为题意表明一定有重复的数字 所以如果在前面都没有返回的话
    说明重复的数字就是0 直接最后返回0就好了


    充分利用了题目中给定的条件，做了算法优化，空间复杂度O(1),时间复杂度O(n)
     */
    public int excute3(int[] nums) {
        for (int i : nums) {
            //nums[i]可能为负(为负是因为这个下标对应的元素出现过了)，所以应该用绝对值表示
            int index = Math.abs(i);
            if (nums[index]<0)
                return index;
            nums[index] *= (-1) ;
        }
        return 0;
    }

}
