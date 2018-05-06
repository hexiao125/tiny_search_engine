/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.searchengine;

import static java.lang.Math.log10;
import java.util.Comparator;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;

/**
 *
 * @author Xiao
 */
public class AttrPlus extends Attributes {
    
    public int count;
    public double relevance;
    
    public AttrPlus(Attributes attr){
        super(attr.document, attr.occurrence);
        this.count = 1;
    }
    
     public AttrPlus(AttrPlus attrPlus){
        super(attrPlus.document, attrPlus.occurrence);
        this.count = attrPlus.count;
        this.relevance = attrPlus.relevance;
    }
    
    public void updateRelevance(int totalDoc, int wordRelvtDoc, int totalWord){
        this.relevance = (double)this.count / totalWord * log10 ((double)totalDoc / wordRelvtDoc);
    } 
    
    public static class Comparators {

      //  public static Comparator COUNT = (Comparator<AttrPlus>) (AttrPlus a1, AttrPlus a2) -> a1.count - a2.count;
        public static Comparator POPULARITY = new Comparator<AttrPlus>() {
            @Override
            public int compare(AttrPlus a1, AttrPlus a2) {
                return a1.document.popularity - a2.document.popularity;
            }
        };
        public static Comparator RELEVANCE = new Comparator<AttrPlus>() {
            @Override
            public int compare(AttrPlus a1, AttrPlus a2) {
                return (int) (a1.relevance - a2.relevance);
            }
        };
    }
}
