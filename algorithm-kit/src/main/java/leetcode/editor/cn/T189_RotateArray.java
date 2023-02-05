
package leetcode.editor.cn;

/**
 * 给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
 * <p>
 * <p>
 * <p>
 * 示例 1:
 * <p>
 * <p>
 * 输入: nums = [1,2,3,4,5,6,7], k = 3
 * 输出: [5,6,7,1,2,3,4]
 * 解释:
 * 向右轮转 1 步: [7,1,2,3,4,5,6]
 * 向右轮转 2 步: [6,7,1,2,3,4,5]
 * 向右轮转 3 步: [5,6,7,1,2,3,4]
 * <p>
 * <p>
 * 示例 2:
 * <p>
 * <p>
 * 输入：nums = [-1,-100,3,99], k = 2
 * 输出：[3,99,-1,-100]
 * 解释:
 * 向右轮转 1 步: [99,-1,-100,3]
 * 向右轮转 2 步: [3,99,-1,-100]
 * <p>
 * <p>
 * <p>
 * 提示：
 * <p>
 * <p>
 * 1 <= nums.length <= 10⁵
 * -2³¹ <= nums[i] <= 2³¹ - 1
 * 0 <= k <= 10⁵
 * <p>
 * <p>
 * <p>
 * <p>
 * 进阶：
 * <p>
 * <p>
 * 尽可能想出更多的解决方案，至少有 三种 不同的方法可以解决这个问题。
 * 你可以使用空间复杂度为 O(1) 的 原地 算法解决这个问题吗？
 * <p>
 * <p>
 * Related Topics数组 | 数学 | 双指针
 * <p>
 * 👍 1704, 👎 0bug 反馈 | 使用指南 | 更多配套插件
 */


public class T189_RotateArray {
    public static void main(String[] args) {

        Solution solution = new T189_RotateArray().new Solution();
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {

        public void reverse(int[] nums,int start,int end){
            int temp;
            for (int i=start,j=end;i<=j;i++,j--){
                temp = nums[i];
                nums[i] = nums[j];
                nums[j]=temp;
            }
        }

        public void rotate(int[] nums, int k) {
            k%=nums.length;
            reverse(nums,0,nums.length-1);
            reverse(nums,0,k-1);
            reverse(nums,k,nums.length-1);
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}
