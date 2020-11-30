package array;

/**
 * @author zhang.xu
 * email nagisaww.zhang@beibei.com
 * 2020/11/30 3:29 下午
 * info :
 */
public class QuickSort {

    public void quickSort(int[] arr, int low, int high) {
        int i,j,tmp,t;
        if (low > high) {
            return;
        }
        i = low;
        j = high;
        tmp = arr[low];

        while (i < j) {
            // 看右边
            while (tmp <= arr[j] && i < j) {
                j--;
            }

            // 再从左往右找
            while (tmp >= arr[i] && i < j) {
                i++;
            }

            // 满足条件则交换
            if (i < j) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }
        }

        arr[low] = arr[i];
        arr[i] = tmp;
        // 递归左半边
        quickSort(arr, low, j - 1);
        // 递归右半边
        quickSort(arr, j + 1, high);
    }

}
