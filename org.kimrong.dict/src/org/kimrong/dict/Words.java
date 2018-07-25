package org.kimrong.dict;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Words {

    public static void main(String[] args) {
        String url = Resources.ROOT + "resource\\";
        Set<String> set = words(url);
        set.forEach(word -> System.out.printf("%s.\n", word));
        System.out.println(set.size());
    }

    public static Set<String> words(String url) {

        TreeSet<String> set = new TreeSet<>();

        Arrays.stream(new File(url).list()).forEach(file -> {
            try {
                List<String> allLines = Files.readAllLines(Paths.get(url + file));
                allLines.stream().forEach(line -> {
                    String[] split = line.split("[^a-zA-Z]+");
                    Arrays.stream(split).forEach(word -> {
                        splitwords(word, set);
                    });
                });
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        return set;
    }

    /**
     * 锟斤拷锟绞诧拷止锟斤拷锟�1锟斤拷锟斤拷写之母锟接讹拷锟叫⌒粗革拷锟�2.锟斤拷锟叫⌒达拷锟侥革拷锟�3. 锟斤拷锟斤拷锟叫达拷锟侥�
     *
     * @param input
     * @param collection
     */
    private static void splitwords(String input, Collection<String> collection) {
        final int length = input.length();
        int start = 0;
        if (length > 1) {
            boolean low = isLowerCase(input.charAt(0));
            for (int index = 1; index < length; index++) {
                if (isLowerCase(input.charAt(index)) != low) {
                    if (low) {
                        add(collection, input.substring(start, index));
                        start = index;
                    } else {
                        if ((index - start) > 1) {
                            add(collection, input.substring(start, index - 1));
                            start = index - 1;
                        }
                    }
                    low = !low;
                }
            }
        }
        add(collection, input.substring(start, length));
    }

    private static void add(Collection<String> collection, String key) {
        if (!key.isEmpty()) {
            if (isLowerCase(key.charAt(0)) != isLowerCase(key.charAt(key.length() - 1))) {
                collection.add(key.toLowerCase());
            } else {
                collection.add(key);
            }
        }
    }

    private static boolean isLowerCase(char c) {
        return (c >= 'a') && (c <= 'z');
    }

}
