package left_01_sort;

import util.SortUtil;

import javax.jws.soap.SOAPBinding;

/**
 * @author grady
 * @version 1.0, on 21:25 2021/5/3.
 * <p>
 * 堆排序
 */
public class Code_03_HeapSort {

    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        //建立堆，大根堆，if > 往上浮
        for (int i = 0; i < arr.length; i++) {//O(N)
            heapInsert(arr, i);//O(logN)
        }

        int size = arr.length;
        swap(arr, 0, --size);//0 位置和堆最后一个数字交换
        while (size > 0) {//O(N)
            //每次将相对大的数据放到长度为size的数组最后一个元素，
            // 形成从小到大的有序数组
            //交换之后，将0号元素下沉
            heapfy(arr, 0, size);//O(logN)
            swap(arr, 0, --size);//O(1)
        }
    }

    public static void heapSortUpdate(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        //如果已有数组，可以直接开始heapfy
        for (int i = arr.length-1; i >=0; i--) {//O(N)
            heapfy(arr, i,arr.length);//O(logN)
        }

        int size = arr.length;
        swap(arr, 0, --size);//0 位置和堆最后一个数字交换
        while (size > 0) {//O(N)
            //每次将相对大的数据放到长度为size的数组最后一个元素，
            // 形成从小到大的有序数组
            //交换之后，将0号元素下沉
            heapfy(arr, 0, size);//O(logN)
            swap(arr, 0, --size);//O(1)
        }
    }

    /**
     * 取掉堆根节点之后的堆调整
     *
     * @param arr
     * @param index
     * @param size
     */
    private static void heapfy(int[] arr, int index, int size) {
        //
        int left = 2 * index + 1;//左孩子下标

        //下方还有孩子的时候
        while (left < size) {
            //找到左右子节点的最大值下标
            //右孩子胜出的条件：有右孩子且右孩子大于左孩子
            int largest = (left + 1) < size && arr[left + 1] > arr[left]
                    ? left + 1 : left;

            // 父节点和交大孩子节点之间的大小比较
            //找到index 和 left 和right 的最大值
            largest = arr[largest] > arr[index] ? largest : index;

            if (largest == index) {
                break;
            }
            swap(arr, index, largest);
            index = largest;
            left = 2 * index + 1;

        }

    }

    /**
     * 当前的数 和 父位置的数比较，并交换，并继续向上窜到不能窜为止
     *-1/2 ==0
     * @param arr
     * @param i
     */
    private static void heapInsert(int[] arr, int i) {
        while (arr[i] > arr[(i - 1) / 2]) {
            SortUtil.swap(arr, i, (i - 1) / 2);
            i = (i - 1) / 2;
        }

    }

    /**
     * @Author: grady
     * @Description: 对于下标相同的两个数交换的时候，异或的方式会有bug，可做如下改进
     * @Params:
     * @Date:
     * @return:
     */
    public static void swap(int[] arr, int i, int j) {
        if (i == j) {
            return;
        }
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int masSize = 100;
        int maxValue = 100;
        boolean succeed = true;

        for (int i = 0; i < testTime; i++) {
            int[] arr1 = SortUtil.generateRandomArray(masSize, maxValue);
            int[] arr2 = SortUtil.copyArray(arr1);
            heapSort(arr1);
            SortUtil.comparator(arr2);
            if (!SortUtil.isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = SortUtil.generateRandomArray(masSize, maxValue);
        SortUtil.printArray(arr);
        heapSort(arr);
        SortUtil.printArray(arr);
    }

}











