package left_01_sort;


import util.SortUtil;

/**
 * @author grady
 * @version 1.0, on 18:55 2021/5/3.
 * <p>
 * 选择排序
 */
public class Code_02_SelectionSort {

    public static void selectionSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 0; i < arr.length - 1; i++) {
            //从第i个元素开始选择一个最小的元素放入i位置,记住下标即可
            int index = i;
            for (int j = i + 1; j < arr.length; j++) {
                index = arr[j] < arr[index] ? j : index;
            }
            SortUtil.swap(arr,i,index);

        }
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = SortUtil.generateRandomArray(maxSize, maxValue);
            int[] arr2 = SortUtil.copyArray(arr1);
            selectionSort(arr1);
            SortUtil.comparator(arr2);
            if (!SortUtil.isEqual(arr1, arr2)) {
                succeed = false;
                SortUtil.printArray(arr1);
                SortUtil.printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = SortUtil.generateRandomArray(maxSize, maxValue);
        SortUtil.printArray(arr);
        selectionSort(arr);
        SortUtil.printArray(arr);
    }
}
