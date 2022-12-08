package heartzert.test.algrithom.java.leetcode;

import heartzert.test.algrithom.java.TreeNode;
import heartzert.test.algrithom.java.utils.ListHelper;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Created by heartzert on 2022/12/8.
 * Email: heartzert@163.com
 */
/*
给你一棵二叉树的根节点 root ，返回其节点值的 后序遍历 。

 

示例 1：


输入：root = [1,null,2,3]
输出：[3,2,1]
示例 2：

输入：root = []
输出：[]
示例 3：

输入：root = [1]
输出：[1]
 

提示：

树中节点的数目在范围 [0, 100] 内
-100 <= Node.val <= 100
 

进阶：递归算法很简单，你可以通过迭代算法完成吗？

来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/binary-tree-postorder-traversal
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _0145 {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(5);
//        root.left = new TreeNode(2);
//        root.right = new TreeNode(3);
//        root.left.left = new TreeNode(4);
//        root.right.left = new TreeNode(5);
//        root.right.right = new TreeNode(6);
        List<Integer> list = new _0145().preorderTraversal(root);
        ListHelper.printList(list);
    }

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        if (root == null) return result;
        Stack<TreeNode> readStack = new Stack<>();
        Set<TreeNode> nodeSet = new HashSet<>();
        readStack.add(root);
        while (!readStack.isEmpty()) {
            TreeNode nowNode = readStack.pop();
            if (nodeSet.contains(nowNode)) {
                result.add(nowNode.val);
                nodeSet.remove(nowNode);
            } else {
                readStack.push(nowNode);
                if (nowNode.right != null) {
                    readStack.push(nowNode.right);
                }
                if (nowNode.left != null) {
                    readStack.push(nowNode.left);
                }
                nodeSet.add(nowNode);
            }
        }
        return result;
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
        readTree(list, node.left);
        readTree(list, node.right);
        list.add(node.val);
    }
}
