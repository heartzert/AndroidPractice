package heartzert.test.all.java.offer;

/**
 * Created by heartzert on 2020/6/30.
 * Email: heartzert@163.com
 */
/*

给定单向链表的头指针和一个要删除的节点的值，定义一个函数删除该节点。

返回删除后的链表的头节点。

注意：此题对比原题有改动

示例 1:

输入: head = [4,5,1,9], val = 5
输出: [4,1,9]
解释: 给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
示例 2:

输入: head = [4,5,1,9], val = 1
输出: [4,5,9]
解释: 给定你链表中值为 1 的第三个节点，那么在调用了你的函数之后，该链表应变为 4 -> 5 -> 9.


说明：

题目保证链表中节点的值互不相同
若使用 C 或 C++ 语言，你不需要 free 或 delete 被删除的节点
 */
class _018 {

    public static void main(String[] args) {
        _018 a = new _018();
        ListNode head = new ListNode(4);
        head.next = new ListNode(5);
        head.next.next = new ListNode(1);
        head.next.next.next = new ListNode(9);
        a.deleteNode(head, 1);
        ListNode p = head;
        while (p != null) {
            System.out.print(p.val + "->");
            p = p.next;
        }
    }

    /*
    测试用例：
    1.删除首个
    2.删除末尾
    3.删除中间
    4.只有一个head并删除
    5.head为null
    6.删除不存在的值

    注意：题目保证链表中节点的值互不相同，所以查到一个就可以直接返回了。

     */
    public ListNode deleteNode(ListNode head, int val) {
        if (head == null) {
            return head;
        }
        if (head.val == val) {
            head = head.next;
            return head;
        }
        ListNode p = null;
        ListNode mid = head;
//        只需两个指针即可，aft可以用mid.next代替
//        ListNode aft = mid.next;
        while (true) {
            if (mid.val == val) {
                p.next = mid.next;
                mid.next = null;
                break;
            }
            if (mid.next == null) {
                break;
            }
            p = mid;
            mid = mid.next;
        }
        return head;
    }
}
