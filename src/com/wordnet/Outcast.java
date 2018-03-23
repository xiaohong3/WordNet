package com.wordnet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordnet;
    public Outcast(WordNet wn) {
        wordnet = wn;
    }
    
    public String outcast(String[] nouns) {
        int distance = 0;
        String noun = null;
        for (int i = 0; i < nouns.length; i++) {
            int d = 0;
            for (int j = 1; j < nouns.length; j++) {
                d += wordnet.distance(nouns[i], nouns[j]);
            }
            if (d > distance) {
                distance = d;
                noun = nouns[i];
            }
        }
        
        return noun;
    }
    
    private String getNoun(int wordIndex) {
        for (String noun : wordnet.nouns()) {
            if(wordIndex == 0)
                return noun;
            else
                wordIndex--;
        }
        return null;
    }
    
    public static void main(String[] args) {
        WordNet wordnet = new WordNet("wordnet/synsets.txt", "wordnet/hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        In in = new In("wordnet/outcast5.txt");
        String[] nouns = in.readAllStrings();
        StdOut.println("Outcast5 " + ": " + outcast.outcast(nouns));
    }
}
