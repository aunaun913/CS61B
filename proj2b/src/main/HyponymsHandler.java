package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.*;
import java.util.stream.Collectors;

public class HyponymsHandler extends NgordnetQueryHandler {
    myGraph data;
    NGramMap ngram;
    HashMap<String, Double> wordsToFrequency;
    public HyponymsHandler(myGraph graph, NGramMap gramMap){
        data = graph;
        ngram = gramMap;
    }
    static class FrequencyComparator implements Comparator<Map.Entry<String, Double>>{
        @Override
        public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
            if(o1.getValue() < o2.getValue()){
                return -1;
            }
            if (o1.getValue() > o2.getValue()){
                return 1;
            }
            return o1.getKey().compareTo(o2.getKey());
        }
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int start = q.startYear();
        int end = q.endYear();
        int k = q.k();
        if (k == 0){
            return data.search_hyponyms(words);
        }else {
            wordsToFrequency = new HashMap<>();
            String wild_result = data.search_hyponyms(words);
            wild_result = wild_result.substring(1, wild_result.length() - 1);
            String[] s_list = wild_result.split(", ");
            for(String s : s_list){
                TimeSeries wordTs = ngram.countHistory(s, start, end);
                double sum = 0;
                for (double val : wordTs.values()) {
                    sum += val;
                }
                wordsToFrequency.put(s, sum);
            }
            Comparator<Map.Entry<String, Double>> mycomparator = new FrequencyComparator();
            Map<String, Double> sorted = wordsToFrequency.entrySet()
                    .stream()
                    .sorted(mycomparator.reversed())
                    .limit(k)
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1,
                            LinkedHashMap::new
                    ));
            List<String> result = new ArrayList<>(sorted.keySet());
            return result.toString();
        }
    }
}
