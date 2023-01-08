package left_01_sort;

import java.util.PriorityQueue;

/**
 * @author grady
 * @version 1.0, on 1:49 2023/1/9.
 */
public class Code03_SortArrayDistanceLessK {

    /**
     * 一个相对有序的数组，每个数字排序之后的变化位置不超过K
     *
     * @param arr
     * @param k
     */
    public void sortArrayDistanceLessK(int[] arr, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();

        int index = 0;
        for (; index <= Math.min(arr.length, k); index++) {
            heap.add(arr[index]);
        }

        int i = 0;
        for (; index < arr.length; i++, index++) {
            heap.add(arr[index]);
            arr[i] = heap.poll();
        }

        while (!heap.isEmpty()){
            arr[i++]=heap.poll();
        }
    }
}
