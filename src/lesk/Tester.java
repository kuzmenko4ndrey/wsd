/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lesk;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Neophron
 */
public class Tester {
    
    public static void main(String[] args) throws Exception {
        WSD wsd = new WSD();
        String sentence = "He urged support for President Kennedy's request for both defense and foreign aid appropriations.";
        //sentence = "i went to bank building";
        //sentence = "i saw bank in red building";
        String l;
        l = "urge";
        //l = "bank";
        System.out.println(sentence);
        System.out.println(l);
        System.out.println(wsd.wsd(sentence, l).toString());
        System.out.println("----------------------------------");
        sentence = "i went to bank building";
        l = "bank";
        System.out.println(sentence);
        System.out.println(l);
        System.out.println(wsd.wsd(sentence, l).toString());
        System.out.println("----------------------------------");
        sentence = "i saw bank in red building";
        l = "bank";
        System.out.println(sentence);
        System.out.println(l);
        System.out.println(wsd.wsd(sentence, l).toString());
        System.out.println("----------------------------------");
        sentence = "Apple is a red fruit";
        l = "apple";
        System.out.println(sentence);
        System.out.println(l);
        System.out.println(wsd.wsd(sentence, l).toString());
        System.out.println("----------------------------------");
        sentence = "A simplified version of the Lesk algorithm is to compare the dictionary definition of an ambiguous word with the terms contained in its neighborhood.";
        l = "algorithm";
        System.out.println(sentence);
        System.out.println(l);
        System.out.println(wsd.wsd(sentence, l).toString());
        System.out.println("----------------------------------");
        sentence = "These colonies thrived well into the 6th century AD.";
        l = "colony";
        System.out.println(sentence);
        System.out.println(l);
        System.out.println(wsd.wsd(sentence, l).toString());
        System.out.println("----------------------------------");
    }
    
}
