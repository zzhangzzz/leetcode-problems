package str;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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


    /**
     * # 3 给定一个字符串， 找到不含重复字符的最长子串长度
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int max = 0;
        // 快慢指针i、j 扫描一遍字符串，已在hash集合 则尝试删除
        for (int i = 0, j = 0; j < s.length(); j++) {
            while (set.contains(s.charAt(j))) {
                set.remove(s.charAt(i));
                i++;
            }
            // 更新max
            set.add(s.charAt(j));
            max = Math.max(max, set.size());
        }
        return max;
    }


    /**
     * 将数组拆分成斐波那契序列
     * 输入："123456579"
     * 输出：[123,456,579]
     *
     * 输入: "11235813"
     * 输出: [1,1,2,3,5,8,13]
     *
     * @param S
     * @return
     */
    public List<Integer> splitIntoFibonacci(String S) {
        List<Integer> res = new ArrayList<>();

        backTrackFb(res, S, S.length(), 0, 0, 0);
        return res;
    }

    private boolean backTrackFb(List<Integer> res, String s, int length, int index, int sum, int pre) {
        if (index == length) {
            return res.size() >= 3;
        }

        int curLong = 0;
        for (int i = index; i < length; i++) {
            // fb 数列中间不能有0
            if (i > index && s.charAt(index) == '0') {
                break;
            }

            curLong = curLong * 10 + s.charAt(i) - '0';
            if (curLong > Integer.MAX_VALUE) {
                break;
            }

            int cur = (int) curLong;
            if (res.size() >= 2) {
                if (cur < sum) {
                    continue;
                } else if (cur > sum) {
                    break;
                }
            }
            res.add(cur);
            if (backTrackFb(res, s, length, i + 1, cur + pre, cur)) {
                return true;
            } else {
                res.remove(res.size() - 1);
            }
        }
        return false;
    }


    /**
     * 给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。
     * 输入: ["eat", "tea", "tan", "ate", "nat", "bat"]
     * 输出:
     * [
     *   ["ate","eat","tea"],
     *   ["nat","tan"],
     *   ["bat"]
     * ]
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagrams(String[] strs) {

        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            int[] counts = new int[26];
            int length = s.length();
            for (int i = 0; i < length; i++) {
                counts[s.charAt(i) - 'a']++;
            }

            // 每个出现次数大于0 和出现次数的顺序组成字符串 作为hashkey
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                if (counts[i] != 0) {
                    sb.append((char) ('a' + i));
                    sb.append(counts[i]);
                }
            }
            String key = sb.toString();
            List<String> list = map.getOrDefault(key, new ArrayList<>());
            list.add(s);
            map.put(key, list);
        }

        return new ArrayList<>(map.values());
    }



}
