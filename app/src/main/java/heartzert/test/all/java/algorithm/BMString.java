package heartzert.test.all.java.algorithm;

import java.util.Arrays;

/**
 * Created by heartzert on 2020/5/22.
 * Email: heartzert@163.com
 */
public class BMString {

    public static void main(String[] args) {
        BMString bmString = new BMString();
        String host = "asjdfajshdflkajshdfasf";
        String mod = "kaj";
        bmString.printResult(host, mod ,bmString.execute(host, mod));
    }

    void printResult(String host, String mod, int execute) {
        System.out.println(execute);
        System.out.println(host);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= execute; i++) {
            sb.append(" ");
        }
        System.out.println(sb.toString() + mod);
    }

    private int execute(String hostString, String modString) {
        int hostLen = hostString.length();
        int modLen = modString.length();
        int[] hash = new int[26];
        int[] suffix = new int[modLen];
        int[] prefix = new int[modLen];
        //错误点1 这里是计算模式串中的hash，不是主串！
        //buildHash(hash, hostString);
        buildHash(hash, modString);
        buildGoodSuffix(modString, suffix, prefix);
        int i = 0;
        int j;
        int add;
        while (i <= hostLen - modLen) {
            //错误点2 这里j要大于等于0啊，0位置也要判断
            //for (j = modLen - 1; j > 0; ) {
            for (j = modLen - 1; j >= 0; ) {
                if (modString.charAt(j) == hostString.charAt(i + j)) {
                    j--;
                } else  {
                    break;
                }
            }
            if (j < 0) {
                return i;
            }
            //得到xi
            add = getHashValue(hash, hostString.charAt(i + j));
            //错误3 这里少了si - xi的这一步
            add = j - add;
            //防止回撤（这里不可能为0，因为si - xi为0意味着，此处的主串与模式串不等，且此处的主串与模式串相等。）
            if (add <= 0) {
                add = 1;
            }
            i += add;
        }
        return -1;
    }

    private void buildHash(int[] a, String string) {
        Arrays.fill(a, -1);
        char[] charArray = string.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            int value = Character.toLowerCase(charArray[i]) - 97;
            a[value] = i;
        }
    }

    private int getHashValue(int[] a, char c) {
        return a[Character.toLowerCase(c) - 97];
    }

    private void buildGoodSuffix(String string, int[] suffix, int[] prefix) {

    }

}
