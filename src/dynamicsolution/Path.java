package dynamicsolution;

import java.util.Arrays;

/**
 * @author zhang.xu
 * email nagisaww.zhang@beibei.com
 * 2020/12/9 9:30 上午
 * info :
 */
public class Path {

    /**m x n 网格的左上角 每次向右或者下移动一步 共有多少种可能
     * m = 3, n = 2
     * 输出: 3
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {
        int[][] res = new int[m][n];
        Arrays.fill(res[0], 1);

        for (int i = 1; i < m; i++) {
            res[i][0] = 1;
            for (int j = 1; j < n; j++) {
                res[i][j] = res[i- 1][j] + res[i][j - 1];
            }
        }
        return res[m - 1][n - 1];
    }

}
