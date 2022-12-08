package heartzert.test.algrithom.java;

import java.util.List;

/**
 * Created by heartzert on 2022/12/8.
 * Email: heartzert@163.com
 */
public class Node {

    public int val;

    public List<Node> children;

    public Node() {
    }

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
}
