package heartzert.test.algrithom.java.offer;

/**
 * Created by heartzert on 2020/7/20.
 * Email: heartzert@163.com
 */

/*
输入一个链表，输出该链表中倒数第k个节点。
为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒数第1个节点。
例如，一个链表有6个节点，从头节点开始，它们的值依次是1、2、3、4、5、6。
这个链表的倒数第3个节点是值为4的节点。


示例：

给定一个链表: 1->2->3->4->5, 和 k = 2.

返回链表 4->5.

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/lian-biao-zhong-dao-shu-di-kge-jie-dian-lcof
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _022 {

    /*
    测试用例：
    1.head is null
    2.k > link size
    3.k == 0
     */
    public static void main(String[] args) {
        _022 a = new _022();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next = new ListNode(6);
        head.next.next.next.next.next.next = new ListNode(7);
        ListNode.print(head);
        System.out.println(a.getKthFromEnd(head, 3).val);
        System.out.println(a.getKthFromEnd(head, 10) == null);
        System.out.println(a.getKthFromEnd(head, 0) == null);
        System.out.println(a.getKthFromEnd(head, -12) == null);
        System.out.println(a.getKthFromEnd(null, 3) == null);
    }

    public ListNode getKthFromEnd(ListNode head, int k) {
        if (head == null) return  null;
        if (k <= 0) return null;
        ListNode point = head;
        int i ;
        for (i = 1; i < k && point.next != null; i++,point = point.next);
        if (i < k) return null;
        ListNode point2 = head;
        while (point.next != null) {
            point = point.next;
            point2 = point2.next;
        }
        return point2;
    }
}
