package leetcode.editor.cn;

//Given the root of a binary tree, return its maximum depth. 
//
// A binary tree's maximum depth is the number of nodes along the longest path f
//rom the root node down to the farthest leaf node. 
//
// 
// Example 1: 
//
// 
//Input: root = [3,9,20,null,null,15,7]
//Output: 3
// 
//
// Example 2: 
//
// 
//Input: root = [1,null,2]
//Output: 2
// 
//
// 
// Constraints: 
//
// 
// The number of nodes in the tree is in the range [0, 104]. 
// -100 <= Node.val <= 100 
// 
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 
// 👍 1153 👎 0

public class MaximumDepthOfBinaryTree{
    public static void main(String[] args) {
        Solution solution = new MaximumDepthOfBinaryTree().new Solution();
        
    }

//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    int  res =0;
    int depth =0;

    public int maxDepth(TreeNode root) {
//        traverse(root);
        if (root == null){
            return 0;
        }
        int leftMax = maxDepth(root.left);
        int rightMax = maxDepth(root.right);
        res = Math.max(leftMax,rightMax)+1;

        return res;
    }

    // 二叉树遍历框架
    private void traverse(TreeNode root) {

        if (root == null){
            //到达叶子节点，更新最大深度
            res = Math.max(res,depth);
            return ;
        }
        //前序位置
        depth++;
        traverse(root.left);
        traverse(root.right);

        //后序位置
        depth--;
    }

//    一棵二叉树的最大深度可以通过子树的最大高度推导出来，这就是分解问题计算答案的思路。


}

//leetcode submit region end(Prohibit modification and deletion)

}