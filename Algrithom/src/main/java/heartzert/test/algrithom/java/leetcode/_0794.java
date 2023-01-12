package heartzert.test.algrithom.java.leetcode;

/**
 * Created by heartzert on 2023/1/6.
 * Email: heartzert@163.com
 */
/*
给你一个字符串数组 board 表示井字游戏的棋盘。当且仅当在井字游戏过程中，棋盘有可能达到 board 所显示的状态时，才返回 true 。

井字游戏的棋盘是一个 3 x 3 数组，由字符 ' '，'X' 和 'O' 组成。字符 ' ' 代表一个空位。

以下是井字游戏的规则：

玩家轮流将字符放入空位（' '）中。
玩家 1 总是放字符 'X' ，而玩家 2 总是放字符 'O' 。
'X' 和 'O' 只允许放置在空位中，不允许对已放有字符的位置进行填充。
当有 3 个相同（且非空）的字符填充任何行、列或对角线时，游戏结束。
当所有位置非空时，也算为游戏结束。
如果游戏结束，玩家不允许再放置字符。
 

示例 1：


输入：board = ["O  ","   ","   "]
输出：false
解释：玩家 1 总是放字符 "X" 。
示例 2：


输入：board = ["XOX"," X ","   "]
输出：false
解释：玩家应该轮流放字符。
示例 3:


输入：board = ["XOX","O O","XOX"]
输出：true
 

提示：

board.length == 3
board[i].length == 3
board[i][j] 为 'X'、'O' 或 ' '

来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/valid-tic-tac-toe-state
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _0794 {

    public static void main(String[] args) {
        System.out.println(new _0794().validTicTacToe(new String[]{"O  ", "   ", "   "}));
        System.out.println(new _0794().validTicTacToe(new String[]{"XOX", " X ", "   "}));
        System.out.println(new _0794().validTicTacToe(new String[]{"XOX", "O O", "XOX"}));
        System.out.println(new _0794().validTicTacToe(new String[]{"XXX", "OOX", "OOX"}));
        System.out.println(new _0794().validTicTacToe(new String[]{"OXX", "XOX", "OXO"}));
    }

    public boolean validTicTacToe(String[] board) {
        int numX = 0;
        int numO = 0;
        for (String string : board) {
            for (int i = 0; i < string.length(); i++) {
                char c = string.charAt(i);
                if ('X' == c) {
                    numX++;
                } else if ('O' == c) {
                    numO++;
                }
            }
        }
        if (numX - numO != 1 && numX - numO != 0) {
            return false;
        }
        int XsucNum = 0;
        //只能有一个三连，否则是无效输入
        for (String string : board) {
            if (string.equals("XXX")) {
                XsucNum++;
            }
        }
        for (int i = 0; i < 3; i++) {
            if ((board[0].substring(i, i + 1) + board[1].charAt(i) + board[2].charAt(i)).equals("XXX")) {
                XsucNum++;
            }
        }
        if ((board[0].substring(0, 1) + board[1].charAt(1) + board[2].charAt(2)).equals("XXX")) {
            XsucNum++;
        }
        if ((board[2].substring(0, 1) + board[1].charAt(1) + board[0].charAt(2)).equals("XXX")) {
            XsucNum++;
        }

        int OsucNum = 0;
        for (String string : board) {
            if (string.equals("OOO")) {
                OsucNum++;
            }
        }
        for (int i = 0; i < 3; i++) {
            if ((board[0].substring(i, i + 1) + board[1].charAt(i) + board[2].charAt(i)).equals("OOO")) {
                OsucNum++;
            }
        }
        if ((board[0].substring(0, 1) + board[1].charAt(1) + board[2].charAt(2)).equals("OOO")) {
            OsucNum++;
        }
        if ((board[2].substring(0, 1) + board[1].charAt(1) + board[0].charAt(2)).equals("OOO")) {
            OsucNum++;
        }

        if (numX - numO == 1) {
            if (OsucNum > 0) {
                return false;
            }
        }
        if (numX - numO == 0) {
            if (XsucNum > 0) {
                return false;
            }
        }
        return true;
    }
}
