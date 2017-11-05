package com.wordnet;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class SAP {
    private Digraph graph;
    private static final int INFINITY = Integer.MAX_VALUE;
    
    public SAP(Digraph G) {
        graph = G;
    }
    
    public int length(int v, int w) {
        if (!vertexInRange(v) || !vertexInRange(w)) {
            throw new java.lang.IllegalArgumentException("Vertex argument should be in range.");
        }
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(graph, w);
        return getLength(bfsV, bfsW);
    }
    
    public int ancestor(int v, int w) {
        if (!vertexInRange(v) || !vertexInRange(w)) {
            throw new java.lang.IllegalArgumentException("Vertex argument should be in range.");
        }
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(graph, w);
        return getAncestor(bfsV, bfsW);
    }
    
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new java.lang.IllegalArgumentException("Arguments should not be null.");
        }
        
        if (!verticesInRange(v) || !verticesInRange(w)) {
            throw new java.lang.IllegalArgumentException("Vertices argument should be in range.");
        }
        
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(graph, w);
        return getLength(bfsV, bfsW);
    }
    
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(graph, w);
        return getAncestor(bfsV, bfsW);   
    }
    
    private int getLength(BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW) {
        int length = INFINITY;
        for (int i = 0; i < graph.V(); i++) {
            int vDistToVetex = bfsV.distTo(i);
            int wDistToVetex = bfsW.distTo(i);
            if (vDistToVetex != INFINITY && wDistToVetex != INFINITY) {
                int d = vDistToVetex + wDistToVetex;
                if (length == INFINITY || length > d) {
                    length = d;
                }
            }
        }
        if (length == INFINITY) {
            return -1;
        }
        return length;
    }
    
    private int getAncestor(BreadthFirstDirectedPaths bfsV, BreadthFirstDirectedPaths bfsW) {
        int length = INFINITY;
        int ancestor = INFINITY;
        for (int i = 0; i < graph.V(); ++i) {
            int vDistToVetex = bfsV.distTo(i);
            int wDistToVetex = bfsW.distTo(i);
            if (vDistToVetex != INFINITY && wDistToVetex != INFINITY) {
                int d = vDistToVetex + wDistToVetex;
                if (length == INFINITY || length > d) {
                    length = d;
                    ancestor = i;
                }
            }
        }
        if (ancestor == INFINITY) {
            return -1;
        }
        return ancestor;
    }
    
    private boolean vertexInRange(int v) {
        if (v < 0 || v > graph.V() - 1) {
            return false;
        }
        return true;
    }
    
    private boolean verticesInRange(Iterable<Integer> vertices) {
        for (int v : vertices) {
            if (vertexInRange(v)) {
                return true;
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        In inputStream = new In("wordnet/digraph1.txt");
        Digraph graph = new Digraph(inputStream);
        SAP sap = new SAP(graph);
        int v = 1;
        int w = 6;
        System.out.println("length " + sap.length(v, w) + " ancestor " + sap.ancestor(v, w));
    }
}
