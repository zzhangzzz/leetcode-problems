package special;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhang.xu
 * email nagisaww.zhang@beibei.com
 * 2020/12/10 10:04 上午
 * info :
 */
public class Solutions {
    /**
     * #860 找零
     * 每一杯柠檬水的售价为 5 美元。
     * 顾客排队购买你的产品，（按账单 bills 支付的顺序）一次购买一杯。
     * 每位顾客只买一杯柠檬水，然后向你付 5 美元、10 美元或 20 美元。你必须给每个顾客正确找零，也就是说净交易是每位顾客向你支付 5 美元。
     输入：[5,5,5,10,20]
     输出：true
     *
     * 这题跟找零钱没关系 关键是要能返回正确的货币
     * 所以要维护 5，10的数量值
     * @param bills
     * @return
     */
    public static boolean lemonadeChange(int[] bills) {
        int five = 0, ten = 0;
        for (int bill : bills) {
            if (bill == 5) {
                five ++;
            } else if (bill == 10) {
                if (five == 0) {
                    return false;
                }
                five --;
                ten ++;
            } else {
                if (five > 0 && ten > 0) {
                    five --;
                    ten--;
                } else if (five >= 3) {
                    five -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] test = {5,5,5,10,20};
        System.out.println(lemonadeChange(test));

        test = new int[]{5, 5, 10};
        System.out.println(lemonadeChange(test));

        test = new int[]{10, 10};
        System.out.println(lemonadeChange(test));

        test = new int[]{5,5,10,10,20};
        System.out.println(lemonadeChange(test));

    }


    /**
     * 移除所有逗号，小数点和空格，得到一个字符串S。返回所有可能的原始字符串到一个列表中。
     * 输入: "(123)"
     * 输出: ["(1, 23)", "(12, 3)", "(1.2, 3)", "(1, 2.3)"]
     * 输入: "(00011)"
     * 输出:  ["(0.001, 1)", "(0, 0.011)"]
     * @param s
     * @return
     *
     * 我们首先把这个二维坐标分成两部分，前一部分表示 x 坐标，后一部分表示 y 坐标。
     * 例如当给出的二维坐标为 (1234) 时，我们可以把它分成 1, 234，12, 34 和 123, 4 三种情况
     * 随后对于每一部分，我们再考虑是否可以添加小数点以及在哪里添加小数点。
     * 例如，对于 123，合法的坐标有 1.23，12.3 和 123。
     *
     * 在处理每一部分时，我们需要将出现多余 0 的不合法的坐标去除。
     * 如果我们不添加小数点，那么这个坐标不能有前导 0；
     * 如果我们在某个位置添加小数点，那么整数部分不能有前导 0，小数部分的末尾也不能有 0。
     */
    public List<String> ambiguousCoordinates(String s) {
        List<String> res = new ArrayList<>();
        for (int i = 2; i < s.length() - 1; i++) {
            for (String left : make(s, 1, i)) {
                for (String right : make(s, i, s.length() - 1)) {
                    res.add("(" + left + "," + right + ")");
                }
            }
        }
        return res;
    }

    private List<String> make(String s, int i, int j) {
        List<String> ans = new ArrayList<>();
        for (int d = 1; d <= j- 1; d++) {
            String left = s.substring(i, i + d);
            String right = s.substring(i + d, j);
            if ((!left.startsWith("0") || "0".equals(left)) && !right.startsWith("0")){
                ans.add(left + (d < j - i ? "." : "") + right);
            }
        }
        return ans;
    }


}
