package left_01_sort;

import static util.SortUtil.*;

/**
 * @author grady
 * @version 1.0, on 15:44 2021/5/5.
 */
public class Code_05_MergeSort {

    public static void mergeSort(int[] arr) {
        if (!checkArryNeedSort(arr)) {
            return;
        }
        mergeSort(arr, 0, arr.length - 1);

    }

    private static void mergeSort(int[] arr, int l, int r) {
        if (l == r) {
            return;
        }
        //找到元素中间位置
        // 后面这一坨必须括起来
        int mid = l + ((r - l) >> 1);
        mergeSort(arr, l, mid);
        mergeSort(arr, mid + 1, r);
        merge(arr, l, mid, r);
    }

    private static void merge(int[] arr, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int i = 0;
        int p1 = l;
        int p2 = mid + 1;
        //两个数组拷贝到辅助数组，谁小谁在前面
        while (p1 <= mid && p2 <= r) {
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        // 保持稳定，先拷贝left部分
        while (p1 <= mid) {
            help[i++] = arr[p1++];
        }
        while (p2 <= r) {
            help[i++] = arr[p2++];
        }
        //拷贝回目标数组
        for (i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
    }

    /**
     * 小和问题：
     * 一个数组，某个数左边的更小的数之和组成数组，叫小和数组，并求和
     * 排序不能省，且如果左组和右组的数相等，一定要先拷贝右组的数据
     *etc：【1,3,4,2,5】,3左边比3小的数：1；4左边***：1,3：小和为1+3=4
     * 小和为所有小和之和
     * @param
     */
    public static int smallSum(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }
        int mid = l + ((r - l) >> 1);
        return smallSum(arr, l, mid)
                + smallSum(arr, mid + 1, r)
                + mergeSmallSum(arr, l, mid, r);
    }

    private static int mergeSmallSum(int[] arr, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int i = 0;
        int p1 = l;
        int p2 = mid + 1;
        int res = 0;
        //两个数组拷贝到辅助数组，谁小谁在前面
        while (p1 <= mid && p2 <= r) {
            //计算arr[p1]会产生多少个小和的
            res += arr[p1] < arr[p2] ? (r - p2 + 1) * arr[p1] : 0;
            help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        // 保持稳定，先拷贝left部分
        while (p1 <= mid) {
            help[i++] = arr[p1++];
        }
        while (p2 <= r) {
            help[i++] = arr[p2++];
        }
        //拷贝回目标数组
        for (i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
        return res;
    }

    /**
     * 逆序对问题，在一个数组中，左边的数如果比右边的数大，则两个数构成一个逆序对，打印所有的逆序对
     * @param args
     */

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            mergeSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        mergeSort(arr);
        printArray(arr);
    }
}
