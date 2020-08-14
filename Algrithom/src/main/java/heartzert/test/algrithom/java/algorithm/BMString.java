package heartzert.test.algrithom.java.algorithm;

import java.util.Arrays;

/**
 * Created by heartzert on 2020/5/22.
 * Email: heartzert@163.com
 */
public class BMString {

    public static void main(String[] args) {
        BMString bmString = new BMString();
        String host = "asjdfajshdflkajkajshdfasf";
        String mod = "jkaj";
        bmString.printResult(host, mod, bmString.execute(host, mod));
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
        //aass dd f(fg)ghjj   //主串空格仅用于对齐
        //   s[fg]d{fg}       //模式串
        //规定：小括号内称为好后缀；中括号内称为“内部后缀”；花括号内称为“后缀子串”
        //
        //用来存储模式串中，每个“后缀子串”对应的“内部后缀”的起始位置。
        //目的是缓存所有的“后缀子串”的“内部后缀”位置，方便比较时直接调用。
        //由于“后缀子串”一定是从最后一位往前，所以只需记录长度，即可表示唯一的“后缀子串”。
        //所以k长的“后缀子串”对应下标为k-1的位置。
        int[] suffix = new int[modLen];
        //aas s ddf(fg)ghjj   //空格仅用于对齐
        //   [g]dgd{fg}
        //中括号内称为：后缀子串的前缀
        //我们不仅要匹配“内部后缀”，也要在模式串中匹配“后缀子串”的前缀
        //注意：是后缀与前缀匹配，一个必须是后缀，一个必须前缀，中缀一律都不行。
        boolean[] prefix = new boolean[modLen];
        //错误点1 这里是计算模式串中的hash，不是主串！
        //buildHash(hash, hostString);
        buildHash(hash, modString);
        buildGoodSuffix(modString, suffix, prefix);
        int i = 0;
        int j;
        int add;
        //将模式串从主串的头部开始比较
        while (i <= hostLen - modLen) {
            //从后向前对比模式串与子串
            //错误点2 这里j要大于等于0啊，0位置也要判断
            //for (j = modLen - 1; j > 0; ) {
            for (j = modLen - 1; j >= 0; ) {
                if (modString.charAt(j) == hostString.charAt(i + j)) {
                    j--;
                } else {
                    break;
                }
            }
            //此时的j为坏字符在模式串中的位置，若j<0意味着完全匹配。
            if (j < 0) {
                return i;
            }
            //得到模式串中坏字符对应字符的位置
            int xi = getHashValue(hash, hostString.charAt(i + j));
            //错误3 这里少了si - xi的这一步
//            int add1 = j - xi;
            int add2 = getGoodsSuffix(modString, j, suffix, prefix);
//            add = Math.max(add1, add2);
            add = add2;
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

    private void buildGoodSuffix(String string, int[] suffix, boolean[] prefix) {
        int length = string.length();
        for (int i = 0; i < length; i++) {
            suffix[i] = -1;
            prefix[i] = false;
        }
        //只处理模式串的真子串
        for (int i = 0, j; i < length - 1; i++) {
            j = 0;
            while (j <= i) {
                //对比模式串子串的后缀与模式串的后缀
                if (string.charAt(i - j) == string.charAt(length - 1 - j)) {
                    j++;
                } else {
                    break;
                }
            }
            if (j != 0) {
                suffix[j] = i - j + 1;
            }
            if (j > i) {
                prefix[j] = true;
            }
        }
    }

    //modString=模式串
    //pos坏字符在模式串中的位置
    private int getGoodsSuffix(String modString, int pos, int[] suffix, boolean[] prefix) {
        int modLength = modString.length();
        if (pos == -1) {
            return modLength;
        }
        //好后缀长度
        int goodsufLength = modLength - pos;

        //如果没有内部后缀
        if (suffix[goodsufLength] == -1) {
            //如果前缀匹配
            if (prefix[goodsufLength]) {
                return modLength - goodsufLength;
            } else  {
                return modLength;
            }
        } else {
            return modLength - suffix[goodsufLength] - goodsufLength;
        }
    }
}
