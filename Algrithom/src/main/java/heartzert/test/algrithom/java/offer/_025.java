package heartzert.test.algrithom.java.offer;

/**
 * Created by heartzert on 2020/7/20.
 * Email: heartzert@163.com
 */

/*
输入两个递增排序的链表，合并这两个链表并使新链表中的节点仍然是递增排序的。

示例1：

输入：1->2->4, 1->3->4
输出：1->1->2->3->4->4
限制：

0 <= 链表长度 <= 1000

注意：本题与主站 21 题相同：https://leetcode-cn.com/problems/merge-two-sorted-lists/

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/he-bing-liang-ge-pai-xu-de-lian-biao-lcof
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _025 {
    public static void main(String[] args) {
        _025 a = new _025();
        ListNode c = ListNode.create(5);
        ListNode.print(c);
        ListNode d = ListNode.create(7);
        ListNode.print(d);
//        ListNode.print(a.mergeTwoLists(c, d));
        ListNode.print(a.mergeTwoLists(c, null));
        ListNode.print(a.mergeTwoLists(null, null));
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode point = head;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                point.next = l1;
                l1 = l1.next;
            } else {
                point.next = l2;
                l2 = l2.next;
            }
            point = point.next;
        }
        if (l1 != null) {
            while (l1 != null) {
                point.next = l1;
                point = point.next;
                l1 = l1.next;
            }
        }
        if (l2 != null) {
            while (l2 != null) {
                point.next = l2;
                point = point.next;
                l2 = l2.next;
            }
        }
        return head.next;
    }
}
