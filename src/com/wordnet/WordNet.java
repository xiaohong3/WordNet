package com.wordnet;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class WordNet {
    String[] words;
    Digraph graph;
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
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
            int v = Integer.parseInt(parts[0]);
            int w = Integer.parseInt(parts[1]);
            graph.addEdge(v, w);
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
    public static void main(String[] args) {}
}
