/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lesk;

import edu.mit.jwi.*;
import edu.mit.jwi.item.*;
import edu.mit.jwi.morph.WordnetStemmer;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import muzdima.parser.MParser;

/**
 *
 * @author Neophron
 */
public class WSD {

    private MParser mp = new MParser();
    public static IRAMDictionary dict = null;
    private WordnetStemmer ws;

    public WSD() throws Exception {
        if (dict == null) {
            String path = "C:\\WordNet\\2.1\\dict";
            URL url = new URL("file", null, path);
            dict = new RAMDictionary(url);
            dict.open();
            dict.load(true);
        }
        ws = new WordnetStemmer(dict);
    }

    public Pair<String, String> wsd(String sentence,
            String polysemantic) {
        List s;
        try {
            s = mp.Parse(sentence);
        } catch (Exception e) {
            return new Pair("", "");
        }
        List<Sense> senses = new ArrayList<>();
        //System.out.println(polysemantic);
        IIndexWord idxWord = dict.getIndexWord(polysemantic, POS.NOUN);
        if (idxWord != null) {
            for (IWordID wordID : idxWord.getWordIDs()) {
                senses.add(new Sense(dict.getWord(wordID)));
            }
        }
        idxWord = dict.getIndexWord(polysemantic, POS.ADJECTIVE);
        if (idxWord != null) {
            for (IWordID wordID : idxWord.getWordIDs()) {
                senses.add(new Sense(dict.getWord(wordID)));
            }
        }
        idxWord = dict.getIndexWord(polysemantic, POS.ADVERB);
        if (idxWord != null) {
            for (IWordID wordID : idxWord.getWordIDs()) {
                senses.add(new Sense(dict.getWord(wordID)));
            }
        }
        idxWord = dict.getIndexWord(polysemantic, POS.VERB);
        if (idxWord != null) {
            for (IWordID wordID : idxWord.getWordIDs()) {
                senses.add(new Sense(dict.getWord(wordID)));
            }
        }
        if (senses.isEmpty()) {
            return new Pair("", "");
        }
        //------------
        for (Object pp : s) {
            Pair p = (Pair) pp;
            String str = p.getValue().toString().split("\\(|\\)|\\,")[1];
            if (str.charAt(0) == '#') {
                str = p.getValue().toString().split("\\(|\\)|\\,")[2];
            }
            //System.out.println(str);
            //System.out.println(p.toString());
            for (Sense sense : senses) {
                if (sense.checkSynonim(p.getKey().toString())) {
                    sense.checkEntry(str);
                }
                if (str.equals(polysemantic)) {
                    for (String ss : p.getKey().toString().split(" ")) {
                        sense.checkEntry(ss);
                    }
                }
            }
        }
        int k = -1;
        sentence = sentence.toLowerCase();
        while (true) {
            k = sentence.indexOf(polysemantic, k + 1);
            if (k == -1) {
                break;
            }
            String str = "";
            for (int i = k; i < sentence.length(); i++) {
                if ((sentence.charAt(i) >= 'a') && (sentence.charAt(i) <= 'z')) {
                    str += sentence.charAt(i);
                } else {
                    break;
                }
            }
            for (Sense sense : senses) {
                sense.checkExamples(str);
            }
        }
        //--------------
        Sense sense = senses.get(0);
        for (Sense sen : senses) {
            if (sense.getProb() < sen.getProb()) {
                sense = sen;
            }
        }
        //System.out.println(sense.getProb());
        return sense.getPair();
    }

}
