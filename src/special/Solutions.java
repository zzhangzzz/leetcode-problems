package special;

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

}
