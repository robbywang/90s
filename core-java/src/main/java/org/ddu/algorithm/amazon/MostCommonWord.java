package org.ddu.algorithm.amazon;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 简单但麻烦.
 *
 * 给定一个段落 (paragraph) 和一个禁用单词列表 (banned)。返回出现次数最多，同时不在禁用列表中的单词。
 *
 * 题目保证至少有一个词不在禁用列表中，而且答案唯一。
 *
 * 禁用列表中的单词用小写字母表示，不含标点符号。段落中的单词不区分大小写。答案都是小写字母。
 */
public class MostCommonWord {

  public String mostCommonWord(String paragraph, String[] banned) {
    paragraph += ".";

    HashSet<String> hs = new HashSet<>();
    for (String ban : banned) {
      hs.add(ban.toLowerCase());
    }

    HashMap<String, Integer> hm = new HashMap<>();
    int max = 0;
    String result = "";
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < paragraph.length(); i++) {
      char c = paragraph.charAt(i);
      if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
        sb.append(c);
        continue;
      }
      if (sb.length() > 0) {
        String res = sb.toString().toLowerCase();
        if (!hs.contains(res)) {
          hm.put(res, hm.getOrDefault(res, 0) + 1);
          int count = hm.get(res);
          if (count > max) {
            max = count;
            result = res;
          }
        }
        sb = new StringBuilder();
      }
    }
    return result;
  }

  public static void main(String[] args) {
    MostCommonWord ins = new MostCommonWord();
    String s = "Bob hit a ball, the hit BALL flew far after it was hit.";
    String[] b = {"hit"};
    System.out.println(ins.mostCommonWord(s, b));
  }
}
