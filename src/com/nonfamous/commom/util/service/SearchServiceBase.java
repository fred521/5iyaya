package com.nonfamous.commom.util.service;

import java.util.ArrayList;
import java.util.List;

import com.nonfamous.commom.util.StringUtils;

public class SearchServiceBase extends POJOServiceBase {
	protected String splitRegex = " ";

	protected List<String> getHitKeyWords(String wordString) {
		List<String> keyWordList = new ArrayList<String>();
		String[] words = wordString.split(" ");
		for (String word : words) {
			if (StringUtils.isNotBlank(word)) {
				keyWordList.add(word.trim());
			}
		}
		return keyWordList;
	}
}
