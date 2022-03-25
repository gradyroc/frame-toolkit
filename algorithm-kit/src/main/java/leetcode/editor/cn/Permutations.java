package leetcode.editor.cn;

//给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,3]
//输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
// 
//
// 示例 2： 
//
// 
//输入：nums = [0,1]
//输出：[[0,1],[1,0]]
// 
//
// 示例 3： 
//
// 
//输入：nums = [1]
//输出：[[1]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 6 
// -10 <= nums[i] <= 10 
// nums 中的所有整数 互不相同 
// 
// Related Topics 数组 回溯 
// 👍 1859 👎 0

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * 回溯法
 * 46 ：全排列
 */

public class Permutations{
    public static void main(String[] args) {
        Solution solution = new Permutations().new Solution();
        
    }

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {

    List<List<Integer>> res = new ArrayList<>();


    public List<List<Integer>> permute(int[] nums) {

        if (nums.length==0){
            return null;
        }

        boolean [] used = new boolean[nums.length];

        List<Integer> path = new LinkedList<>();
        dfs(nums,used,path);
        return res;
    }

    private void dfs(int[] nums, boolean[] used, List<Integer> path) {
        //触发结束条件
        if (path.size() == nums.length){
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            //排除不合法的选择
            if (used[i] ==true){
                continue;
            }
            // 做选择
            used[i]=true;
            path.add(nums[i]);
            // 进入下一层决策树
            dfs(nums,used,path);
            // 撤销选择，回溯到上一步
            used[i]=false;
            path.remove(path.lastIndexOf(nums[i]));
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}