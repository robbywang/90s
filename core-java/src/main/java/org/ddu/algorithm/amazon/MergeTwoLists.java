package org.ddu.algorithm.amazon;

public class MergeTwoLists {

  public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    if (l1 == null) {
      return l2;
    } else if (l2 == null) {
      return l1;
    } else {
      ListNode root = new ListNode(0);
      ListNode pre = root;

      while (l1 != null && l2 != null) {
        if (l1.val <= l2.val) {
          pre.next = l1;
          l1 = l1.next;

        } else {
          pre.next = l2;
          l2 = l2.next;
        }

        pre = pre.next;
      }

      if (l1 == null) {
        pre.next = l2;
      } else {
        pre.next = l1;
      }

      return root.next;
    }
  }

  class ListNode {

    int val;
    ListNode next;

    ListNode(int x) {
      val = x;
    }
  }

  public static void main(String[] args) {

  }

}
