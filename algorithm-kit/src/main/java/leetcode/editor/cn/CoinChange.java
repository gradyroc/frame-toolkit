package leetcode.editor.cn;

//You are given an integer array coins representing coins of different denominat
//ions and an integer amount representing a total amount of money. 
//
// Return the fewest number of coins that you need to make up that amount. If th
//at amount of money cannot be made up by any combination of the coins, return -1.
// 
//
// You may assume that you have an infinite number of each kind of coin. 
//
// 
// Example 1: 
//
// 
//Input: coins = [1,2,5], amount = 11
//Output: 3
//Explanation: 11 = 5 + 5 + 1
// 
//
// Example 2: 
//
// 
//Input: coins = [2], amount = 3
//Output: -1
// 
//
// Example 3: 
//
// 
//Input: coins = [1], amount = 0
//Output: 0
// 
//
// 
// Constraints: 
//
// 
// 1 <= coins.length <= 12 
// 1 <= coins[i] <= 231 - 1 
// 0 <= amount <= 104 
// 
// Related Topics 广度优先搜索 数组 动态规划 
// 👍 1803 👎 0

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 322 凑零钱问题,动态规划
 *      1：存在「重叠子问题」
 *      2：具备「最优子结构」
 *      3：列出正确的「状态转移方程」
 * <p>
 *      明确「状态」 -> 定义 dp 数组/函数的含义 -> 明确「选择」-> 明确 base case。
 * <p>
 * step1：先确定「状态」，也就是原问题和子问题中变化的变量。
 *          由于硬币数量无限，所以唯一的状态就是目标金额amount。
 * step2：确定dp函数的定义：函数 dp(n)表示，当前的目标金额是n，至少需要dp(n)个硬币凑出该金额。
 * <p>
 * step3：确定「选择」并择优
 *      也就是对于每个状态，可以做出什么选择改变当前状态。
 *      etc：无论当的目标金额是多少，选择就是从面额列表coins中选择一个硬币，然后目标金额就会减少：
 * # 伪码框架
 * def coinChange(coins: List[int], amount: int):
 *  # 定义：要凑出金额 n，至少要 dp(n) 个硬币
 *      def dp(n):
 *      # 做选择，需要硬币最少的那个结果就是答案
 *          for coin in coins:
 *              res = min(res, 1 + dp(n - coin))
 *          return res
 *      # 我们要求目标金额是 amount
 *      return dp(amount)
 *
 * step4：最后明确 base case，
 *      显然目标金额为 0 时，所需硬币数量为 0；
 *      当目标金额小于 0 时，无解，返回 -1：
 * <p>
 * def coinChange(coins: List[int], amount: int):
 *
 *      def dp(n):
 *          # base case
 *          if n == 0: return 0
 *          if n < 0: return -1
 *          # 求最小值，所以初始化为正无穷
 *          res = float('INF')
 *          for coin in coins:
 *              subproblem = dp(n - coin)
 *              # 子问题无解，跳过
 *              if subproblem == -1: continue
 *              res = min(res, 1 + subproblem)
 *
 *          return res if res != float('INF') else -1
 *
 *      return dp(amount)
 */

public class CoinChange {
    public static void main(String[] args) {
        Solution solution = new CoinChange().new Solution();

    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {

        HashMap<Integer,Integer> memo = new HashMap();

        public int coinChange(int[] coins, int amount) {

            if (amount ==0){
                return 0;
            }
            if (amount<0 ){
                return -1;
            }
            int res = Integer.MAX_VALUE;

//            查备忘录，避免重复计算
            if (memo.containsKey(amount)){
                return memo.get(amount);
            }
            for (int coin : coins) {
                int subPro = coinChange(coins,amount-coin);

                if (subPro == -1){
                    continue;
                }
                res = Math.min(res,1+subPro);

            }
//           记入备忘录
            memo.put(amount,res !=Integer.MAX_VALUE? res:-1);
            return memo.get(amount);
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}