package heartzert.test.all.java.offer;

/**
 * Created by heartzert on 2020/7/20.
 * Email: heartzert@163.com
 */

/*
定义一个函数，输入一个链表的头节点，反转该链表并输出反转后链表的头节点。

 

示例:

输入: 1->2->3->4->5->NULL
输出: 5->4->3->2->1->NULL
 

限制：

0 <= 节点个数 <= 5000

 

注意：本题与主站 206 题相同：https://leetcode-cn.com/problems/reverse-linked-list/

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/fan-zhuan-lian-biao-lcof
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _024 {
    public static void main(String[] args) {
        _024 a = new _024();
        ListNode list = ListNode.create(10);
        ListNode.print(list);
        ListNode.print(a.reverseList(list));
    }
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode pre = null;
        ListNode mid = head;
        ListNode aft = head.next;
        while (mid != null) {
            mid.next = pre;
            pre = mid;
            mid = aft;
            if (mid != null) {
                aft = mid.next;
            }
        }
        return pre;
    }
}
