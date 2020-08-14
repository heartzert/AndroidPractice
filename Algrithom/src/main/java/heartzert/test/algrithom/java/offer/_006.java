package heartzert.test.algrithom.java.offer;

import java.util.LinkedList;

/**
 * Created by heartzert on 2020/5/25.
 * Email: heartzert@163.com
 */
/*

输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。

示例 1：
输入：head = [1,3,2]
输出：[2,3,1]

限制：
0 <= 链表长度 <= 10000
 */
public class _006 {

    public static void main(String[] args) {
        _006 instance = new _006();
        ListNode head = instance.buildLink();
//        int[] a = instance.excute(head);
        int[] a = instance.execute3(head);
        for (int x : a) {
            System.out.println(x + ",");
        }
    }

    ListNode buildLink() {
        ListNode head = new ListNode(1);
        head.next = new ListNode(3);
        head.next.next = new ListNode(2);
        return head;
    }

    //我的思路，先将链表翻转，打印，再翻转。
    //时间复杂度O(n) 空间复杂度O(1)
    int[] excute(ListNode head) {
        if (head == null) {
            return new int[0];
        }
        if (head.next == null) {
            return new int[]{head.val};
        }
        //翻转
        ListNode pre = null;
        ListNode point = head;
        ListNode aft = point.next;
        int count = 0;
        while (aft != null) {
            point.next = pre;
            pre = point;
            point = aft;
            aft = point.next;
            count++;
        }
        point.next = pre;
        count++;

        //输出
        aft = point;
        int[] a = new int[count];
        for (int i = 0; aft != null; i++) {
            a[i] = aft.val;
            aft = aft.next;
        }
        //翻转
        pre = null;
        aft = point.next;
        while (aft != null) {
            point.next = pre;
            pre = point;
            point = aft;
            aft = point.next;
        }
        point.next = pre;
        return a;
    }

    //答案思路：遍历时用一个栈存储所有节点，时间O(n),空间O(n)
    int[] execute2(ListNode head) {
        ListNode point = head;
        LinkedList list = new LinkedList<Integer>();
        while (point != null) {
            list.add(point.val);
            point = point.next;
        }
        int size = list.size();
        int[] result = new int[size];
        for (int i = size - 1; i >= 0; i--) {
            result[size - 1 - i] = (int) list.get(i);
        }
        return result;
    }

    //答案思路2: 递归，时间O(n),空间O(n)
    int[] execute3(ListNode head) {
        LinkedList<Integer> list = new LinkedList<>();
        execute3Inner(list, head);
        int size = list.size();
        int[] result = new int[size];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    void execute3Inner(LinkedList<Integer> list, ListNode head) {
        if (head == null) {
            return;
        }
        execute3Inner(list, head.next);
        list.add(head.val);
    }
}
