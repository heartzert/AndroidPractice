package heartzert.test.all.java.offer;

/**
 * Created by heartzert on 2020/5/21.
 * Email: heartzert@163.com
 */
/*
在一个 n * m 的二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。 

示例:
现有矩阵 matrix 如下：
[
  [1,   4,  7, 11, 15],
  [2,   5,  8, 12, 19],
  [3,   6,  9, 16, 22],
  [10, 13, 14, 17, 24],
  [18, 21, 23, 26, 30]
]
给定 target = 5，返回 true。
给定 target = 20，返回 false。
 
限制：
0 <= n <= 1000
0 <= m <= 1000
 
注意：本题与主站 240 题相同：https://leetcode-cn.com/problems/search-a-2d-matrix-ii/

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/er-wei-shu-zu-zhong-de-cha-zhao-lcof
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class _004 {

    public static void main(String[] args) {
        int[][] a = new int[][]{{1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}};
        int[][] b = new int[][]{{1,4,7,11,15}};
        int[][] c = new int[][]{};
        System.out.println(execute2(a, 6));
        System.out.println(execute2(b, 6));
        System.out.println(execute2(c, 6));
    }

    //无思路 菜批
    static boolean execute(int[][] matrix, int target) {
        return true;
    }

    //官方解法
    /*
    由于给定的二维数组具备每行从左到右递增以及每列从上到下递增的特点，当访问到一个元素时，可以排除数组中的部分元素。
    从二维数组的右上角开始查找。如果当前元素等于目标值，则返回 true。如果当前元素大于目标值，则移到左边一列。
    如果当前元素小于目标值，则移到下边一行。

    可以证明这种方法不会错过目标值。如果当前元素大于目标值，说明当前元素的下边的所有元素都一定大于目标值，
    因此往下查找不可能找到目标值，往左查找可能找到目标值。如果当前元素小于目标值，说明当前元素的左边的所有元素都一定小于目标值，
    因此往左查找不可能找到目标值，往下查找可能找到目标值。

    思考：仔细分析题目给的条件，判断数据规律。我只想到了左上和右下开始，并没有想到右上开始的情况。
    要严格注意边界条件！！
     */
    static boolean execute2(int[][] matrix, int target) {
        //输入校验
        if (matrix.length <= 0) return false;
        int n = matrix.length - 1;
        int x = matrix[0].length - 1;//m..0
        int y = 0;//0..n
        while (true) {
            //边界校验
            if (x < 0 || y > n) return false;
            //注意二维数组的取值方式，前列后行。
            int value = matrix[y][x];
            //循环退出条件
            if (value == target) return true;
            //递进条件，如果目标值小于当前值，则一定小于当前值下面的所有值，左移一列判断
            if (target < value) {
                x--;
                continue;
            }
            //如果目标值大于当前值，则一定大于当前值左侧所有值，下移一行判断
            y++;
        }
    }
}
