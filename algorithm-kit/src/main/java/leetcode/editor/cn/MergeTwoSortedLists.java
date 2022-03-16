package leetcode.editor.cn;

////You are given the heads of two sorted linked lists list1 and list2. 
////
//// Merge the two lists in a one sorted list. The list should be made by 
////splicing together the nodes of the first two lists. 
////
//// Return the head of the merged linked list. 
////
//// 
//// Example 1: 
////
//// 
////Input: list1 = [1,2,4], list2 = [1,3,4]
////Output: [1,1,2,3,4,4]
//// 
////
//// Example 2: 
////
//// 
////Input: list1 = [], list2 = []
////Output: []
//// 
////
//// Example 3: 
////
//// 
////Input: list1 = [], list2 = [0]
////Output: [0]
//// 
////
//// 
//// Constraints: 
////
//// 
//// The number of nodes in both lists is in the range [0, 50]. 
//// -100 <= Node.val <= 100 
//// Both list1 and list2 are sorted in non-decreasing order. 
//// 
//// Related Topics 递归 链表 👍 2269 👎 0
//

public class MergeTwoSortedLists{
    public static void main(String[] args) {
        Solution solution = new MergeTwoSortedLists().new Solution();
        
    }

//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        // 虚拟头结点
        ListNode dummy = new ListNode(-1), p = dummy;
        ListNode p1 = list1, p2 = list2;
        while (p1 != null && p2 != null) {
            // 比较 p1 和 p2 两个指针
            // 将值较小的的节点接到 p 指针
            if (p1.val > p2.val) {
                p.next = p2;
                p2 = p2.next;
            } else {
                p.next = p1;
                p1 = p1.next;
            }
            // p 指针不断前进
            p = p.next;
        }

        if (p1 != null) {
            p.next = p1;
        }

        if (p2 != null) {
            p.next = p2;
        }

        return dummy.next;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}