package left_01_sort.code_02;

/**
 * @author grady
 * @version 1.0, on 1:57 2023/1/10.
 */
public class Code02_RadixSort {

    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        radixSort(arr, 0, arr.length - 1, maxBits(arr));
    }

    /**
     * 获取最大的数字的位数
     *
     * @param arr
     * @return
     */
    private static int maxBits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        int res = 0;
        while (max != 0) {
            res++;
            max /= 10;
        }
        return res;
    }

    /**
     * arr[L,R]排序
     *
     * @param arr
     * @param L
     * @param R
     * @param digit
     */
    private static void radixSort(int[] arr, int L, int R, int digit) {
        final int radix = 10;//十进制
        int i = 0, j = 0;

        //准备arr.length个数的辅助空间
        int[] bucket = new int[R - L + 1];
        for (int d = 0; d <= digit; d++) {//有多少位就进出多少次

            /**
             *10个空间
             * count[0] 当前位（d位） 是0的数字有多少个
             * count[1] 当前位（d位） 是（0和1）的数字有多少个
             * count[2] 当前位（d位） 是（0和1,和2）的数字有多少个
             * count[1] 当前位（d位） 是（0~i）的数字有多少个
             */

            int[] count = new int[radix];//count[0,9]
            for (i = L; i <= R; i++) {
                j = getDigit(arr[i], d);//取出d位的数字
                count[j]++;//计数，词频累计
            }

            //求累加和
            for (i = 1; i < radix; i++) {
                count[i] = count[i] + count[i - 1];
            }

            //从右往左，最右边的数字放到第多少位，相当于出桶，
            //相当于出桶了一次
            for (i = R; i >= L; i--) {
                j = getDigit(arr[i], d);
                bucket[count[j] - 1] = arr[i];// d位的数字，填入数字值-1的位置，
                count[j]--;// 词频计数-1
            }

            //然后辅助数组copy到原来的数组
            for (i = L, j = 0; i <= R; i++, j++) {
                arr[i] = bucket[j];
            }
        }
    }

    //获取数据x的第d位的下标
    private static int getDigit(int x, int d) {
        return ((x / ((int) Math.pow(10, d - 1))) % 10);
    }
}
