package array;

/**
 * @author zhang.xu
 * email nagisaww.zhang@beibei.com
 * 2020/11/18 4:05 下午
 * info : 链表相关的题目
 */
public class LinkedList {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * # 237  删除链表中的节点
     * 请编写一个函数，使其可以删除某个链表中给定的（非末尾）节点。传入函数的唯一参数为 要被删除的节点 。
     * 输入：head = [4,5,1,9], node = 5
     * 输出：[4,1,9]
     * 解释：给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
     */
    public void deleteNode(ListNode node, int val) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    /**
     * #206 反转链表
     * 输入: 1->2->3->4->5->NULL
     * 输出: 5->4->3->2->1->NULL
     */
    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode now = head;
        while (now != null) {
            ListNode next = now.next;
            now.next = pre;
            pre = now;
            now = next;
        }
        return pre;
    }
}