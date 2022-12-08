package heartzert.test.algrithom.java.leetcode;

import heartzert.test.algrithom.java.Node;
import heartzert.test.algrithom.java.TreeNode;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by heartzert on 2022/12/8.
 * Email: heartzert@163.com
 */
/*
给定一个 N 叉树，返回其节点值的层序遍历。（即从左到右，逐层遍历）。

树的序列化输入是用层序遍历，每组子节点都由 null 值分隔（参见示例）。

 

示例 1：



输入：root = [1,null,3,2,4,null,5,6]
输出：[[1],[3,2,4],[5,6]]
示例 2：



输入：root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
输出：[[1],[2,3,4,5],[6,7,8,9,10],[11,12,13],[14]]
 

提示：

树的高度不会超过 1000
树的节点总数在 [0, 10^4] 之间

来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/n-ary-tree-level-order-traversal
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _0429 {

    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> result = new LinkedList<>();
        List<Node> readList = new LinkedList<>();
        List<List<Node>> readMultiList = new LinkedList<>();
        if (root == null) {
            return result;
        }
        readList.add(root);
        readMultiList.add(readList);
        while (!readMultiList.isEmpty()) {
            List<Node> tmpList = new LinkedList<>();
            List<Node> nowList = readMultiList.remove(0);
            List<Integer> tmpIntList = new LinkedList<>();
            for (Node node : nowList) {
                tmpIntList.add(node.val);
                if (node.children != null) {
                    tmpList.addAll(node.children);
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
