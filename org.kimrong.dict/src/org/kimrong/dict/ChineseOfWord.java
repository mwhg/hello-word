package org.kimrong.dict;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChineseOfWord {

	public static void main(String[] args) throws IOException {
		String url = "D:\\_workshop\\workspace_4.8.0_10\\org.kimrong.dict\\chineseOfWord\\";
		Map<String, String> map = getChineseOfWord(url);
		map.forEach((key, value) -> System.out.printf("%s%s\n", key, value));
	}

	public static Map<String, String> getChineseOfWord(String url) throws IOException {
		Map<String, String> map = new HashMap<>();
		
		int index =0;
		String key = "";
		String value = "";
		for (String file : new File(url).list()) {
			List<String> allLines = Files.readAllLines(Paths.get(url + file));
			for(String line: allLines) {
				String trim = line.trim();
				if (!trim.isEmpty()) {
					if(index++%2==0) {
						key=trim;
					}else {
						value=trim;
						map.put(key, value);
					}
					
				}
			}
		}
		return map;
	}
}
