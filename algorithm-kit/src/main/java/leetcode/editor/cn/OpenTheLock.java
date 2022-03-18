package leetcode.editor.cn;

//You have a lock in front of you with 4 circular wheels. Each wheel has 10 slot
//s: '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'. The wheels can rotate freel
//y and wrap around: for example we can turn '9' to be '0', or '0' to be '9'. Each
// move consists of turning one wheel one slot. 
//
// The lock initially starts at '0000', a string representing the state of the 4
// wheels. 
//
// You are given a list of deadends dead ends, meaning if the lock displays any 
//of these codes, the wheels of the lock will stop turning and you will be unable 
//to open it. 
//
// Given a target representing the value of the wheels that will unlock the lock
//, return the minimum total number of turns required to open the lock, or -1 if i
//t is impossible. 
//
// 
// Example 1: 
//
// 
//Input: deadends = ["0201","0101","0102","1212","2002"], target = "0202"
//Output: 6
//Explanation: 
//A sequence of valid moves would be "0000" -> "1000" -> "1100" -> "1200" -> "12
//01" -> "1202" -> "0202".
//Note that a sequence like "0000" -> "0001" -> "0002" -> "0102" -> "0202" would
// be invalid,
//because the wheels of the lock become stuck after the display becomes the dead
// end "0102".
// 
//
// Example 2: 
//
// 
//Input: deadends = ["8888"], target = "0009"
//Output: 1
//Explanation: We can turn the last wheel in reverse to move from "0000" -> "000
//9".
// 
//
// Example 3: 
//
// 
//Input: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"], t
//arget = "8888"
//Output: -1
//Explanation: We cannot reach the target without getting stuck.
// 
//
// 
// Constraints: 
//
// 
// 1 <= deadends.length <= 500 
// deadends[i].length == 4 
// target.length == 4 
// target will not be in the list deadends. 
// target and deadends[i] consist of digits only. 
// 
// Related Topics 广度优先搜索 数组 哈希表 字符串 
// 👍 461 👎 0

import java.util.*;
import java.util.stream.Collectors;

/**
 * 752 打开转盘锁
 */

public class OpenTheLock {
    public static void main(String[] args) {
        Solution solution = new OpenTheLock().new Solution();

    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {

        /**
         * 将字符串s 第j 位向上拨动一次
         * @param s
         * @param j
         * @return
         */
        public String plusOne(String s, int j) {
            char[] ch = s.toCharArray();
            if (ch[j]=='9'){
                ch[j]='0';
            }else {
                ch[j]+=1;
            }
            return new String(ch);
        }

        public String miusOne(String s, int j) {
            char[] ch = s.toCharArray();
            if (ch[j]=='0'){
                ch[j]='9';
            }else {
                ch[j]-=1;
            }
            return new String(ch);
        }

        public int openLock_BFS(String[] deadends, String target) {
            Queue<String> queue = new LinkedList<>();
            int step = 0;
            queue.offer("0000");

            // 记录需要跳过的死亡密码
            Set<String> deads = new HashSet<>(Arrays.asList(deadends));
            // 记录已经穷举过的密码，防止走回头路
            Set<String> visited = new HashSet<>();
            visited.add("0000");

            while (!queue.isEmpty()) {
                int size = queue.size();

                //队列里的所有节点向周围扩散
                for (int i = 0; i < size; i++) {
                    String cur = queue.poll();

                    /* 判断是否到达终点 以及是否是死锁点*/
                    if (deads.contains(cur)){
                        continue;
                    }
                    if (cur.equals(target)) {
                        return step;
                    }
                    //加入相邻接点
                    for (int j = 0; j < 4; j++) {
                        String preview = plusOne(cur,j);
                        String next = miusOne(cur,j);
                        if (!visited.contains(preview)){
                            queue.offer(preview);
                            visited.add(preview);
                        }
                        if (!visited.contains(next)){
                            queue.offer(next);
                            visited.add(next);
                        }
                    }

                }
                /* 在这里增加步数 */
                step++;
            }
// 如果穷举完都没找到目标密码，那就是找不到了
            return -1;
        }

        /**
         * 传统的 BFS 框架就是从起点开始向四周扩散，遇到终点时停止；
         * 而双向 BFS 则是从起点和终点同时开始扩散，当两边有交集的时候停止。
         * 双向 BFS 也有局限，因为你必须知道终点在哪里
         * 双向 BFS 还是遵循 BFS 算法框架的，只是不再使用队列，而是使用 HashSet 方便快速判断两个集合是否有交集。
         *
         * 另外的一个技巧点就是 while 循环的最后交换q1和q2的内容，所以只要默认扩散q1就相当于轮流扩散q1和q2。
         *
         * 其实双向 BFS 还有一个优化，就是在 while 循环开始时做一个判断：
         *
         * // ...
         * while (!q1.isEmpty() && !q2.isEmpty()) {
         *     if (q1.size() > q2.size()) {
         *         // 交换 q1 和 q2
         *         temp = q1;
         *         q1 = q2;
         *         q2 = temp;
         *     }
         *     // ...
         *
         * @param deadends
         * @param target
         * @return
         */
        public int openLock(String[] deadends, String target) {
            // 记录需要跳过的死亡密码
            Set<String> deads = new HashSet<>(Arrays.asList(deadends));
            // 用集合不用队列，可以快速判断元素是否存在
            Set<String> q1 = new HashSet<>();
            Set<String> q2 = new HashSet<>();
            Set<String> visited = new HashSet<>();
            int step = 0;
            q1.add("0000");
            q2.add(target);

            while (!q1.isEmpty() && !q2.isEmpty()) {
                // 哈希集合在遍历的过程中不能修改，用 temp 存储扩散结果
                Set<String> temp = new HashSet<>();

                //q1中所有的节点扩散
                for (String cur : q1) {
                    /* 判断是否到达终点 以及是否是死锁点*/
                    if (deads.contains(cur)){
                        continue;
                    }
                    if (q2.contains(cur)) {
                        return step;
                    }
                    visited.add(cur);
                    //加入相邻接点
                    for (int j = 0; j < 4; j++) {
                        String preview = plusOne(cur,j);
                        String next = miusOne(cur,j);
                        if (!visited.contains(preview)){
                            temp.add(preview);
                        }
                        if (!visited.contains(next)){
                            temp.add(next);
                        }
                    }
                }

                /* 在这里增加步数 */
                step++;

                //temp 相当于去
                // 扩散完q1 然后扩散q2【target 开始的扩散】
                q1 = q2;
                q2=temp;

            }
// 如果穷举完都没找到目标密码，那就是找不到了
            return -1;
        }

    }
//leetcode submit region end(Prohibit modification and deletion)

}