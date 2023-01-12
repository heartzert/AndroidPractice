package heartzert.test.algrithom.java.leetcode;

import java.util.Arrays;

/**
 * Created by heartzert on 2022/12/27.
 * Email: heartzert@163.com
 */
/*
爱丽丝和鲍勃继续他们的石子游戏。许多堆石子 排成一行，每堆都有正整数颗石子 piles[i]。游戏以谁手中的石子最多来决出胜负。

爱丽丝和鲍勃轮流进行，爱丽丝先开始。最初，M = 1。

在每个玩家的回合中，该玩家可以拿走剩下的 前 X 堆的所有石子，其中 1 <= X <= 2M。然后，令 M = max(M, X)。

游戏一直持续到所有石子都被拿走。

假设爱丽丝和鲍勃都发挥出最佳水平，返回爱丽丝可以得到的最大数量的石头。

 

示例 1：

输入：piles = [2,7,9,4,4]
输出：10
解释：如果一开始Alice取了一堆，Bob取了两堆，然后Alice再取两堆。爱丽丝可以得到2 + 4 + 4 = 10堆。如果Alice一开始拿走了两堆，那么Bob可以拿走剩下的三堆。在这种情况下，Alice得到2 + 7 = 9堆。返回10，因为它更大。
示例 2:

输入：piles = [1,2,3,4,5,100]
输出：104
 

提示：

1 <= piles.length <= 100
1 <= piles[i] <= 104

来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/stone-game-ii
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _1140_not_finish {


    public static void main(String[] args) {
        System.out.println(new _1140_not_finish().stoneGameII2(new int[]{7468,6245,9261,3958,1986,1074,5677,9386,1408,1384,8811,3885,9678,8470,8893,7514,4941,2148,5217,5425,5307,747,1253,3518,5238,5834,9133,8391,6100,3362,7807,2581,6121,7684,8744,9584,4068,7204,4285,8635}));
//        System.out.println(new _1140().stoneGameII(new int[]{2, 7, 9, 4, 4}));
//        System.out.println(new _1140().stoneGameII(new int[]{1, 2, 3, 4, 5, 100}));
//        System.out.println(new _1140().stoneGameII(new int[]{0}));
//        System.out.println(new _1140().stoneGameII(new int[]{1, 1}));
//        System.out.println(new _1140().stoneGameII(new int[]{1, 1, 1}));
    }

//    public int stoneGameII(int[] piles) {
//        int[][] dp = new int[piles.length][piles.length + 1];
//        for (int i = 0; i < dp.length; i++) {
//            for (int j = 0; j < dp[0].length; j++) {
//                if (j * 2 >= dp.length - i) {
//                    int resultTmp = 0;
//                    for (int k = i; k < dp.length; k++) {
//                        resultTmp += piles[k];
//                    }
//                    dp[i][j] = resultTmp;
//                }
//            }
//        }
//        for (int j = dp[0].length - 1; j >= 0 ; j--) {
//            for (int i = dp.length - 1; i >= 0; i--) {
//                if (dp[i][j] != 0) {
//                    continue;
//                }
//                int result = Integer.MIN_VALUE;
//                for (int k = 1; i <= 2 * j; k++) {
//                    int resultTmp = 0;
//                    for (int j = 0; j < i; j++) {
//                        resultTmp += piles[s + j];
//                    }
//                    resultTmp -= winScore2(all, piles, s + i, Math.max(i, m));
//                    result = Math.max(result, resultTmp);
//                }
//                dp[i][j] =
//            }
//        }
//    }

    public int stoneGameII2(int[] piles) {
        int[][] all = new int[piles.length][piles.length + 1];
        int win = winScore2(all, piles, 0, 1);
        int sum = Arrays.stream(piles).sum();
        return (sum + win) / 2;
    }

    public int winScore2(int[][] all, int[] piles, int s, int m) {
        if (s < all.length || m < all[0].length) {
            if (all[s][m] != 0) {
                return all[s][m];
            }
        }
        int length = piles.length;
        if (s >= length) {
            return 0;
        }
        if (s + 2 * m >= length) {
            int resultTmp = 0;
            for (int j = s; j < length; j++) {
                resultTmp += piles[j];
            }
            return resultTmp;
        }
        int result = Integer.MIN_VALUE;
        for (int i = 1; i <= 2 * m; i++) {
            int resultTmp = 0;
            for (int j = 0; j < i; j++) {
                resultTmp += piles[s + j];
            }
            resultTmp -= winScore2(all, piles, s + i, Math.max(i, m));
            result = Math.max(result, resultTmp);
        }
        return result;
    }
}
