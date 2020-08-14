package heartzert.test.algrithom.java.offer;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by heartzert on 2020/6/27.
 * Email: heartzert@163.com
 */
/*
地上有一个m行n列的方格，从坐标 [0,0] 到坐标 [m-1,n-1] 。
一个机器人从坐标 [0, 0] 的格子开始移动，
它每次可以向左、右、上、下移动一格（不能移动到方格外），
也不能进入行坐标和列坐标的数位之和大于k的格子。
例如，当k为18时，机器人能够进入方格 [35, 37] ，因为3+5+3+7=18。
但它不能进入方格 [35, 38]，因为3+5+3+8=19。请问该机器人能够到达多少个格子？

示例 1：

输入：m = 2, n = 3, k = 1
输出：3
示例 2：

输入：m = 3, n = 1, k = 0
输出：1
提示：

1 <= n,m <= 100
0 <= k <= 20

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/ji-qi-ren-de-yun-dong-fan-wei-lcof
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _013 {

    List list;

    public static void main(String[] args) {
        _013 a = new _013();
        //测试用例
        //1,8,3  4
        //1,8,13  8
        //8,1,3  4
        //8,1,13  8
        //15,15,8 45
        //15,15,15 219
        System.out.println(a.movingCount3(1, 8, 3));
        System.out.println(a.movingCount3(1, 8, 13));
        System.out.println(a.movingCount3(8, 1, 3));
        System.out.println(a.movingCount3(8, 1, 13));
        System.out.println(a.movingCount3(15, 15, 8));
        System.out.println(a.movingCount3(15, 15, 15));
    }

    /*
    分析:
    可以使用类似于广度优先搜索的办法，将可以走到的格子放入队列，
    然后不断出队格子，将该格子可走到的位置入队，如果已经走过直接返回。

    所以需要：
    1.一个队列。
    2.一个m*n的数组，用来标记是否访问过

    为什么不写测试用例！！！！
    1.只有一行，有界无界
    2.只有一列，有界无界
    3.多行多列，随便写两个界

    ps:由于在机器人行进中，我默认可以朝4个方向移动，所以判断无法执行深度优先遍历，所以采用了广度优先遍历。
    而在题解分析中分析了解的范围，并推断出只需朝右以及朝下即可遍历所有可达解，所以可以使用深度优先遍历。
     */
    public int movingCount(int m, int n, int k) {
        //m行n列写反了，但是答案是对的，因为是镜像反转，而且只求个数。
        //注意二维数组的行列！！！
        boolean[][] board = new boolean[m][n];
        list = new LinkedList<Data>();
        int i = 0;
        list.add(new Data(0, 0));
        while (!list.isEmpty()) {
            Data d = (Data) list.remove(0);
            if (bfs(board, d.i, d.j, k)) {
                i++;
            }
        }
        return i;
    }

    class Data {

        int i, j;

        public Data(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    public boolean bfs(boolean[][] board, int i, int j, int k) {
        if (board[i][j]) {
            return false;
        }
        //用来标记读取过的这一行居然忘记写了，也是奇葩。
        board[i][j] = true;
        checkAndPut(board, i - 1, j, k);
        checkAndPut(board, i, j - 1, k);
        checkAndPut(board, i, j + 1, k);
        checkAndPut(board, i + 1, j, k);
        return true;
    }

    /*
    没有判断负数！

    其实可以提前筛选，减少循环次数
     */
    private void checkAndPut(boolean[][] board, int i, int j, int k) {
        if (i < 0 || j < 0) {
            return;
        }
        if (i >= board.length || j >= board[0].length) {
            return;
        }
        //是 <= k!不是 <k !!
        if (figureSum(i) + figureSum(j) <= k) {
            list.add(new Data(i, j));
        }
    }

    private int figureSum(int num) {
        int sum = 0;
        while (num != 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }

    /*
    大佬的答案 BFS
     */
    public int movingCount2(int m, int n, int k) {
        boolean[][] visited = new boolean[m][n];
        Queue queue = new LinkedList<int[]>();
        int res = 0;
        queue.add(new int[]{0, 0, 0, 0});
        while (!queue.isEmpty()) {
            int[] entry = (int[]) queue.poll();
            int i = entry[0];
            int j = entry[1];
            int si = entry[2];
            int sj = entry[3];
            if (i >= m || j >= n || si + sj > k || visited[i][j]) {
                continue;
            }
            visited[i][j] = true;
            /*
            关于数位和的计算请看：
            https://leetcode-cn.com/problems/ji-qi-ren-de-yun-dong-fan-wei-lcof/solution/mian-shi-ti-13-ji-qi-ren-de-yun-dong-fan-wei-dfs-b/
             */
            queue.add(new int[]{i + 1, j, (i + 1) % 10 != 0 ? si + 1 : si - 8, sj});
            queue.add(new int[]{i, j + 1, si, (j + 1) % 10 != 0 ? sj + 1 : sj - 8});
            res++;
        }
        return res;
    }

    /*
    参考大佬答案的DFS
     */
    public int movingCount3(int m, int n, int k) {
        boolean[][] visited = new boolean[m][n];
        return dfs(0, 0, 0, 0, k, visited);
    }

    private int dfs(int i, int j, int si, int sj, int k, boolean[][] visited) {
        if (i >= visited.length || j >= visited[0].length || si + sj > k || visited[i][j]) {
            return 0;
        }
        visited[i][j] = true;
        int newSi = (i + 1) % 10 != 0 ? si + 1 : si - 8;
        int newSj = (j + 1) % 10 != 0 ? sj + 1 : sj - 8;
        return 1 + dfs(i + 1, j, newSi, sj, k, visited) + dfs(i, j + 1, si, newSj, k, visited);
    }
}
