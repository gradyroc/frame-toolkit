package leetcode.editor.cn;

//给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。 
//
// 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [-4,-1,0,3,10]
//输出：[0,1,9,16,100]
//解释：平方后，数组变为 [16,1,0,9,100]
//排序后，数组变为 [0,1,9,16,100] 
//
// 示例 2： 
//
// 
//输入：nums = [-7,-3,2,3,11]
//输出：[4,9,9,49,121]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 104 
// -104 <= nums[i] <= 104 
// nums 已按 非递减顺序 排序 
// 
//
// 
//
// 进阶： 
//
// 
// 请你设计时间复杂度为 O(n) 的算法解决本问题 
// 
// Related Topics 数组 双指针 排序 
// 👍 482 👎 0

public class SquaresOfASortedArray{
    public static void main(String[] args) {
        Solution solution = new SquaresOfASortedArray().new Solution();
        
    }

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 非递减即，i<j  ,必有 a[i] <= a[j]
     * @param nums
     * @return
     */
    public int[] sortedSquares(int[] nums) {
        int[] ans = new int[nums.length];
        int n = nums.length;

        /**
         * 利用 i和j 双指针，加额外数组
         */
        for (int i = 0,j=n-1,pos = n-1; i <=j ;) {
            int ai=nums[i]*nums[i];
            int aj=nums[j]*nums[j];
            if (ai >  aj){
                ans[pos]= ai;
                ++i;
            }
            if (ai <= aj){
                ans[pos]= aj;
                --j;
            }
            --pos;
        }
        return ans;
    }
}




//leetcode submit region end(Prohibit modification and deletion)

}