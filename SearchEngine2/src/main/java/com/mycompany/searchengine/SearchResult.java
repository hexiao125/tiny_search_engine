/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.searchengine;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Xiao
 */
public class SearchResult {
    
    public ArrayList<AttrPlus> searchResult;

    public SearchResult(){
        this.searchResult = new ArrayList<>();
    }
    
    public SearchResult(ArrayList list){
        this.searchResult = list;
    }
    
    public void intersection(ArrayList<AttrPlus> result) {
        int i = 0;
        int j = 0;
        ArrayList<AttrPlus> resultList = new ArrayList<>();

        while (i < this.searchResult.size() && j < result.size()) {
            int cmp = this.searchResult.get(i).document.compareTo(result.get(j).document);
            if (cmp < 0) {
                i++;
            } else if (cmp > 0) {
                j++;
            } else {
                AttrPlus newAttrPlus;
                if (this.searchResult.get(i).occurrence < result.get(j).occurrence) {
                    newAttrPlus = new AttrPlus(this.searchResult.get(i));
                } else {
                    newAttrPlus = new AttrPlus(result.get(j));
                }
                newAttrPlus.count = this.searchResult.get(i).count + result.get(j).count;
                newAttrPlus.relevance = this.searchResult.get(i).relevance + result.get(j).relevance;
                resultList.add(newAttrPlus);
                i++;
                j++;
            }
        }
        this.searchResult = resultList;
    }
    
    public void union(ArrayList<AttrPlus> result) {
        int i = 0;
        int j = 0;
        ArrayList<AttrPlus> resultList = new ArrayList<>();

        while (i < this.searchResult.size() && j < result.size()) {
            int cmp = this.searchResult.get(i).document.compareTo(result.get(j).document);
            if (cmp < 0) {
                resultList.add(this.searchResult.get(i));
                i++;
            } else if (cmp > 0) {
                resultList.add(result.get(j));
                j++;
            } else {
                AttrPlus newAttrPlus;
                if (this.searchResult.get(i).occurrence < result.get(j).occurrence) {
                    newAttrPlus = new AttrPlus(this.searchResult.get(i));
                } else {
                    newAttrPlus = new AttrPlus(result.get(j));
                }
                newAttrPlus.count = this.searchResult.get(i).count + result.get(j).count;
                newAttrPlus.relevance = this.searchResult.get(i).relevance + result.get(j).relevance;
                resultList.add(newAttrPlus);
                i++;
                j++;
            }

        }
        
        while (i < this.searchResult.size()){
            resultList.add(this.searchResult.get(i));
            i++;
        }
        
        while (j < result.size()){
            resultList.add(result.get(j));
            j++;
        }
        
        this.searchResult = resultList;
    }

    public void difference(ArrayList<AttrPlus> result) {
        int i = 0;
        int j = 0;
        ArrayList<AttrPlus> resultList = new ArrayList<>();

        while (i < this.searchResult.size() && j < result.size()) {
            int cmp = this.searchResult.get(i).document.compareTo(result.get(j).document);
            if (cmp < 0) {
                resultList.add(this.searchResult.get(i));
                i++;
            } else if (cmp > 0) {
                j++;
            } else {
                i++;
                j++;
            }

        }

        while (i < this.searchResult.size()) {
            resultList.add(this.searchResult.get(i));
            i++;
        }

        this.searchResult = resultList;
    }

    public void sort(String prop, String dirx) {

        if (prop.equals("popularity")) {
            Collections.sort(searchResult, AttrPlus.Comparators.POPULARITY);
            if (dirx.equals("desc")) {
                Collections.reverse(searchResult);
            }
        } else if (prop.equals("relevance")) {
            Collections.sort(searchResult, AttrPlus.Comparators.RELEVANCE);
            if (dirx.equals("desc")) {
                Collections.reverse(searchResult);
            }
        }

    }
}
