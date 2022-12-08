package heartzert.test.algrithom.java.leetcode;

import heartzert.test.algrithom.java.TreeNode;
import heartzert.test.algrithom.java.utils.ListHelper;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by heartzert on 2022/12/8.
 * Email: heartzert@163.com
 */
/*
给你二叉树的根节点 root ，返回其节点值的 层序遍历 。 （即逐层地，从左到右访问所有节点）。

 

示例 1：


输入：root = [3,9,20,null,null,15,7]
输出：[[3],[9,20],[15,7]]
示例 2：

输入：root = [1]
输出：[[1]]
示例 3：

输入：root = []
输出：[]
 

提示：

树中节点数目在范围 [0, 2000] 内
-1000 <= Node.val <= 1000

来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/binary-tree-level-order-traversal
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _0102 {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
//        root.right = new TreeNode(2);
//        root.right.left = new TreeNode(5);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.right.left = new TreeNode(5);
        root.right.right = new TreeNode(6);
        List<List<Integer>> list = new _0102().levelOrder(root);
        ListHelper.printList(list);
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new LinkedList<>();
        List<TreeNode> readList = new LinkedList<>();
        List<List<TreeNode>> readMultiList = new LinkedList<>();
        if (root == null) {
            return result;
        }
        readList.add(root);
        readMultiList.add(readList);
        while (!readMultiList.isEmpty()) {
            List<TreeNode> tmpList = new LinkedList<>();
            List<TreeNode> nowList = readMultiList.remove(0);
            List<Integer> tmpIntList = new LinkedList<>();
            for (TreeNode node : nowList) {
                tmpIntList.add(node.val);
                if (node.left != null) {
                    tmpList.add(node.left);
                }
                if (node.right != null) {
                    tmpList.add(node.right);
                }
            }
            result.add(tmpIntList);
            if (!tmpList.isEmpty()) {
                readMultiList.add(tmpList);
            }
        }
        return result;
    }
}
