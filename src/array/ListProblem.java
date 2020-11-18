package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


}
