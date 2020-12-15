package array;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhang.xu
 * email nagisaww.zhang@beibei.com
 * 2020/12/15 9:10 上午
 * info :
 */
public class NumberProblem {

    /**
     * 给定一个非负整数 N，找出小于或等于 N 的最大的整数，
     * 同时这个整数需要满足其各个位数上的数字是单调递增。
     *输入: N = 10
     * 输出: 9
     * 输入: N = 1234
     * 输出: 1234
     * 输入: N = 332
     * 输出: 299
     * @param n
     * @return
     */
    public static int monotoneIncreasingDigits(int n) {

        char[] strN = Integer.toString(n).toCharArray();
        int i = 1;
        while (i < strN.length && strN[i - 1] < strN[i]) {
            i++;
        }
        // 说明中间有大小不顺序的
        if (i < strN.length) {
            while (i > 0 && strN[i - 1] > strN[i]) {
                strN[i - 1] --;
                i --;
            }
            for (i += 1; i < strN.length; ++i) {
                strN[i] = '9';
            }
        }

        return Integer.parseInt(new String(strN));
    }

    public static void main(String[] args) {
        monotoneIncreasingDigits(1092);
    }

}
