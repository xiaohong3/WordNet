package com.wordnet;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.DirectedCycle;

public class WordNet {
    String[] words;
    Digraph graph;
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null|| hypernyms == null) {
            throw new java.lang.IllegalArgumentException("Input file names are null.");
        }
        In synsetsIn = new In(synsets);
        In hypernymsIn = new In(hypernyms);
        String[] lines = synsetsIn.readAllLines();
        graph = new Digraph(lines.length);
        words = new String[lines.length];
        while (synsetsIn.hasNextLine()) {
            String line = synsetsIn.readLine();
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            String word = parts[1];
            words[id] = word;
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
    public Iterable<String> nouns() { return null; }

    // is the word a WordNet noun?
    public boolean isNoun(String word) { return false; }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) { return 0; }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) { return ""; }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet("wordnet/synsets3.txt", "wordnet/hypernyms3InvalidCycle.txt");
        System.out.println(wn.graph.V());
        System.out.println(wn.graph.E());
    }
}
