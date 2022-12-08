package heartzert.test.algrithom.java.leetcode;

import heartzert.test.algrithom.java.TreeNode;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by heartzert on 2022/12/8.
 * Email: heartzert@163.com
 */
/*
给你二叉树的根节点 root ，返回它节点值的 前序 遍历。

 

示例 1：


输入：root = [1,null,2,3]
输出：[1,2,3]
示例 2：

输入：root = []
输出：[]
示例 3：

输入：root = [1]
输出：[1]
示例 4：


输入：root = [1,2]
输出：[1,2]
示例 5：


输入：root = [1,null,2]
输出：[1,2]
 

提示：

树中节点数目在范围 [0, 100] 内
-100 <= Node.val <= 100
 

进阶：递归算法很简单，你可以通过迭代算法完成吗？

来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/binary-tree-preorder-traversal
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _0144 {

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new LinkedList<>();
        if (root == null) return list;
        List<TreeNode> readList = new LinkedList<>();
        readList.add(root);
        while (readList.size() > 0) {
            TreeNode nowNode = readList.remove(0);
            list.add(nowNode.val);
            if (nowNode.left != null && nowNode.right != null) {
                readList.add(0, nowNode.left);
                readList.add(1, nowNode.right);
            } else if (nowNode.left != null) {
                readList.add(0, nowNode.left);
            } else if (nowNode.right != null) {
                readList.add(0, nowNode.right);
            }
        }
        return list;
    }

    public List<Integer> preorderTraversal2(TreeNode root) {
        List<Integer> list = new LinkedList<>();
        readTree(list, root);
        return list;
    }

    public void readTree(List<Integer> list, TreeNode node) {
        if (node == null) {
            return;
        }
        list.add(node.val);
        readTree(list, node.left);
        readTree(list, node.right);
    }
}
