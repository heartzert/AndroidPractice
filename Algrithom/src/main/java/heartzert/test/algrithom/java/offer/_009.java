package heartzert.test.algrithom.java.offer;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by heartzert on 2020/6/18.
 * Email: heartzert@163.com
 */
/*
用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，
分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead 操作返回 -1 )


示例 1：

输入：
["CQueue","appendTail","deleteHead","deleteHead"]
[[],[3],[],[]]
输出：[null,null,3,-1]
示例 2：

输入：
["CQueue","deleteHead","appendTail","appendTail","deleteHead","deleteHead"]
[[],[],[5],[2],[],[]]
输出：[null,-1,null,null,5,2]
提示：

1 <= values <= 10000
最多会对 appendTail、deleteHead 进行 10000 次调用


来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/yong-liang-ge-zhan-shi-xian-dui-lie-lcof
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */

/*
 * Your CQueue object will be instantiated and called as such:
 * CQueue obj = new CQueue();
 * obj.appendTail(value);
 * int param_2 = obj.deleteHead();
 */
class _009 {

    public static void main(String[] args) {

    }

    /*
    注意：
    1. Stack<Integer> stack = new Stack<>(); 已经不建议使用了，最好用LinkedList代替，顺便加深对LinkedList的熟练程度。
    2.合理利用LinkedList.remove()方法的返回值。
    3.其实一开始没看懂题目，看了官方答案才知道要写什么，还是刷题不够，题目要理解透彻
    4.忘记做边界检查，甚至题目中都提示了边界，做题要认真，不要粗心大意

     */
    class CQueue {
        LinkedList<Integer> a = new LinkedList<>();
        LinkedList<Integer> b = new LinkedList<>();

        public CQueue() { }

        public void appendTail(int value) {
            a.addLast(value);
        }

        public int deleteHead() {
            //一开始忘记了做边界检查
            if (a.size() == 0) return -1;
            while (a.size() != 0) b.addLast(a.removeLast());
            int result = b.removeLast();
            while (b.size() != 0) a.addLast(b.removeLast());
            return result;
        }

        //官方实现，不用每次都把数据留在A栈中
        /*
        A用来入栈，B用来出栈。
        可以这样理解A栈的栈顶是队尾，B栈的栈顶是对头，然后将两个栈拼起来成为一个队列。
        所以当出队的时候，直接将B栈pop up，入队时，直接pushA栈即可。
         */
        public void appendTail2(int value) {
            a.addLast(value);
        }

        public int deleteHead2() {
            //b不空，直接出栈
            if (!b.isEmpty()) return b.removeLast();
            //b空a不空，将A倒到B，然后B出栈
            if (!a.isEmpty()) {
                while (!a.isEmpty()) b.addLast(a.removeLast());
                return b.removeLast();
            }
            //都空，-1
            return -1;
        }
    }

    //用栈实现试试
    class StackQueue {
        Stack<Integer> a = new Stack<>();
        Stack<Integer> b = new Stack<>();

        public void appendTail(int value) {
            a.push(value);
        }

        public int deleteHead() {
            if (!b.isEmpty()) return b.pop();
            if (!a.isEmpty()) {
                while (a.size() != 1) b.push(a.pop());
                return a.pop();
            }
            return -1;
        }
    }
}
