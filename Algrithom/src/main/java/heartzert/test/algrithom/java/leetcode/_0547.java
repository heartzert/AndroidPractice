package heartzert.test.algrithom.java.leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by heartzert on 2022/12/8.
 * Email: heartzert@163.com
 */
/*
有 n 个城市，其中一些彼此相连，另一些没有相连。如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，那么城市 a 与城市 c 间接相连。

省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。

给你一个 n x n 的矩阵 isConnected ，其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，而 isConnected[i][j] = 0 表示二者不直接相连。

返回矩阵中 省份 的数量。

 

示例 1：


输入：isConnected = [[1,1,0],[1,1,0],[0,0,1]]
输出：2
示例 2：


输入：isConnected = [[1,0,0],[0,1,0],[0,0,1]]
输出：3
 

提示：

1 <= n <= 200
n == isConnected.length
n == isConnected[i].length
isConnected[i][j] 为 1 或 0
isConnected[i][i] == 1
isConnected[i][j] == isConnected[j][i]

来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/number-of-provinces
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _0547 {

    public static void main(String[] args) {
        int[][] ints = new int[3][3];
        ints[0] = new int[]{1, 1, 0};
        ints[1] = new int[]{1, 1, 0};
        ints[2] = new int[]{0, 0, 1};
        System.out.println(new _0547().findCircleNum(ints));
    }

    /*
    深度优先算法
     */
    public int findCircleNum(int[][] isConnected) {
        boolean[] readed = new boolean[isConnected.length];
        int result = 0;
        for (int i = 0; i < isConnected.length; i++) {
            if (readed[i]) {
                continue;
            }
            dfs(isConnected, readed, i);
            result++;
        }
        return result;
    }

    public void dfs(final int[][] isConnected, final boolean[] readed, final int i) {
        readed[i] = true;
        for (int j = 0; j < isConnected[i].length;j++) {
            if (readed[j]) {
                continue;
            }
            if (i != j && isConnected[i][j] == 1) {
                dfs(isConnected, readed, j);
            }
        }
    }

    /*
    广度优先
     */
    public int findCircleNum2(int[][] isConnected) {
        boolean[] readed = new boolean[isConnected.length];
        Queue<Integer> reading = new LinkedList<>();
        int result = 0;
        for (int i = 0; i < isConnected.length; i++) {
            if (readed[i]) {
                continue;
            }
            reading.add(i);
            while (!reading.isEmpty()) {
                int nowColum = reading.poll();
                if (readed[nowColum]) {
                    continue;
                }
                for (int j = 0; j < isConnected[nowColum].length; j++) {
                    if (j != nowColum && isConnected[nowColum][j] == 1) {
                        reading.add(j);
                    }
                }
                readed[nowColum] = true;
            }
            result++;
        }
        return result;
    }
}
