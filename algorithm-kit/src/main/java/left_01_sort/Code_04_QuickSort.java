package left_01_sort;

import java.util.Stack;

import static util.SortUtil.*;

/**
 * @author grady
 * @version 1.0, on 0:47 2021/5/5.
 */
public class Code_04_QuickSort {

    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int l, int r) {
        if (l < r) {
            //区间内随机一个数据和right 交换
            swap(arr, l + (int) (Math.random() * (r - l + 1)), r);
            // 返回两个区域，小于等于和大于的边界下标，如果中间有相等的数据即可不用考察了
            int[] p = partition(arr, l, r);
            quickSort(arr, l, p[0] - 1);
            quickSort(arr, p[1] + 1, r);
        }
    }

    /**
     * @Author: grady
     * @Description: 返回小于等于和大于的两个区间的边界下标，小于等于的右边界，和大于的左边界
     * @Params:
     * @Date:
     * @return:
     */
    private static int[] partition(int[] arr, int l, int r) {
        //划分，首先记录左边区间的最右的下标，和右边大于区间的最左下标
        int less = l - 1;
        int more = r;

        while (l < more) {
            if (arr[l] < arr[r]) {
                //此时判断的是l位置的值
                //如果l 值 < r值，左边区域的右边界+1位置的数据和 l位置交换，且l+1
                //即将小于等于区间的下一个数和l位置交换，l++以进入下次循环
                swap(arr, ++less, l++);
            } else if (arr[l] > arr[r]) {
                //如果l值 > r值，则右边区域的左边界-1位置的数据和l位置交换
                //因为从右边区域左边界-1位置换到l位置之后，,大于区域-1，扩大区域
                // 还没进行过数据比较，所以下标l不变，进行下一次比较

                swap(arr, --more, l);
            } else {
                // 如果相等，比如一开始l=0，less=-1, 相当于0 和0 交换，l ++
                l++;
            }
        }
        //最后将大于区间的第一个数和r交换，划分值就应该放在more位置
        swap(arr, more, r);
        return new int[]{less + 1, more};
    }

    private static int myPartition(int[] arr, int l, int r) {
        //划分，首先记录左边区间的最右的下标，和右边大于区间的最左下标
        int less = l - 1;
        int more = r;

        while (l < r) {
            if (arr[l] < arr[r]) {
                //此时判断的是l位置的值
                //如果l 值 < r值，左边区域的右边界+1位置的数据和 l位置交换，且l+1
                //即将小于等于区间的下一个数和l位置交换，l++以进入下次循环
                swap(arr, ++less, l++);
            } else {
                // 如果相等，比如一开始l=0，less=-1, 相当于0 和0 交换，l ++
                l++;
            }
        }
        //最后将大于区间的第一个数和r交换，划分值就应该放在more位置
       swap(arr, less+1 , r);
        return less+1;
    }
    private static void quickSortNotR(int[] arr, int left, int right) {
        if (!checkArryNeedSort(arr)) {
            return;
        }
        //先进后出
        Stack<Integer> s = new Stack<>();
        s.push(left);
        s.push(right);
        while (!s.isEmpty()) {
            int r = s.pop();
            int l = s.pop();
            int index = myPartition(arr, l, r);
            if ((index - 1) > l) {
                s.push(l);
                s.push(index - 1);
            }
            if ((index + 1) < r) {
                s.push(index+1);
                s.push(r);
            }
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
//            quickSort(arr1);
            quickSortNotR(arr1,0, arr1.length - 1);
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
        quickSort(arr);
        printArray(arr);
    }


}
