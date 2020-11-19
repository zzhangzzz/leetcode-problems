package special;

/**
 * @author zhang.xu
 * email nagisaww.zhang@beibei.com
 * 2020/11/19 2:02 下午
 * info :
 */
public class IPTransStr {

    /**
     * 粉笔4面技术面的算法题 要求把ip转成一个int存储
     *
     * 想法是首先对ip进行拆分，然后按位存储
     */
    public int ipToInt(String ip) {
        String[] ips = ip.split("\\.");
        int ipFour = 0;
        for (String s : ips) {
            Integer ip4 = Integer.parseInt(s);
            ipFour = (ip4 << 8) | ip4;
        }
        return ipFour;
    }

    public String intToIp(int ip) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 3; i >= 0; i--) {
            int ipa = (ip >> (8 * i)) & (0xff);
            stringBuilder.append(ipa + ".");
        }
        return stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length()).toString();
    }
}
