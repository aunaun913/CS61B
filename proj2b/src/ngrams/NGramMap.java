package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    HashMap<String, TimeSeries> totalWordsMap = new HashMap<>();
    TimeSeries totalCountsMap = new TimeSeries();
    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        In wordReader = new In(wordsFilename);
        In countReader = new In(countsFilename);
        while (wordReader.hasNextLine()){
            String text = wordReader.readLine();
            String[] content = text.split("\t");
            String word = content[0];
            int year = Integer.parseInt(content[1]);
            double frequency = Double.parseDouble(content[2]);
            TimeSeries ts = totalWordsMap.getOrDefault(word, new TimeSeries());
            ts.put(year, frequency);
            totalWordsMap.put(word, ts);
        }
        while (countReader.hasNextLine()){
            String text = countReader.readLine();
            String[] content = text.split(",");
            int year = Integer.parseInt(content[0]);
            double frequency = Double.parseDouble(content[1]);
            totalCountsMap.put(year, frequency);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (!totalWordsMap.containsKey(word)){
            return new TimeSeries();
        }
        TimeSeries old = totalWordsMap.get(word);
        TimeSeries sub = new TimeSeries();
        for (int year : old.years()) {
            if (year >= startYear && year <= endYear) {
                sub.put(year, old.get(year));
            }
        }
        return sub;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (!totalWordsMap.containsKey(word)){
            return new TimeSeries();
        }
        TimeSeries old = totalWordsMap.get(word);
        TimeSeries sub = new TimeSeries();
        for (int year : old.years()) {
            sub.put(year, old.get(year));
        }
        return sub;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(totalCountsMap, totalCountsMap.years().get(0), totalCountsMap.years().get(totalCountsMap.size() - 1));
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if(totalWordsMap.containsKey(word)){
            TimeSeries wordCountTS = countHistory(word, startYear, endYear);
            TimeSeries totalCountTS = totalCountHistory();
            return wordCountTS.dividedBy(totalCountTS);
        }else {
            return new TimeSeries();
        }
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if(totalWordsMap.containsKey(word)){
            TimeSeries wordCountTS = countHistory(word);
            TimeSeries totalCountTS = totalCountHistory();
            return wordCountTS.dividedBy(totalCountTS);
        }else {
            return new TimeSeries();
        }
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        TimeSeries result = new TimeSeries();
        for (String s : words){
            if (countHistory(s, startYear, endYear).isEmpty()){
                continue;
            }else {
                result = result.plus(weightHistory(s, startYear, endYear));
            }
        }
        return result;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries result = new TimeSeries();
        for (String s : words){
            if (countHistory(s).isEmpty()){
                continue;
            }else {
                result = result.plus(weightHistory(s));
            }
        }
        return result;
    }
}
