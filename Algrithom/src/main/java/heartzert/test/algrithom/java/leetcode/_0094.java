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
给定一个二叉树的根节点 root ，返回 它的 中序 遍历 。

 

示例 1：


输入：root = [1,null,2,3]
输出：[1,3,2]
示例 2：

输入：root = []
输出：[]
示例 3：

输入：root = [1]
输出：[1]
 

提示：

树中节点数目在范围 [0, 100] 内
-100 <= Node.val <= 100
 

进阶: 递归算法很简单，你可以通过迭代算法完成吗？

来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/binary-tree-inorder-traversal
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _0094 {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(5);
//        root.left = new TreeNode(2);
//        root.right = new TreeNode(3);
//        root.left.left = new TreeNode(4);
//        root.right.left = new TreeNode(5);
//        root.right.right = new TreeNode(6);
        List<Integer> list = new _0094().preorderTraversal(root);
        ListHelper.printList(list);
    }

    public List<Integer> preorderTraversal3(TreeNode root) {
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
                if (nowNode.right != null) {
                    readStack.push(nowNode.right);
                }
                readStack.push(nowNode);
                if (nowNode.left != null) {
                    readStack.push(nowNode.left);
                }
                nodeSet.add(nowNode);
            }
        }
        return result;
    }


    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new LinkedList<>();
        if (root == null) return list;
        List<TreeNode> readList = new LinkedList<>();
        Set<TreeNode> nodeSet = new HashSet<>();
        readList.add(root);
        while (readList.size() > 0) {
            TreeNode nowNode = readList.get(0);
            if (nodeSet.contains(nowNode) || (nowNode.right == null && nowNode.left == null)) {
                readList.remove(0);
                list.add(nowNode.val);
                nodeSet.remove(nowNode);
            } else if (nowNode.left != null && nowNode.right != null) {
                readList.add(0, nowNode.left);
                readList.add(2, nowNode.right);
                nodeSet.add(nowNode);
            } else if (nowNode.left != null) {
                readList.add(0, nowNode.left);
                nodeSet.add(nowNode);
            } else {
                readList.remove(0);
                list.add(nowNode.val);
                nodeSet.remove(nowNode);
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
        readTree(list, node.left);
        list.add(node.val);
        readTree(list, node.right);
    }
}
