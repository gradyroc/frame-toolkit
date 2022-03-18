package leetcode.editor.cn;

//You are given an array of k linked-lists lists, each linked-list is sorted in 
//ascending order. 
//
// Merge all the linked-lists into one sorted linked-list and return it. 
//
// 
// Example 1: 
//
// 
//Input: lists = [[1,4,5],[1,3,4],[2,6]]
//Output: [1,1,2,3,4,4,5,6]
//Explanation: The linked-lists are:
//[
//  1->4->5,
//  1->3->4,
//  2->6
//]
//merging them into one sorted list:
//1->1->2->3->4->4->5->6
// 
//
// Example 2: 
//
// 
//Input: lists = []
//Output: []
// 
//
// Example 3: 
//
// 
//Input: lists = [[]]
//Output: []
// 
//
// 
// Constraints: 
//
// 
// k == lists.length 
// 0 <= k <= 10⁴ 
// 0 <= lists[i].length <= 500 
// -10⁴ <= lists[i][j] <= 10⁴ 
// lists[i] is sorted in ascending order. 
// The sum of lists[i].length will not exceed 10⁴. 
// 
// Related Topics 链表 分治 堆（优先队列） 归并排序 👍 1816 👎 0

import java.util.PriorityQueue;

/**
 * 23 合并k个链表
 */

public class MergeKSortedLists{
    public static void main(String[] args) {
        Solution solution = new MergeKSortedLists().new Solution();
        
    }

//leetcode submit region begin(Prohibit modification and deletion)

//public class ListNode {
//    int val;
//    ListNode next;
//    ListNode() {}
//    ListNode(int val) { this.val = val; }
//    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
//}

class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length ==0){
            return null;
        }
        ListNode vtrHead = new ListNode(-1);
        ListNode p =vtrHead;
        PriorityQueue<ListNode> queue = new PriorityQueue<>((o1, o2) -> o1.val - o2.val);

        for (ListNode head : lists) {
            if (head != null){
                queue.add(head);
            }
        }
        while (!queue.isEmpty()){
            ListNode node = queue.poll();
            p.next = node;
            //将当前链表节点的下一个节点入队，优先级队列出队时会自动选取顶部元素
            if (node.next != null){
                queue.add(node.next);
            }
            p = p.next;
        }
        return vtrHead.next;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}