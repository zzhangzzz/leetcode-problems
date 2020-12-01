package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

/**
 * @author zhang.xu
 * email nagisaww.zhang@beibei.com
 * 2020/11/18 5:19 下午
 * info : 数组问题
 */
public class ListProblem {

    /**
     * #136  只出现一次的数字
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素
     * 输入: [4,1,2,1,2]
     * 输出: 4
     */
    public int singleNumber(int[] nums) {
        Map<Integer, Integer> numsMap = new HashMap<>();
        for (int i : nums) {
            if (numsMap.containsKey(i)) {
                numsMap.put(i, 2);
            } else{
                numsMap.put(i, 1);
            }
        }
        return numsMap.entrySet().stream().filter(i -> i.getValue() == 1).findAny().get().getKey();
    }

    /**
     * # 122 买卖股票的最佳时机 II
     * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
     * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）
     * 输入: [7,1,5,3,6,4]
     * 输出: 7
     * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     *      随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3
     */
    public int maxProfit(int[] prices) {
        int total = 0;
        for (int i = 0; i < prices.length - 1; i++ ) {
            if (prices[i] < prices[i + 1]) {
                total += prices[i + 1] - prices[i];
            }
        }
        return total;
    }

    /**
     * #169 多数元素
     * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
     * 输入: [2,2,1,1,1,2,2]
     * 输出: 2
     */
    public int majorityElement(int[] nums) {
        // 如果一个数超过 n/2 那么出现次数一定超过一半 从最开始出发 挨个判断 出现次数小于0就换
        int hold = nums[0];
        int time = 1;
        for (int i = 1; i < nums.length; i++) {
            if (time == 0) {
                hold = nums[i];
                time = 1;
                continue;
            }
            if (nums[i] == hold) {
                time ++;
            } else {
                time --;
            }

        }
        return hold;
    }



    /**
     * 回溯模板
     * result = []
     * func backTrack(路径， 选择列表）{
     *     if 满足条件
     *          result.add
     *          return
     *     for 选择 in 列表
     *           选择
     *           backTrack
     *           撤销操作
     * }
     */


    /**
     * #78 子集
     * 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
     * 输入: nums = [1,2,3]
     * 输出:
     * [
     *   [3],
     *   [1],
     *   [2],
     *   [1,2,3],
     *   [1,3],
     *   [2,3],
     *   [1,2],
     *   []
     * ]
     */
    private List<List<Integer>> output = new ArrayList<>();
    int totalNum, nowSize;
    public List<List<Integer>> subsets(int[] nums) {
        // 通过回溯法 逐步增加结果数组集的解
        totalNum = nums.length;
        for (nowSize = 0; nowSize < totalNum + 1; nowSize++) {
            subsetBackTrack(0, new ArrayList<>(), nums);
        }
        return output;
    }

    private void subsetBackTrack(int first, List<Integer> cur, int[] nums) {
        if (cur.size() == nowSize) {
            output.add(new ArrayList<>(cur));
        }
        for (int i = first; i < totalNum; i++) {
            cur.add(nums[i]);
            subsetBackTrack(i + 1, cur, nums);
            cur.remove(cur.size() - 1);
        }
    }


    /**
     * #46 全排列
     * 给定一个 没有重复 数字的序列，返回其所有可能的全排列。
     * 输入: [1,2,3]
     * 输出:
     * [
     *   [1,2,3],
     *   [1,3,2],
     *   [2,1,3],
     *   [2,3,1],
     *   [3,1,2],
     *   [3,2,1]
     * ]
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        ArrayList<Integer> out = new ArrayList<>();
        for (int num : nums) {
            out.add(num);
        }
        int n = nums.length;
        permuteBackTrack(n, 0, result, out);

        return result;
    }
    private void permuteBackTrack(int n, int nowIndex, List<List<Integer>> result, ArrayList<Integer> out) {
        if (nowIndex == n) {
            result.add(new ArrayList<>(out));
        }
        for (int i = nowIndex; i < n; i++) {
            // 互换数组
            Collections.swap(out, nowIndex, i);

            permuteBackTrack(n, nowIndex + 1, result, out);

            // 撤销 进行回溯
            Collections.swap(out, nowIndex, i);
        }
    }


    /**
     * #714 买卖股票最佳时间含手续费
     * 给定一个整数数组 prices，其中第 i 个元素代表了第 i 天的股票价格 ；非负整数 fee 代表了交易股票的手续费用。
     * 你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
     * 返回获得利润的最大值。
     *
     * 注意：这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费。
     * 输入: prices = [1, 3, 2, 8, 4, 9], fee = 2
     * 输出: 8
     * 解释: 能够达到的最大利润:
     * 在此处买入 prices[0] = 1
     * 在此处卖出 prices[3] = 8
     * 在此处买入 prices[4] = 4
     * 在此处卖出 prices[5] = 9
     * 总利润: ((8 - 1) - 2) + ((9 - 4) - 2) = 8.
     */
    public int maxProfit(int[] prices, int fee) {
        // cash 不持有股票时最大利润
        // hold 持有股票时最大利润
        int cash = 0, hold = -prices[0];
        for (int i = 1; i < prices.length; i++) {
            cash = Math.max(cash, hold + prices[i] - fee);
            hold = Math.max(hold, cash - prices[i]);
        }
        return cash;
    }


    /**
     * 找到未排序数组第K个最大的数
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        if (k == 0 || nums.length == 0) {
            return 0;
        }

        Queue<Integer> queue = new PriorityQueue<>((v1, v2) -> v1 - v2);
        for (int n : nums) {
            queue.add(n);
            if (queue.size() > k) {
                queue.poll();
            }
        }
        return queue.poll();
    }

    /**
     * 快排的划分操作，每次都可以确定一个最终元素的位置，如果某次划分结果是k的下标就找到了，否则往左 或者 往右找
     * @param nums
     * @param k
     * @return
     */
    public int findK_by_quick_sort(int[] nums, int k) {
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    private int quickSelect(int[] nums, int left, int right, int index) {
        int q = randomPartition(nums, left, right);
        if (q == index) {
            return nums[q];
        }
        return q < index ? quickSelect(nums, q + 1, right, index) : quickSelect(nums, left, q - 1, index);
    }

    private int randomPartition(int[] nums, int left, int right) {
        Random random = new Random();
        int i = random.nextInt(right - left) + 1;
        swap(nums, i, right);
        return partition(nums, left, right);
    }

    private int partition(int[] nums, int left, int right) {
        int x = nums[right], i = left - 1;
        for (int j = 1; j < right; j++) {
            if (nums[j] <= x) {
                swap(nums, ++i, j);
            }
        }
        swap(nums, i + 1, right);
        return i + 1;
    }

    private void swap(int[] nums, int left, int right) {
        int tmp = nums[left];
        nums[left] = nums[right];
        nums[right] = tmp;
    }


    public int find_k_by_heap(int[] nums, int k) {
        int heapSize = nums.length;
        buildHeap(nums, heapSize);
        for (int i = nums.length - 1; i >= nums.length - k + 1; i--) {
            swap(nums, 0, i);
            --heapSize;
            maxHeapify(nums, 0, heapSize);
        }
        return nums[0];
    }

    private void buildHeap(int[] nums, int heapSize) {
        for (int i = heapSize / 2; i >= 0; i--) {
            maxHeapify(nums, i, heapSize);
        }
    }

    private void maxHeapify(int[] nums, int i, int heapSize) {
        int left = i * 2 + 1, right = i * 2 + 2, largest = i;
        if (left < heapSize && nums[left] > nums[largest]) {
            largest = left;
        }
        if (right < heapSize && nums[right] > nums[largest]) {
            largest = right;
        }
        if (largest != i) {
            swap(nums, i, largest);
            maxHeapify(nums, largest, heapSize);
        }
    }


    /* #34给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
          如果数组中不存在目标值 target，返回 [-1, -1]。
          输入：nums = [5,7,7,8,8,10], target = 8
          输出：[3,4]
     */
    public int[] searchRange(int[] nums, int target) {
        int left = findByHalf(nums, target, true);
        int right = findByHalf(nums, target, false) - 1;
        if (left <= right && right < nums.length && nums[left] == target && nums[right] == target) {
            return new int[]{left, right};
        }
        return new int[]{-1, -1};
    }
    private int findByHalf(int[] nums, int target, boolean lower) {
        int left = 0, right = nums.length - 1, ans = nums.length;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] > target || (lower && nums[mid] >= target)) {
                right = mid - 1;
                ans = mid;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }

}
