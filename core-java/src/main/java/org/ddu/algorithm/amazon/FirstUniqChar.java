package org.ddu.algorithm.amazon;

import java.util.HashMap;
import java.util.Map;

public class FirstUniqChar {

  public int firstUniqChar(String s) {
    if (s == null) {
      return -1;
    }

    char[] chars = s.toCharArray();
    Map<Character, Integer> map = new HashMap<>();

    for (char c : chars) {
      int count = map.getOrDefault(c, 0);
      map.put(c, ++count);
    }

    for (int i = 0; i < chars.length; i++) {
      if (map.get(chars[i]) == 1) {
        return i;
      }
    }

    return -1;
  }
}
