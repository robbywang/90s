import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class SolutionTests {

  /**
   * 编写一个方法，计算一个字符串中，第一个不重复的字符在当前字符串中的索引。
   */
  @Test
  public void test1() {
    String input = "aaabcaaa";
    char[] chars = input.toCharArray();

    int j = 0;

    for (int i = 0; i < chars.length - 1; i++) {
      j = i + 1;
      int a = (int) chars[i];
      int b = (int) chars[j];

      int result = a ^ b;

      if (result != 0) {
        break;
      }
    }

    System.out.println(j);
  }

  /**
   * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
   *
   * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
   *
   * 示例:
   *
   * 给定 nums = [2, 7, 11, 15], target = 9 因为 nums[0] + nums[1] = 2 + 7 = 9 所以返回 [0, 1]
   */
  @Test
  public void test2() {
    int[] nums = {1, 2, 7, 11, 15};
    int target = 9;

    // 暴力破解法.
    for (int i = 0; i < nums.length - 1; i++) {
      for (int j = i + 1; j < nums.length; j++) {
        int temp = (nums[i] + nums[j]) ^ target;
        if (temp == 0) {
          System.out.println(String.format("暴力破解法 - x: %s, y: %s", i, j));
          break;
        }
      }
    }

    /**
     * Hash法
     * key - 值
     * value - index
     */
    Map<Integer, Integer> hash = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
      int delta = target - nums[i];

      if (hash.keySet().contains(delta)) {
        System.out.println(String.format("Hash法 - x: %s, y: %s", i, hash.get(delta)));
        break;
      }
      hash.put(nums[i], i);
    }

    /**
     * 数组法
     * 值 - 下标
     */
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < nums.length; i++) {
      int delta = target - nums[i];
      if (delta >= 0 && delta <= list.size()) {
        if (list.get(delta) != null) {
          System.out.println(String.format("数组法 - x: %s, y: %s", i, list.get(delta)));
          break;
        }
        list.add(nums[i], i);
      }
    }
  }

  /**
   * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。 输入: "abcabcbb" 输出: 3 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
   */
  @Test
  public void test3() {
    String input = "abcabcbb";
//    System.out.println(lengthOfLongestSubstring(input));
    input = "pwwkew";
    System.out.println(lengthOfLongestSubstring(input));
    input = "dvdf";
//    System.out.println(lengthOfLongestSubstring(input));
  }

  public class ListNode {

    int val;
    ListNode next;

    ListNode(int x) {
      val = x;
    }
  }

  /**
   * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
   *
   * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
   *
   * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
   *
   * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4) 输出：7 -> 0 -> 8 原因：342 + 465 = 807
   */
  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    int value1 = 0;
    int value2 = 0;

    if (l1 != null) {
      value1 = l1.val;
    }
    if (l2 != null) {
      value2 = l2.val;
    }

    int thisValue = (value1 + value2) % 10;
    int nextValue = (value1 + value2) / 10;

    ListNode sum = new ListNode(thisValue);
    addTwoNumbers(sum, l1.next, l2.next, nextValue);

    return sum;
  }

  private void addTwoNumbers(ListNode sum, ListNode l1, ListNode l2, int modeValue) {
    if (l1 != null || l2 != null) {
      int value1 = 0;
      int value2 = 0;

      ListNode nextNode1 = null;
      ListNode nextNode2 = null;

      if (l1 != null) {
        value1 = l1.val;
        nextNode1 = l1.next;
      }
      if (l2 != null) {
        value2 = l2.val;
        nextNode2 = l2.next;
      }

      int thisValue = (value1 + value2 + modeValue) % 10;
      int nextValue = (value1 + value2 + modeValue) / 10;

      ListNode newNode = new ListNode(thisValue);
      sum.next = newNode;

      addTwoNumbers(newNode, nextNode1, nextNode2, nextValue);
    } else {
      if (modeValue != 0) {
        ListNode newNode = new ListNode(modeValue);
        sum.next = newNode;
      }
    }
  }

  public int lengthOfLongestSubstring(String s) {
    char[] chars = s.toCharArray();
    int max = 0;
    Map<Character, Integer> map = new HashMap();
    for (int i = 0; i < chars.length; i++) {
      Set set = map.keySet();
      if (set.contains(chars[i])) {
        if (set.size() > max) {
          max = set.size();
        }

        int start = map.get(chars[i]) + 1;
        map.clear();

        for (int j = start; j <= i; j++) {
          map.put(chars[j], j);
        }

      } else {
        map.put(chars[i], i);
      }
    }

    return max > map.keySet().size() ? max : map.keySet().size();
  }


  public int lengthOfLongestSubstring2(String s) {
    int nums[] = new int[128];
    for (int i = 0; i < 128; i++) {
      nums[i] = -1;
    }
    //l是第一个非重复元素的下标。
    //cur是当前计数器的值
    int l = 0, max = 0, curLen = 0, sLen = s.length();

    for (int i = 0; i < sLen; i++) {
      int index = s.charAt(i);
      if (nums[index] < l) {
        nums[index] = i;
        curLen++;
      } else {
        max = curLen > max ? curLen : max;
        curLen -= nums[index] - l;
        l = nums[index] + 1;
        nums[index] = i;
      }
    }
    return curLen > max ? curLen : max;
  }

  public int lengthOfLongestSubstring3(String s) {
    char[] str = s.toCharArray();
    int[] exist = new int[127];
    int i1 = 0;
    int result = 0;
    int i2 = 0;
    for (; i2 < str.length; ++i2) {
      if (exist[str[i2]] >= i1 + 1) {
        result = i2 - i1 > result ? i2 - i1 : result;
        i1 = exist[str[i2]];
      }
      exist[str[i2]] = i2 + 1;
    }
    return i2 - i1 > result ? i2 - i1 : result;
  }

  public int lengthOfLongestSubstring4(String s) {
    int n = s.length(), ans = 0;
    Map<Character, Integer> map = new HashMap<>();
    for (int end = 0, start = 0; end < n; end++) {
      char alpha = s.charAt(end);
      if (map.containsKey(alpha)) {
        start = Math.max(map.get(alpha), start);
      }
      ans = Math.max(ans, end - start + 1);
      map.put(s.charAt(end), end + 1);
    }
    return ans;
  }

  /**
   * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
   *
   * 有效字符串需满足：
   *
   * 左括号必须用相同类型的右括号闭合。 左括号必须以正确的顺序闭合。 注意空字符串可被认为是有效字符串。
   *
   * 示例 1:
   *
   * 输入: "()" 输出: true 示例 2:
   *
   * 输入: "()[]{}" 输出: true 示例 3:
   *
   * 输入: "(]" 输出: false 示例 4:
   *
   * 输入: "([)]" 输出: false 示例 5:
   *
   * 输入: "{[]}" 输出: true
   */
  @Test
  public void test4() {
    System.out.println(isValid("()[]{}"));

  }

  public boolean isValid(String s) {
    char[] chars = s.toCharArray();
    Stack<Character> stack = new Stack();

    for (int i = 0; i < chars.length; i++) {
      char stringCur = chars[i];

      if (stringCur == '(' || stringCur == '[' || stringCur == '{') {
        stack.push(stringCur);

      } else {

        if (!stack.isEmpty()) {
          Character stackTop = stack.pop();
          boolean valid = false;

          switch (stringCur) {
            case ')':
              if (stackTop == '(') {
                valid = true;
              }
              break;
            case ']':
              if (stackTop == '[') {
                valid = true;
              }
              break;
            case '}':
              if (stackTop == '{') {
                valid = true;
              }
              break;
          }

          if (!valid) {
            return false;
          }

        } else {
          return false;
        }

      }
    }
    return stack.isEmpty();
  }

  @Test
  public void test5() {
    int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
//    System.out.println(removeDuplicates(nums));
    int[] nums2 = {1, 1, 2};
    System.out.println(removeDuplicates(nums2));
  }

  /**
   * 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
   *
   * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
   *
   * 示例 1:
   *
   * 给定数组 nums = [1,1,2],
   *
   * 函数应该返回新的长度 2, 并且原数组 nums 的前两个元素被修改为 1, 2。
   *
   * 你不需要考虑数组中超出新长度后面的元素。 示例 2:
   *
   * 给定 nums = [0,0,1,1,1,2,2,3,3,4],
   *
   * 函数应该返回新的长度 5, 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4。
   *
   * 你不需要考虑数组中超出新长度后面的元素。
   */
  public int removeDuplicates(int[] nums) {
    int x = 0, i = 0, j = 1;

    while (j < nums.length) {

      if (nums[j] == nums[i]) {
        if (j - i == 1) { // 第一次发现重复.
          nums[x] = nums[j];
        }

      } else {
        if (i != j) {
          nums[++x] = nums[j];
        }
        i = j;
      }

      j++;

    }

    Arrays.stream(nums).forEach(System.out::print);
    System.out.println("\n");
    return x + 1;
  }

  @Test
  public void test6() {
//    int[] nums = {0,1,2,2,3,0,4,2};
//    int[] nums = {3,2,2,3};
//    int val = 3;
    int[] nums = {2};
    int val = 3;
    removeElement(nums, val);
    Arrays.stream(nums).forEach(System.out::print);
  }

  public int removeElement(int[] nums, int val) {
    if (nums.length == 0) {
      return 0;
    } else {
      int i = 0;
      int j = nums.length - 1;

      while (i < j) {
        if (nums[i] != val) {
          i++;

        } else {
          if (nums[j] != val) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;

          } else {
            j--;
          }
        }
      }

      System.out.println("length: " + j);
      return j;
    }

  }

  @Test
  public void test() {

  }

  public ListNode removeElements(ListNode head, int val) {

    while (head != null && head.val == val) {
      head = head.next;
    }

    if (head != null) {
      ListNode nodei = head;
      ListNode nodej = head.next;

      while (nodej != null && nodei != null) {
        if (nodej.val == val) {
          nodei.next = nodej.next;

          if (nodei != null) {
            nodej = nodei.next;
          }
        } else {
          nodei = nodej;
          nodej = nodej.next;
        }
      }
    }

    return head;
  }

  @Test
  public void testRob() {
    int[] input = {2, 1, 3};
    System.out.println(rob(input));
  }

  public int rob(int[] num) {
    int prevMax = 0;
    int currMax = 0;
    for (int x : num) {
      int temp = currMax;
      currMax = Math.max(prevMax + x, currMax);
      prevMax = temp;
    }
    return currMax;
  }

  public int lengthOfLastWord(String s) {
    if (s == null) {
      return 0;
    }

    s = s.trim();

    String[] strArray = s.split(" ");
    String last = strArray[strArray.length - 1];
    return last.length();
  }


  @Test
  public void testReplace() {
    Map<String, String> tokens = new HashMap<String, String>();
    tokens.put("cat", "Garfield");
    tokens.put("beverage", "coffee");

    String template = "%cat% really needs some %beverage%.";
    String template2 = "${cat} really needs some ${beverage}.";

// Create pattern of the format "%(cat|beverage)%"
    String patternString = "%(" + StringUtils.join(tokens.keySet(), "|") + ")%";
    String patternString2 = "\\$\\{(" + StringUtils.join(tokens.keySet(), "|") + ")}";
    Pattern pattern = Pattern.compile(patternString);
    Pattern pattern2 = Pattern.compile(patternString2);
    Matcher matcher = pattern.matcher(template);
    Matcher matcher2 = pattern2.matcher(template2);

    StringBuffer sb = new StringBuffer();
    while (matcher.find()) {
      matcher.appendReplacement(sb, tokens.get(matcher.group(1)));
    }
    matcher.appendTail(sb);

    System.out.println(sb.toString());

    StringBuffer sb2 = new StringBuffer();
    while (matcher2.find()) {
      matcher2.appendReplacement(sb2, tokens.get(matcher2.group(1)));
    }
    matcher2.appendTail(sb2);

    System.out.println(sb2.toString());

  }

  public boolean hasPathSum(TreeNode root, int sum) {
    if (root == null) {
      return false;
    }
    return hasPathSum(root, 0, sum);
  }

  public boolean hasPathSum(TreeNode treeNode, int sum, int target) {
    // 叶子节点
    if (treeNode.left == null && treeNode.right == null) {

      if (treeNode.val + sum == target) {
        return true;
      } else {
        return false;
      }

    } else {
      // 非叶子节点.
      sum = sum + treeNode.val;
      boolean found = false;
      if (treeNode.left != null) {
        found = hasPathSum(treeNode.left, sum, target);
      }
      if (!found) {
        if (treeNode.right != null) {
          found = hasPathSum(treeNode.right, sum, target);
        }
      }
      return found;
    }
  }

  public int minDepth(TreeNode root) {
    if (root == null) {
      return 0 ;

    } else {

      int leftDepth = 0;
      int rightDepth = 0;

      if (root.left != null) {
        leftDepth = minDepth(root.left) + 1;
      }
      if (root.right != null) {
        rightDepth = minDepth(root.right) + 1;
      }

      return Math.min(leftDepth, rightDepth);
    }
  }

  public class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }

  public String frequencySort(String s) {
    //初始化字母数组
    int[] latters = new int[256];
    //填充数组
    for(char c:s.toCharArray()){
      latters[c]++;
    }
    //排序
    PriorityQueue<Latter> queue = new PriorityQueue<>();

    for (int i = 0;i<latters.length;i++){
      if (latters[i]!=0){
        queue.add(new Latter((char) i,latters[i]));
      }
    }
    StringBuilder stringBuilder = new StringBuilder();

    while (!queue.isEmpty()){
      Latter latter = queue.poll();
      for (int i =0;i<latter.count;i++)
        stringBuilder.append(latter.latter);
    }


    return stringBuilder.toString();
  }

  public class Latter implements Comparable<Latter>{
    public char latter = '0';
    public int count = 0;

    public Latter(char latter, int count) {
      this.latter = latter;
      this.count = count;
    }

    @Override
    public int compareTo(Latter o) {
      return o.count - this.count;
    }
  }



  public boolean isPalindrome(int x) {
    // 判断数字的长度
    int length = 0;

//    while (x >> length != 0) {
//      length++;
//    }

    while (x/(10 << length) != 0) {
      length++;
    }

    length--;


    int mid = length /2;
    boolean result= true;
    for(int i=1; i<=mid; i++) {
      int l = 10 * (length-i);
      int left = x / l;
      int r = 10 * i;
      int right = x%r;
      if (left != right) {
        result = false;
        break;
      }
    }

    return result;

  }

  @Test
  public void testIsPalindrome() {
    isPalindrome(121);
  }


}
