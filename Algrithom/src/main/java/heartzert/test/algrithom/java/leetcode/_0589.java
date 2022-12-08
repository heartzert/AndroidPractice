package heartzert.test.algrithom.java.leetcode;

import heartzert.test.algrithom.java.Node;
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
给定一个 n 叉树的根节点  root ，返回 其节点值的 前序遍历 。

n 叉树 在输入中按层序遍历进行序列化表示，每组子节点由空值 null 分隔（请参见示例）。


示例 1：



输入：root = [1,null,3,2,4,null,5,6]
输出：[1,3,5,6,2,4]
示例 2：



输入：root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
输出：[1,2,3,6,7,11,14,4,8,12,5,9,13,10]
 

提示：

节点总数在范围 [0, 104]内
0 <= Node.val <= 104
n 叉树的高度小于或等于 1000
 

进阶：递归法很简单，你可以使用迭代法完成此题吗?

来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/n-ary-tree-preorder-traversal
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _0589 {
    public List<Integer> preorderTraversal(Node root) {
        List<Integer> result = new LinkedList<>();
        if (root == null) return result;
        Stack<Node> readStack = new Stack<>();
        Set<Node> nodeSet = new HashSet<>();
        readStack.add(root);
        while (!readStack.isEmpty()) {
            Node node = readStack.pop();
            if (nodeSet.contains(node)) {
                result.add(node.val);
                nodeSet.remove(node);
            } else {
                if (node.children != null && !node.children.isEmpty()) {
                    for(int i = node.children.size() - 1; i >= 0; i--) {
                        readStack.push(node.children.get(i));
                    }
                }
                readStack.push(node);
                nodeSet.add(node);
            }
        }
        return result;
    }

    public List<Integer> preorderTraversal2(Node root) {
        List<Integer> list = new LinkedList<>();
        readTree(list, root);
        return list;
    }

    public void readTree(List<Integer> list, Node node) {
        if (node == null) {
            return;
        }
        list.add(node.val);
        if (node.children != null && !node.children.isEmpty()) {
            for (Node childNode : node.children) {
                readTree(list, childNode);
            }
        }
    }
}
