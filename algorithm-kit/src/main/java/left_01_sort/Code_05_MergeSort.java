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
