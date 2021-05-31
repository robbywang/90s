package org.ddu.algorithm.amazon;

/**
 * 输入: 1->2->3->4->5->NULL 输出: 5->4->3->2->1->NULL
 */
public class ReverseList {

  public ListNode reverseList(ListNode head) {

    if (head == null || head.next == null) {
      return head;

    } else {
      ListNode pre = head;
      ListNode cur = head.next;
      ListNode next = cur.next;

      while (cur != null) {
        next = cur.next;
        cur.next = pre;
        pre = cur;

        if (next != null) {
          cur = next;
        } else {
          break;
        }
      }

      head.next = null;

      return cur;
    }
  }


  public class ListNode {

    int val;
    ListNode next;

    ListNode(int x) {
      val = x;
    }
  }

  public static void main(String[] args) {
    ReverseList ins = new ReverseList();


  }

}
