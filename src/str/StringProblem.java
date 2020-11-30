package str;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhang.xu
 * email nagisaww.zhang@beibei.com
 * 2020/11/18 4:53 下午
 * info : 字符串问题
 */
public class StringProblem {

    /**
     * #344  编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 char[] 的形式给出。
     * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
     * 输入：["h","e","l","l","o"]
     * 输出：["o","l","l","e","h"]
     */
    public void reverseString(char[] s) {
        for (int i = 0; i < s.length / 2; i++) {
            char tmp = s[s.length - i - 1];
            s[s.length - i - 1] = s[i];
            s[i] = tmp;
        }
    }

    /**
     * #349 给定两个数组，编写一个函数来计算它们的交集
     * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
     * 输出：[2]
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        Map<Integer, Integer> contactMap = new HashMap<>(Math.max(nums1.length, nums2.length));
        int[] longarr = nums1.length > nums2.length ? nums1 : nums2;
        int[] shotarr = nums1.length > nums2.length ? nums2 : nums1;
        for (int i : shotarr) {
            contactMap.putIfAbsent(i, 1);
        }
        for (int i : longarr) {
            contactMap.computeIfPresent(i, (key, val) -> val = 0);
        }
        int res[] = new int[longarr.length];
        int i = 0;
        for (int key : contactMap.keySet()) {
            if (contactMap.get(key) == 0) {
                res[i] = key;
                i++;
            }
        }
        return Arrays.copyOf(res, i);
    }


    /**
     * 给定一个字符串，你的任务是计算这个字符串中有多少个回文子串。
     * 输入："abc"
     * 输出：3
     * 解释：三个回文子串: "a", "b", "c"
     *
     * 输入："aaa"
     * 输出：6
     * 解释：6个回文子串: "a", "a", "a", "aa", "aa", "aaa"
     * @param s
     * @return
     */
    public int countSubstrings(String s) {
        /**
         * 从中心点向两边扩展 挨个计算是否是回文串
         */
        int length = s.length(), ans = 0;
        for (int i = 0; i < 2 * length - 1; i++) {
            int left = i / 2, right = i / 2 + i % 2;
            while (left >= 0 && right < length && s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
                ans++;
            }
        }
        return ans;
    }


    /**
     * #767 重构字符串
     * 给定一个字符串S，检查是否能重新排布其中的字母，使得两相邻的字符不同。
     * 输入: S = "aab"
     * 输出: "aba"
     * @param S
     * @return
     */
    public String reorganizeString(String S) {
        if (S.length() < 2) {
            return S;
        }
        int[] counts = new int[26];
        int max = 0;
        int length = S.length();
        for (int i = 0; i < length; i++) {
            char c = S.charAt(i);
            counts[c - 'a']++;
            max = Math.max(max, counts[c - 'a']);
        }
        if (max > (length + 1) / 2) {
            return "";
        }
        char[] resArray = new char[length];
        // 奇数下标 和 偶数下标
        int evenIndex = 0, oddIndex = 1;
        int halfLength = length / 2;
        for (int i = 0; i < 26; i++) {
            char c = (char) ('a' + i);
            // 当 n 是奇数且出现最多的字母的出现次数是 (n+1)/2时，出现次数最多的字母必须全部放置在偶数下标，
            while (counts[i] > 0 && counts[i] <= halfLength && oddIndex < length) {
                resArray[oddIndex] = c;
                counts[i]--;
                oddIndex += 2;
            }
            while (counts[i] > 0) {
                resArray[evenIndex] = c;
                counts[i]--;
                evenIndex += 2;
            }
        }
        return new String(resArray);
    }
}
