package heartzert.test.algrithom.java.leetcode;

import heartzert.test.algrithom.java.ListNode;

/**
 * Created by heartzert on 2023/1/11.
 * Email: heartzert@163.com
 */
/*
给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。

请你将两个数相加，并以相同形式返回一个表示和的链表。

你可以假设除了数字 0 之外，这两个数都不会以 0 开头。

 

示例 1：


输入：l1 = [2,4,3], l2 = [5,6,4]
输出：[7,0,8]
解释：342 + 465 = 807.
示例 2：

输入：l1 = [0], l2 = [0]
输出：[0]
示例 3：

输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
输出：[8,9,9,9,0,0,0,1]
 

提示：

每个链表中的节点数在范围 [1, 100] 内
0 <= Node.val <= 9
题目数据保证列表表示的数字不含前导零

来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/add-two-numbers
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _0002 {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        boolean flag = false;
        ListNode fl1 = l1;
        ListNode fl2 = l2;
        //题目给非空链表，所以不用个判断
//        if (fl1 == null && fl2 != null) {
//            return fl2;
//        }
//        if (fl2 == null && fl1 != null) {
//            return fl1;
//        }
        while (fl1 != null) {
            if (fl2 != null) {
                fl1.val += fl2.val;
                fl1.val += flag ? 1 : 0;
                if (fl1.val >= 10) {
                    fl1.val -= 10;
                    flag = true;
                } else {
                    flag = false;
                }
                if (fl1.next == null) {
                    fl1.next = fl2.next;
                    fl2.next = null;
                }
                if (fl1.next == null && flag) {
                    fl1.next = new ListNode(1);
                    break;
                }
                fl1 = fl1.next;
                fl2 = fl2.next;
            } else {
                //l2 == null
                fl1.val += flag ? 1 : 0;
                if (fl1.val >= 10) {
                    fl1.val -= 10;
                    flag = true;
                } else {
                    flag = false;
                }
                if (fl1.next == null && flag) {
                    fl1.next = new ListNode(1);
                    break;
                }
                fl1 = fl1.next;
            }
        }
        return l1;
    }
}
