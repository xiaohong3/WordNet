package com.wordnet;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.DirectedCycle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordNet {
    private List<String> words;
    private Digraph graph;
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null|| hypernyms == null) {
            throw new java.lang.IllegalArgumentException("Input file names are null.");
        }
        In synsetsIn = new In(synsets);
        In hypernymsIn = new In(hypernyms);
        String[] lines = synsetsIn.readAllLines();
        graph = new Digraph(lines.length);
        words = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            String word = parts[1];
            words.add(word);
        }
        while (hypernymsIn.hasNextLine()) {
            String line = hypernymsIn.readLine();
            String[] parts = line.split(",");
            int length = parts.length;
            int v, w;
            if (length < 2) {
                v = Integer.parseInt(parts[0]);
                w = v;
                graph.addEdge(v, w);
            } else {
                v = Integer.parseInt(parts[0]);
                for (int i = 1; i < length; i++) {
                    w = Integer.parseInt(parts[i]);
                    graph.addEdge(v, w);
                }
            }

        }

        // check if has cycle
        //DirectedCycle dc = new DirectedCycle(graph);
        //if (dc.hasCycle()) {
        //    throw new java.lang.IllegalArgumentException("The digraph has cycle.");
        //}
        
        // check if has more than 1 root
        int rootCounter = 0;
        for (int i = 0; i < graph.V(); i ++) {
             if (graph.outdegree(i) == 0) {
                 rootCounter += 1;
             }
             if (rootCounter > 1) {
                 throw new java.lang.IllegalArgumentException("The digraph has more than 1 root.");
             }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return words;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (getNounIndex(word) == -1) {
            return false;
        }
        return true;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        SAP shortestAncestralPath = new SAP(graph);
        int indexA = getNounIndex(nounA);
        int indexB = getNounIndex(nounB);
        return shortestAncestralPath.length(indexA, indexB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        SAP shortestAncestralPath = new SAP(graph);
        int indexA = getNounIndex(nounA);
        int indexB = getNounIndex(nounB);
        int ancestorWordIndex = shortestAncestralPath.ancestor(indexA, indexB);
        return words.get(ancestorWordIndex);
    }
    
    private int getNounIndex(String noun) {
        return words.indexOf(noun);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet("wordnet/synsets.txt", "wordnet/hypernyms.txt");
        System.out.println(wn.graph.V());
        System.out.println(wn.graph.E());
    }
}
