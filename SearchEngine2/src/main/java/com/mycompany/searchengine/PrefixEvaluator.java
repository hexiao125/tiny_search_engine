/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.searchengine;

import java.util.ArrayList;
import java.util.Stack;
import java.util.HashMap;

/**
 *
 * @author Xiao
 */
public class PrefixEvaluator {

    Stack<ArrayList> stack = new Stack<>();
    HashMap<String, ArrayList<AttrPlus>> index;
    HashMap<String, ArrayList<AttrPlus>> cache;
    
    public PrefixEvaluator(HashMap<String, ArrayList<AttrPlus>> index, HashMap<String, ArrayList<AttrPlus>> cache){
        this.index = index;
        this.cache = cache;
    }

    public ArrayList evaluate(String[] tokens) {
        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];
            // (word,word,oper) pattern 
            if (i > 1 && !isOperator(token) && !isOperator(tokens[i-1]) && isOperator(tokens[i-2]) ){
                checkCache(token,tokens[i-1],tokens[i-2]);
                i = i - 2;
            } else { // normal process
                processToken(token);
            }   
        }
        return stack.pop();
    }

    private void checkCache(String token1, String token2, String token3) {
        String key1 = token2 + token3 + token1;  // word1 operator word2
        String key2 = token1 + token3 + token2;  // word2 operator word1
        if (!cache.isEmpty() && cache.containsKey(key1)){
            stack.push(cache.get(key1));
        } else if (!cache.isEmpty() && cache.containsKey(key2) && !token3.equals("-")  ){
            stack.push(cache.get(key2));
        } else {
            SearchResult searchResult = new SearchResult(index.get(token2));
            ArrayList al = index.get(token1);
            
            switch (token3) {
            case "+":
                searchResult.intersection(al);
                break;
            case "|":
                searchResult.union(al);
                break;
            case "-":
                searchResult.difference(al);
                break;
            default:
                // not valid operator
            }
            
            stack.push(searchResult.searchResult);
            cache.put(key1, searchResult.searchResult);
        }
        /*for (String key : cache.keySet()){
            System.out.println("key: " + key);
        }*/
            
    }
    
    private boolean isOperator (String token){
        if (token.equals("+") || token.equals("|") || token.equals("-")){
            return true;
        } 
        return false;
    }
    
    private void processToken(String token) {
        switch (token) {
            case "+":
                insec();
                break;
            case "|":
                union();
                break;
            case "-":
                diff();
                break;
            default:
                stack.push(index.get(token));
        }
    }
    
    private void insec() {
        SearchResult searchResult = new SearchResult(stack.pop());
        ArrayList al = stack.pop();
        searchResult.intersection(al);
        stack.push(searchResult.searchResult);
    }
    
    private void union(){
        SearchResult searchResult = new SearchResult(stack.pop());
        ArrayList al = stack.pop();
        searchResult.union(al);
        stack.push(searchResult.searchResult);
    }
    
    private void diff(){
        SearchResult searchResult = new SearchResult(stack.pop());
        ArrayList al = stack.pop();
        searchResult.difference(al);
        stack.push(searchResult.searchResult);
    }
}
