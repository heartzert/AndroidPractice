package heartzert.test.algrithom.java.leetcode;

/**
 * Created by heartzert on 2022/12/26.
 * Email: heartzert@163.com
 */
/*
给你一个整数数组 nums 。玩家 1 和玩家 2 基于这个数组设计了一个游戏。

玩家 1 和玩家 2 轮流进行自己的回合，玩家 1 先手。开始时，两个玩家的初始分值都是 0 。每一回合，玩家从数组的任意一端取一个数字（即，nums[0] 或 nums[nums.length - 1]），取到的数字将会从数组中移除（数组长度减 1 ）。玩家选中的数字将会加到他的得分上。当数组中没有剩余数字可取时，游戏结束。

如果玩家 1 能成为赢家，返回 true 。如果两个玩家得分相等，同样认为玩家 1 是游戏的赢家，也返回 true 。你可以假设每个玩家的玩法都会使他的分数最大化。

 

示例 1：

输入：nums = [1,5,2]
输出：false
解释：一开始，玩家 1 可以从 1 和 2 中进行选择。
如果他选择 2（或者 1 ），那么玩家 2 可以从 1（或者 2 ）和 5 中进行选择。如果玩家 2 选择了 5 ，那么玩家 1 则只剩下 1（或者 2 ）可选。
所以，玩家 1 的最终分数为 1 + 2 = 3，而玩家 2 为 5 。
因此，玩家 1 永远不会成为赢家，返回 false 。
示例 2：

输入：nums = [1,5,233,7]
输出：true
解释：玩家 1 一开始选择 1 。然后玩家 2 必须从 5 和 7 中进行选择。无论玩家 2 选择了哪个，玩家 1 都可以选择 233 。
最终，玩家 1（234 分）比玩家 2（12 分）获得更多的分数，所以返回 true，表示玩家 1 可以成为赢家。
 

提示：

1 <= nums.length <= 20
0 <= nums[i] <= 107

来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/predict-the-winner
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _0486_important {

    /**
     * 一道很经典的动态规划题，如果忘了动态规划，就看看这道题
     */


    public static void main(String[] args) {
        System.out.println(new _0486_important().PredictTheWinner(new int[]{0, 0, 7, 6, 5, 6, 1}));
        System.out.println(new _0486_important().PredictTheWinner(new int[]{1, 1, 1}));
        System.out.println(new _0486_important().PredictTheWinner(new int[]{0}));
        System.out.println(new _0486_important().PredictTheWinner(new int[]{1, 1}));
    }

    /*
    动态规划
     */
    public boolean PredictTheWinner(int[] nums) {
        return dp(nums);
    }

    boolean dp(int[] nums) {
        int[][] dp = new int[nums.length][nums.length];
        for (int i = 0; i < nums.length; i++) {
            dp[i][i] = nums[i];
        }
        for (int i = nums.length - 2; i >= 0; i--) {
            for (int j = i + 1; j < nums.length; j++) {
                dp[i][j] = Math.max(nums[i] - dp[i + 1][j], nums[j] - dp[i][j - 1]);
            }
        }
        return dp[0][nums.length - 1] >= 0;
    }

    /**
     * 净胜分形式记忆化递归
     */
    public boolean PredictTheWinner05(int[] nums) {
        int[][] all = new int[nums.length][nums.length];
        return winScore2(all, nums, 0, nums.length - 1) >= 0;
    }

    /*
    不能带getScore，否则转动态规划时就变成3维数组了
    此次尝试去掉getScore
     */
    int winScore2(int[][] all, int[] nums, int l, int r) {
        if (all[l][r] != 0) {
            return all[l][r];
        }
        if (r == l) {
            return nums[l];
        }
        /*
        本次是否获胜的计算方法：本次拿到的分数-对手之后的净胜分
        就不需要getScore了。
         */
        int scoreL = nums[l] - winScore2(all, nums, l + 1, r);
        int scoreR = nums[r] - winScore2(all, nums, l, r - 1);
        int result = Math.max(scoreL, scoreR);
        all[l][r] = result;
        return result;
    }

    /**
     * 净胜分形式记忆化递归
     */
    public boolean PredictTheWinner04(int[] nums) {
        int[][] all = new int[nums.length][nums.length];
        return winScore(all, nums, 0, nums.length - 1, 1) >= 0;
    }

    int winScore(int[][] all, int[] nums, int l, int r, int getScore) {
        if (all[l][r] != 0) {
            return all[l][r];
        }
        if (r == l) {
            return getScore * nums[l];
        }
        int scoreL = nums[l] * getScore + winScore(all, nums, l + 1, r, -getScore);
        int scoreR = nums[r] * getScore + winScore(all, nums, l, r - 1, -getScore);
        int result;
        if (getScore == 1) {
            result = Math.max(scoreL, scoreR);
        } else {
            result = Math.min(scoreL, scoreR);
        }
        all[l][r] = result;
        return result;
    }

    /**
     * 总分形式记忆化递归
     */
    public boolean PredictTheWinner03(int[] nums) {
        int[][] all = new int[nums.length][nums.length];
        int score1 = maxScore03(nums, 0, nums.length - 1, all);
        long sum = 0;
        for (final int num : nums) {
            sum += num;
        }
        long score2 = sum - score1;
        return score1 >= score2;
    }

    public int maxScore03(int[] arr, int l, int r, int[][] all) {
        if (all[l][r] != 0) {
            return all[l][r];
        }
        if (r < l) {
            return 0;
        }
        if (l == r) {
            return arr[l];
        }
        if (r - l == 1) {
            return Math.max(arr[l], arr[r]);
        }
        int sl = arr[l] + Math.min(maxScore03(arr, l + 2, r, all), maxScore03(arr, l + 1, r - 1, all));
        int sr = arr[r] + Math.min(maxScore03(arr, l + 1, r - 1, all), maxScore03(arr, l, r - 2, all));
        int result = Math.max(sl, sr);
        all[l][r] = result;
        return result;
    }

    /**
     * 这个递归是最简单的思路展示。
     * 通常这种递归是必须做记忆化处理的。
     */
    public boolean PredictTheWinner02(int[] nums) {
        int score1 = maxScore02(nums, 0, nums.length - 1);
        long sum = 0;
        for (final int num : nums) {
            sum += num;
        }
        long score2 = sum - score1;
        return score1 >= score2;
    }

    public int maxScore02(int[] arr, int l, int r) {
        if (r < l) {
            return 0;
        }
        if (l == r) {
            return arr[l];
        }
        if (r - l == 1) {
            return Math.max(arr[l], arr[r]);
        }
        int sl = arr[l] + Math.min(maxScore02(arr, l + 2, r), maxScore02(arr, l + 1, r - 1));
        int sr = arr[r] + Math.min(maxScore02(arr, l + 1, r - 1), maxScore02(arr, l, r - 2));
        return Math.max(sl, sr);
    }

    /**
     * 这个思路下 无法通过这个案例 [0,0,7,6,5,6,1]
     * 思路的bug：你本来以为，先手拿一个数后，就变成“2号先手并且偶数个元素”的问题。
     * 但一旦这种情况下如果2号不能赢，它完全可以不这样拿，来找一种必赢的方案。
     */
    public boolean PredictTheWinner001(int[] nums) {
        if (nums.length == 0 || nums.length == 1) {
            return true;
        }
        if (nums.length % 2 == 0) {
            //如果是偶数个，则先手必赢，参考0877
            return true;
        } else {
            long oddSum = 0;
            long evenSum = 0;
            for (int i = 1; i < nums.length - 1; i++) {
                if (((i + 1) & 1) == 1) {
                    oddSum += nums[i];
                } else {
                    evenSum += nums[i];
                }
            }
            if (nums[0] > nums[nums.length - 1]) {
                oddSum += nums[nums.length - 1];
                return Math.min(oddSum, evenSum) + nums[0] > Math.max(oddSum, evenSum);
            } else {
                oddSum += nums[0];
                return Math.min(oddSum, evenSum) + nums[nums.length - 1] > Math.max(oddSum, evenSum);
            }
        }
    }


}
