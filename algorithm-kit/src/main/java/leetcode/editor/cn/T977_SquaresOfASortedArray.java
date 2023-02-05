
package leetcode.editor.cn;
/**
给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。 

 
 

 

 示例 1： 

 
输入：nums = [-4,-1,0,3,10]
输出：[0,1,9,16,100]
解释：平方后，数组变为 [16,1,0,9,100]
排序后，数组变为 [0,1,9,16,100] 

 示例 2： 

 
输入：nums = [-7,-3,2,3,11]
输出：[4,9,9,49,121]
 

 

 提示： 

 
 1 <= nums.length <= 10⁴ 
 -10⁴ <= nums[i] <= 10⁴ 
 nums 已按 非递减顺序 排序 
 

 

 进阶： 

 
 请你设计时间复杂度为 O(n) 的算法解决本问题 
 

 Related Topics数组 | 双指针 | 排序 

 👍 713, 👎 0bug 反馈 | 使用指南 | 更多配套插件 

*/


public class T977_SquaresOfASortedArray{
    public static void main(String[] args){
    
        Solution solution = new T977_SquaresOfASortedArray().new Solution(); 
    }
    
    //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] sortedSquares(int[] nums) {
        int i =0;
        int j=nums.length-1;
        int[] res = new int[nums.length];
        int n = nums.length-1;
        while(i<=j){
            if (Math.abs(nums[i])>Math.abs(nums[j])){
                res[n] = nums[i]*nums[i];
                i++;
            }else{
                res[n]=nums[j]*nums[j];
                j--;
            }
            n--;
        }
        return res;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}
