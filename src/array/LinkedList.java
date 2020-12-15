package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

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


    /**
     * #148 排序链表
     * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。
     */
    public ListNode sortList(ListNode head) {
        // 递归 快慢指针拆分到每两两各自处理
        if (null == head || null == head.next) {
            return head;
        }
        // 快慢指针各自处理一半
        ListNode fast = head.next;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        // 左右各自递归
        ListNode tmp = slow.next;
        slow.next = null;
        ListNode left = sortList(head);
        ListNode right = sortList(tmp);

        // 最后两个节点组成链表 返回
        ListNode h = new ListNode(0);
        ListNode res = h;

        while (left != null && right != null) {
            if (left.val < right.val) {
                h.next = left;
                left = left.next;
            } else {
                h.next = right;
                right = right.next;
            }
            h = h.next;
        }
        h.next = left != null ? left : right;
        return res.next;
    }


    /**
     * #15 三数之和
     * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。

     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) throws InterruptedException {
        if (nums == null || nums.length < 3) {
            return Collections.emptyList();
        }

        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> anw = new ArrayList<>();

        // 保证最开始的一个数不一样
        for (int first = 0; first < n; first++) {
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }

            int third = n - 1;
            int target = -nums[first];
            // 找第二个数
            for (int second = first + 1; second < n; second++) {
                if (second > first + 1 && nums[second] == nums[second - 1]) {
                    continue;
                }
                Semaphore a = new Semaphore(1);
                a.acquire();

                while (second < third && nums[second] + nums[third] > target) {
                    third--;
                }

                if (second == third) {
                    break;
                }

                if (nums[second] + nums[third] == target) {
                    anw.add(Arrays.asList(nums[first], nums[second], nums[third]));
                }
            }
        }
        return anw;
    }
}