package leetcode.editor.cn;

//The Fibonacci numbers, commonly denoted F(n) form a sequence, called the Fibon
//acci sequence, such that each number is the sum of the two preceding ones, start
//ing from 0 and 1. That is, 
//
// 
//F(0) = 0, F(1) = 1
//F(n) = F(n - 1) + F(n - 2), for n > 1.
// 
//
// Given n, calculate F(n). 
//
// 
// Example 1: 
//
// 
//Input: n = 2
//Output: 1
//Explanation: F(2) = F(1) + F(0) = 1 + 0 = 1.
// 
//
// Example 2: 
//
// 
//Input: n = 3
//Output: 2
//Explanation: F(3) = F(2) + F(1) = 1 + 1 = 2.
// 
//
// Example 3: 
//
// 
//Input: n = 4
//Output: 3
//Explanation: F(4) = F(3) + F(2) = 2 + 1 = 3.
// 
//
// 
// Constraints: 
//
// 
// 0 <= n <= 30 
// 
// Related Topics 递归 记忆化搜索 数学 动态规划 
// 👍 421 👎 0


/**
 * 502 斐波拉契数列
 */

import java.util.HashMap;

public class FibonacciNumber {
    public static void main(String[] args) {
        Solution solution = new FibonacciNumber().new Solution();

    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {


        public int fib(int n) {
            //备忘录方法
//            int[] memo = new int[n+1];
//            return fibRecall(n, memo);

//            dp 数组的迭代（递推）解法
            if (n == 0 || n == 1) {
                return n;
            }
//            int dp[]  = new int[n+1];
            //base case
            int dp1 = 1, dp2 = 0;

//            dp[0]=0;dp[1]=1;
            for (int i = 2; i <= n; i++) {
                int dpi = dp1 + dp2;
                //滚动更新
                dp2 = dp1;
                dp1 = dpi;
            }
            return dp1;
        }


        private int fibRecall(int n, int[] memo) {
            if (n == 0 || n == 1) {
                return n;
            }
            if (memo[n] != 0) {
                return memo[n];
            }
            memo[n] = fibRecall(n - 1, memo) + fibRecall(n - 2, memo);
            return memo[n];
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}