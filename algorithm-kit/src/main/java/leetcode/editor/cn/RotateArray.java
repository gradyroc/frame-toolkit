package leetcode.editor.cn;

//给你一个数组，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。 
//
// 
//
// 示例 1: 
//
// 
//输入: nums = [1,2,3,4,5,6,7], k = 3
//输出: [5,6,7,1,2,3,4]
//解释:
//向右轮转 1 步: [7,1,2,3,4,5,6]
//向右轮转 2 步: [6,7,1,2,3,4,5]
//向右轮转 3 步: [5,6,7,1,2,3,4]
// 
//
// 示例 2: 
//
// 
//输入：nums = [-1,-100,3,99], k = 2
//输出：[3,99,-1,-100]
//解释: 
//向右轮转 1 步: [99,-1,-100,3]
//向右轮转 2 步: [3,99,-1,-100] 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 105 
// -231 <= nums[i] <= 231 - 1 
// 0 <= k <= 105 
// 
//
// 
//
// 进阶： 
//
// 
// 尽可能想出更多的解决方案，至少有 三种 不同的方法可以解决这个问题。 
// 你可以使用空间复杂度为 O(1) 的 原地 算法解决这个问题吗？ 
// 
//
// 
// 
//
// 
// 
// Related Topics 数组 数学 双指针 
// 👍 1417 👎 0

public class RotateArray{
    public static void main(String[] args) {
        Solution solution = new RotateArray().new Solution();
        
    }

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {


    public int gcd(int x, int y) {
        return y > 0 ? gcd(y, x % y) : x;
    }
    public void rotate(int[] nums, int k) {
        /**
         * [0,1,2,3,4,5,.....n-1]右转k个
         *  i元素的目标位置为 （i+k）% n
         *
         */
        int count = gcd(k,nums.length);
//        System.out.println(n);
        for (int i = 0; i < count; ++i) {
            int cur = i;
            int pre = nums[i];
            do {
                int next = (cur+k)%nums.length;
                int temp=nums[next];
                nums[next] = pre;
                pre = temp;
                cur=next;

            }while (i !=cur);

        }

    }
}
//leetcode submit region end(Prohibit modification and deletion)

}