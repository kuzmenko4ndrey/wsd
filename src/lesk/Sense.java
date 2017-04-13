/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lesk;

import edu.mit.jwi.*;
import edu.mit.jwi.item.*;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author Neophron
 */
public class Sense {

    private final IWord word;
    private final List<String> synonims;
    private int prob = 0;

    public Sense(IWord sense) {
        word = sense;
        synonims = new ArrayList<>();
        ISynset is = word.getSynset();
        for (IWord iw : is.getWords()) {
            synonims.add(iw.getLemma().replaceAll("_", " "));
        }
    }

    public Pair<String, String> getPair() {
        return new Pair(word.getSenseKey().toString().split("%")[1],
                word.getSynset().getGloss().split(";")[0]);
    }

    public int getProb() {
        return prob;
    }

    public boolean checkEntry(String s) {
        boolean f = false;
        String meaning = word.getSynset().getGloss().split(";")[0];
        IDictionary dict = WSD.dict;
        IIndexWord idxWord = dict.getIndexWord(s, POS.NOUN);
        if (idxWord != null) {
            for (IWordID wordID : idxWord.getWordIDs()) {
                if (meaning.contains(dict.getWord(wordID).getLemma())) {
                    prob++;
                    f = true;
                }
            }
        }
        idxWord = dict.getIndexWord(s, POS.ADJECTIVE);
        if (idxWord != null) {
            for (IWordID wordID : idxWord.getWordIDs()) {
                if (meaning.contains(dict.getWord(wordID).getLemma())) {
                    prob++;
                    f = true;
                }
            }
        }
        idxWord = dict.getIndexWord(s, POS.ADVERB);
        if (idxWord != null) {
            for (IWordID wordID : idxWord.getWordIDs()) {
                if (meaning.contains(dict.getWord(wordID).getLemma())) {
                    prob++;
                    f = true;
                }
            }
        }
        idxWord = dict.getIndexWord(s, POS.VERB);
        if (idxWord != null) {
            for (IWordID wordID : idxWord.getWordIDs()) {
                if (meaning.contains(dict.getWord(wordID).getLemma())) {
                    prob++;
                    f = true;
                }
            }
        }
        if (meaning.contains(s)) {
            prob++;
            f = true;
        }
        return f;
    }

    public boolean checkExamples(String s) {
        String meaning = word.getSynset().getGloss();
        if (meaning.contains(s)) {
            prob++;
            return true;
        }
        return false;
    }

    public boolean checkSynonim(String s) {
        for (String syn : synonims) {
            if (syn.equals(s)) {
                prob++;
                return true;
            }
        }
        return false;
    }

}
