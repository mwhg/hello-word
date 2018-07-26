package org.kimrong.dict;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ChineseWord2 {

    public static void main(String[] args) throws IOException {
        String resourceUrl = Resources.ROOT + "resource\\";
        String chineseOfWordUrl = Resources.ROOT + "chineseOfWord\\";

        Map<String, String> noChineseWord = noChineseWord(resourceUrl, chineseOfWordUrl);
        System.out.printf("noChineseWord:%d\n", noChineseWord.size());
        noChineseWord.forEach((key, value) -> System.out.printf("%s.\n%s\n", key, value));
        System.out.printf("noChineseWord:%d", noChineseWord.size());

    }

    private static Map<String, String> noChineseWord(String resourceUrl, String chineseOfWordUrl) throws IOException {
        Set<String> words = Words.words(resourceUrl);
        Map<String, String> chineseOfWord = ChineseOfWord.getChineseOfWord(chineseOfWordUrl);

        Map<String, String> chineseWord = new TreeMap<>();
        Set<String> noChineseWord = new TreeSet<>();
        for (String word : words) {
            String value = chineseOfWord.get(word + ".");
            if (value == null) {
                throw new RuntimeException(String.format("please use no chinese words check, [%s] has no value", word));
            }
            chineseWord.put(word, value);
        }
        return chineseWord;
    }

}
