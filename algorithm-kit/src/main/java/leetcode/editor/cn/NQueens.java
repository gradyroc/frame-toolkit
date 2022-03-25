package leetcode.editor.cn;

//n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。 
//
// 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。 
//
// 
// 
// 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 4
//输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
//解释：如上图所示，4 皇后问题存在两个不同的解法。
// 
//
// 示例 2： 
//
// 
//输入：n = 1
//输出：[["Q"]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 9 
// 
// 
// 
// Related Topics 数组 回溯 
// 👍 1224 👎 0
/**
 * 51 N 皇后问题
 */

import java.util.*;

public class NQueens {
    public static void main(String[] args) {
        Solution solution = new NQueens().new Solution();

    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {

        public List<List<String>> solveNQueens(int n) {
            List<List<String>> res = new ArrayList<>();
            //列方向
            Set<Integer> columns = new HashSet<>();
            //正斜线方向
            Set<Integer> diagonal1 = new HashSet<>();
            //反斜线方向
            Set<Integer> diagonal2 = new HashSet<>();
            int[] queens = new int[n];
            Arrays.fill(queens, -1);

            //从第0 行开始进行回溯判断
            backTrack(res,queens, n, 0, columns, diagonal1, diagonal2);

            return res;
        }

        private void backTrack(List<List<String>> res, int[] queens,int n, int row
                , Set<Integer> columns, Set<Integer> diagonal1, Set<Integer> diagonal2) {

            //到最后一行，则每一行都放置了一个皇后，产生一个解，并返回
            if (row == n){
                List<String> answer = generateQueen(queens,n);
                res.add(answer);
                return;
            }else {

                for (int i = 0; i < n; i++) {
                    //列是否相同，判断该列上是否有皇后
                    if (columns.contains(i)){
                        continue;
                    }

                    //正斜线上，斜率相同，竖坐标-横坐标的值相等
                    int dia1 = row -i;
                    if (diagonal1.contains(dia1)){
                        continue;
                    }

                    //反斜线上，横坐标+竖坐标的值相等
                    int dia2 = row+i;
                    if (diagonal2.contains(dia2)){
                        continue;
                    }

                    //加入选择
                    queens[row]=i;
                    columns.add(i);
                    diagonal1.add(dia1);
                    diagonal2.add(dia2);
                    //进行下一次递归
                    backTrack(res,queens,n,row+1,columns,diagonal1,diagonal2);
                    //取消选择
                    queens[row] =-1;
                    columns.remove(i);
                    diagonal1.remove(dia1);
                    diagonal2.remove(dia2);

                }
            }










        }

        /**
         * 到最后一行，
         * @param queens：下标代表第多少行，值代表每个皇后所在行的下标
         * @param n：皇后个数
         * @return
         */
        private List<String> generateQueen(int[] queens, int n) {

            List<String > board = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                char[] row = new char[n];
                Arrays.fill(row,'.');
                //queens[i]的值 代表当前行的皇后所在下标值
                row[queens[i]]='Q';
                String queen = new String(row);
                board.add(queen);
            }
            return board;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}