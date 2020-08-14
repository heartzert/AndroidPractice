package heartzert.test.algrithom.java.offer;

import java.util.Random;

/**
 * Created by heartzert on 2020/7/20.
 * Email: heartzert@163.com
 */

public class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

    static void print(ListNode head) {
        while (head != null) {
            System.out.print(head.val + "->");
            head = head.next;
        }
        System.out.println("null");
    }

    static ListNode create(int n) {
        ListNode head = new ListNode(1);
        ListNode point = head;
        for (int i = 2; i < n + 2; i++) {
            point.next = new ListNode(i);
            point = point.next;
        }
        return head;
    }

    static ListNode createRandom(int n) {
        ListNode head = new ListNode(new Random().nextInt(2 * n));
        ListNode point = head;
        for (int i = 1; i < n; i++) {
            point.next = new ListNode(new Random().nextInt(2 * n));
            point = point.next;
        }
        return head;
    }
}
