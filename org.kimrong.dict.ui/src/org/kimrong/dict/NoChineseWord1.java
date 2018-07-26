package org.kimrong.dict;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class NoChineseWord1 {

    public static void main(String[] args) throws IOException {
        String resourceUrl = Resources.ROOT + "resource\\";
        String chineseOfWordUrl = Resources.ROOT + "chineseOfWord\\";

        Set<String> noChineseWord = noChineseWord(resourceUrl, chineseOfWordUrl);
        System.out.printf("noChineseWord:%d\n", noChineseWord.size());
        noChineseWord.forEach(word -> System.out.printf("%s.\n", word));
        System.out.printf("noChineseWord:%d", noChineseWord.size());

    }

    private static Set<String> noChineseWord(String resourceUrl, String chineseOfWordUrl) throws IOException {
        Set<String> noChineseWord = new TreeSet<>();
        Set<String> words = Words.words(resourceUrl);

        Map<String, String> chineseOfWord = ChineseOfWord.getChineseOfWord(chineseOfWordUrl);
        for (String word : words) {
            if (!chineseOfWord.containsKey(word + ".")) {
                noChineseWord.add(word);
            }
        }
        return noChineseWord;
    }

}
