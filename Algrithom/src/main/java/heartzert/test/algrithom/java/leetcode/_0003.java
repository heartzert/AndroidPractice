package heartzert.test.algrithom.java.leetcode;

import java.util.HashSet;

/**
 * Created by heartzert on 2023/1/11.
 * Email: heartzert@163.com
 */
/*
给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。

 

示例 1:

输入: s = "abcabcbb"
输出: 3
解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
示例 2:

输入: s = "bbbbb"
输出: 1
解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
示例 3:

输入: s = "pwwkew"
输出: 3
解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 

提示：

0 <= s.length <= 5 * 104
s 由英文字母、数字、符号和空格组成

来源：力扣（LeetCode）
链接：https://leetcode.cn/problems/longest-substring-without-repeating-characters
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class _0003 {

    public static void main(String[] args) {
        System.out.println(new _0003().lengthOfLongestSubstring("abcabcbb"));
        System.out.println(new _0003().lengthOfLongestSubstring("bbbbbbbb"));
        System.out.println(new _0003().lengthOfLongestSubstring("pwwkew"));
        System.out.println(new _0003().lengthOfLongestSubstring("dvdf"));
    }

    public int lengthOfLongestSubstring(String s) {
        HashSet<Character> hashSet = new HashSet<>();
        if (s.length() == 0) {
            return 0;
        }
        int i = 0;
        int j = 1;
        int maxLength = 1;
        hashSet.add(s.charAt(0));
        while (i + maxLength < s.length() && j < s.length()) {
            if (hashSet.add(s.charAt(j))) {
                j++;
                maxLength = Math.max(maxLength, j - i);
            } else {
                hashSet.remove(s.charAt(i));
                i++;
            }
        }
        return maxLength;
    }

    public int lengthOfLongestSubstring2(String s) {
        if (s.length() == 0) {
            return 0;
        }
        int i = 0;
        int j = 1;
        int maxLength = 1;
        while (i + maxLength < s.length() && j < s.length()) {
            String subString = s.substring(i, j);
            int index = subString.indexOf(s.charAt(j));
            if (index == -1) {
                j++;
                maxLength = Math.max(maxLength, j - i);
            } else {
                i += index + 1;
                j++;
            }
        }
        return maxLength;
    }

}
