package leetcode.editor.cn;

//给定一个正整数，检查它的二进制表示是否总是 0、1 交替出现：换句话说，就是二进制表示中相邻两位的数字永不相同。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 5
//输出：true
//解释：5 的二进制表示是：101
// 
//
// 示例 2： 
//
// 
//输入：n = 7
//输出：false
//解释：7 的二进制表示是：111. 
//
// 示例 3： 
//
// 
//输入：n = 11
//输出：false
//解释：11 的二进制表示是：1011. 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 231 - 1 
// 
// Related Topics 位运算 
// 👍 193 👎 0

public class BinaryNumberWithAlternatingBits{
    public static void main(String[] args) {
        Solution solution = new BinaryNumberWithAlternatingBits().new Solution();
        
    }

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean hasAlternatingBits(int n) {

        //当且仅当输入 n 为交替位二进制数时，a 的二进制表示全为 1（不包括前导 0）
        //将 a 与 a+1 按位与，当且仅当 a 的二进制表示全为 1 时，结果为 0

        int a = n ^ (n>>1);
        return (a & (a+1)) == 0 ;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}