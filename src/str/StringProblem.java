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
}
