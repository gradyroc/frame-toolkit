package left_01_sort;

import static util.SortUtil.*;

/**
 * @author grady
 * @version 1.0, on 16:55 2021/5/5.
 */
public class Code_06_BucketSort {

    // 只适用于0~200 之间的value
    public static void bucketSort(int[] arr) {
        if (!checkArryNeedSort(arr)) {
            return;
        }

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        // 创建max+1 个桶
        int[] bucket = new int[max + 1];
        //将每个元素都放入桶中
        for (int i = 0; i < arr.length; i++) {
            bucket[arr[i]]++;
        }
        int i = 0;
        for (int j = 0; j < bucket.length; j++) {
            //依次移出桶中的数据
            while (bucket[j]-- > 0) {
                arr[i++] = j;
            }
        }


    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 150;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            bucketSort(arr1);
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
        bucketSort(arr);
        printArray(arr);
    }
}
