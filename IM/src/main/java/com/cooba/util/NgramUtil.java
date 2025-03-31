package com.cooba.util;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.IndexAnalysis;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NgramUtil {
    public static void main(String[] args) {
        List<String> chatLogs = Arrays.asList(
                "今天下雨了，我們去吃火鍋吧！我們去吃火鍋吧 story",
                "這家火锅店很好吃，我们下次再来。",
                "聊天機器人可以幫助我們回答問題。",
                "這裡的麻辣燙很棒！"
        );

        for (String chatLog : chatLogs) {
            Set<String> twoGramList = splitWord(chatLog)
                    .stream()
                    .flatMap(word -> generate2grams(word).stream()).collect(Collectors.toSet());
            System.out.println(twoGramList);
        }
    }
    private static Set<String> generate2gramsWord(String text){
        return splitWord(text)
                .stream()
                .flatMap(word -> generate2grams(word).stream())
                .collect(Collectors.toSet());
    }

    private static List<String> splitWord(String text) {
        String simplifiedText = ZhConverterUtil.toSimple(text);
        List<Term> terms = IndexAnalysis.parse(simplifiedText).getTerms();

        return terms.stream()
                .map(Term::getName)
                .filter(name -> name.length() >= 2)
                .toList();
    }

    private static List<String> generate2grams(String word) {
        if (word.length() < 2) return Collections.emptyList();

        if (word.length() == 2) return List.of(word);

        return List.of(word, word.substring(0, 2));
    }
}


