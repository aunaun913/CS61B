package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap data;
    public HistoryTextHandler(NGramMap map){
        data = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        StringBuilder response = new StringBuilder();
        for(String s : words) {
            TimeSeries ts = data.weightHistory(s, startYear, endYear);
            response.append(s).append(": ").append(ts.toString()).append("\n");
        }
        return response.toString();
    }
}
