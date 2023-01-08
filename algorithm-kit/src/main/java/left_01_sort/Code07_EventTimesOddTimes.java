package left_01_sort;

/**
 * @author grady
 * @version 1.0, on 2:00 2023/1/6.
 */
public class Code07_EventTimesOddTimes {


    /**
     * 其他数都出现偶数次，有两个数出现奇数次，找出这2个数
     * @param arr
     */
    public static void printOddTimesNum2(int[] arr) {

        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        /**
         * eor = a^b
         * eor !=0
         * eor 必然有一个位置上是1
         *
         */
        //提取出最右侧的1
        int rightOne = eor & (~eor + 1);

        int onlyOne = 0;
        for (int cur : arr) {
            //rightOne 位上位0  或者1 的才异或
            if ((cur & rightOne) == 0) {
                onlyOne ^= cur;
            }
        }

        int anotherOne  = eor ^onlyOne;

        System.out.println(onlyOne+" "+ anotherOne);
    }
}
