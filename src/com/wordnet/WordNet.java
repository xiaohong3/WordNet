package com.wordnet;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.DirectedCycle;

import java.util.*;

public class WordNet {
    private Map<String, List<Integer>> nounIdMap;
    private List<String> words;
    private Digraph graph;
    private int V;
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null|| hypernyms == null) {
            throw new java.lang.IllegalArgumentException("Input file names are null.");
        }
        nounIdMap= new HashMap<>();
        words = new ArrayList<>();
        In synsetsIn = new In(synsets);
        In hypernymsIn = new In(hypernyms);
        while (synsetsIn.hasNextLine()) {
            V += 1;
            String line = synsetsIn.readLine();
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            String synset = parts[1];
            words.add(synset);
            String[] synsetNouns = synset.split(" ");
            for (String n: synsetNouns) {
                if (nounIdMap.containsKey(n)) {
                    nounIdMap.get(n).add(id);
                } else {
                    List<Integer> list = new ArrayList<>();
                    list.add(id);
                    nounIdMap.put(n, list);
                }
            }
        }
        graph = new Digraph(V);
        while (hypernymsIn.hasNextLine()) {
            String line = hypernymsIn.readLine();
            String[] parts = line.split(",");
            int length = parts.length;
            int v, w;
            if (length >= 2) {
                v = Integer.parseInt(parts[0]);
                for (int i = 1; i < length; i++) {
                    w = Integer.parseInt(parts[i]);
                    graph.addEdge(v, w);
                }
            }

        }

        // check if has cycle
        DirectedCycle dc = new DirectedCycle(graph);
        if (dc.hasCycle()) {
            throw new java.lang.IllegalArgumentException("The digraph has cycle.");
        }
        
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
        return nounIdMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nounIdMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new java.lang.IllegalArgumentException("NounA and NounB should be in WordNet");
        }
        SAP shortestAncestralPath = new SAP(graph);
        List<Integer> indexA = nounIdMap.get(nounA);
        List<Integer> indexB = nounIdMap.get(nounB);
        return shortestAncestralPath.length(indexA, indexB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new java.lang.IllegalArgumentException("NounA and NounB should be in WordNet");
        }
        SAP shortestAncestralPath = new SAP(graph);
        List<Integer> indexA = nounIdMap.get(nounA);
        List<Integer> indexB = nounIdMap.get(nounB);
        int ancestorWordIndex = shortestAncestralPath.ancestor(indexA, indexB);
        return words.get(ancestorWordIndex);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet("wordnet/synsets8.txt", "wordnet/hypernyms8WrongBFS.txt");
    }
}
