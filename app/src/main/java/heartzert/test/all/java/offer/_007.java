package heartzert.test.all.java.offer;

import android.util.Log;

/**
 * Created by heartzert on 2020/5/29.
 * Email: heartzert@163.com
 */
/*
输入某二叉树的前序遍历和中序遍历的结果，请重建该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。

例如，给出

前序遍历 preorder = [3,9,20,15,7]
中序遍历 inorder = [9,3,15,20,7]
返回如下的二叉树：

    3
   / \
  9  20
    /  \
   15   7
 
限制：
0 <= 节点个数 <= 5000

public class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
}

特殊输入：
1.任意遍历为空
2.前序与中序遍历不匹配
3.前序与中序遍历都只有一个值

注意：本题与主站 105 题重复：https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/zhong-jian-er-cha-shu-lcof
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class _007 {

    class TreeNode {

        int val;

        TreeNode left;

        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        int[] preList = new int[]{3, 9, 20, 15, 7};
        int[] midList = new int[]{9, 3, 15, 20, 7};
        int[] preList2 = new int[]{3};
        int[] midList2 = new int[]{3};
        _007 a = new _007();
        TreeNode root = a.execute1(preList2, midList2);
        Log.d("=======", "main: ");
    }

    /*
    自己的思路，参考了一下官方标准答案

    注意：
    1.数组无法取子数组，所以必须将整个数组传入并标明起止位置
    2.方法内的形参！起初想吧root传入，在方法内赋值，完全忘记了方法内的形参的问题。
    而且记得，如果传入引用，修改引用没有用，但是修改引用指向的数据是会传递出方法外的！

    本次编码的优点：
    1.考虑到了各种特殊输入，代码几乎一次跑通
    2.各种边界处理完善，代码几乎一次跑通

    分析：
    时间复杂度：只是遍历了两个数组，O(n)
    空间：除了最终的树外，只用到O(1)的空间

     */
    TreeNode execute1(int[] preorder, int[] inorder) {
        TreeNode root = null;
        //为空的情况没有考虑到
        if (preorder == null || inorder == null) {
            return null;
        }
        if (preorder.length != inorder.length) {
            return null;
        }
        if (preorder.length == 0) {
            return null;
        }
        if (preorder.length == 1) {
            if (preorder[0] == inorder[0]) {
                root = new TreeNode(preorder[0]);
            }
            return root;
        }
        root = deal1(inorder, preorder, 0, preorder.length - 1, 0, inorder.length - 1);
        return root;
    }

    private TreeNode deal1(int[] midList, int[] preList, int preStart, int preEnd, int midStart, int midEnd) {
        if (preEnd < preStart) {
            return null;
        }
        if (midEnd < midStart) {
            return null;
        }
        TreeNode root;
        int rootvalue = preList[midStart];
        int i = preStart;
        //官方答案的思路是先用一个map将preList预处理，就不用每次循环来找了
        for (; i <= preEnd; i++) {
            if (midList[i] == rootvalue) {
                break;
            }
        }
        if (i > preEnd) {
            return null;
        }
        root = new TreeNode(rootvalue);
        int leftTreeItemsNum = i - preStart;
        //要注意计算起止位置
        root.left = deal1(midList, preList, preStart, i - 1, midStart + 1, midStart + leftTreeItemsNum);
        root.right = deal1(midList, preList, i + 1, preEnd, midStart + 1 + leftTreeItemsNum, midEnd);
        return root;
    }
}
