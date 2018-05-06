/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.searchengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import se.kth.id1020.TinySearchEngineBase;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Sentence;
import se.kth.id1020.util.Word;

/**
 *
 * @author Xiao
 */
public class TinySearchEngine implements TinySearchEngineBase {
    
    HashMap<String, ArrayList<AttrPlus>> index = new HashMap<>();
    HashMap<String, Integer> docInfo = new HashMap<>();
    HashMap<String, ArrayList<AttrPlus>> cache = new HashMap<>();
    
    @Override
    public void preInserts() {
        
    }
    
    @Override
    public void insert(Sentence sentence, Attributes attr) {
        for (Word word : sentence.getWords()) {
            insert(word,attr);
        }
    }
    
    @Override
    public void postInserts() {
        int totalDoc = docInfo.size();
        for (HashMap.Entry<String, ArrayList<AttrPlus>> entry : index.entrySet())
        {
            int wordRelvtDoc = entry.getValue().size();
            for (AttrPlus attrPlus : entry.getValue()) {
                int totalWord = docInfo.get(attrPlus.document.name);
                attrPlus.updateRelevance(totalDoc, wordRelvtDoc,totalWord);
            }
        }
        
    }
    private void insert(Word word, Attributes attr) {
        
        // docInfo calculation
        if ((!docInfo.isEmpty()) && docInfo.containsKey(attr.document.name)) {
            docInfo.put(attr.document.name, docInfo.get(attr.document.name)+1);
        } else {
            docInfo.put(attr.document.name, 1);
        }
        
        if ((!index.isEmpty()) && index.containsKey(word.word)) {
            for (int i = 0; i < index.get(word.word).size(); i++) {                
                if (index.get(word.word).get(i).document.compareTo(attr.document) == 0) {
                    index.get(word.word).get(i).count++;                    
                    return;
                }
            }
            AttrPlus attrPlus1 = new AttrPlus(attr);
            index.get(word.word).add(attrPlus1);
        } else {
            AttrPlus attrPlus = new AttrPlus(attr);
            ArrayList<AttrPlus> attrList = new ArrayList<>();
            attrList.add(attrPlus);
            index.put(word.word, attrList);
        }
    }
    
    @Override
    public List<Document> search(String query) {
        ParsedQuery parsedQuery = new ParsedQuery(query);
        PrefixEvaluator prefixEvaluator = new PrefixEvaluator(index,cache);
        ArrayList alist = prefixEvaluator.evaluate(parsedQuery.tokens);
        SearchResult searchResult = new SearchResult();
        searchResult.searchResult = alist;
        if (parsedQuery.hasOrderby > 0) {
            searchResult.sort(parsedQuery.prop, parsedQuery.dirx);
        }
        ArrayList docList = new ArrayList<Document>();
        for (Iterator<AttrPlus> it = searchResult.searchResult.iterator(); it.hasNext();) {
            docList.add(it.next().document);
        }

        return docList;
    }
    
    @Override
    public String infix(String query) {
        StringBuilder sb = new StringBuilder("Query(");
        Stack<String> st = new Stack<>();
        ParsedQuery parsedQuery = new ParsedQuery(query);
        for (int i = parsedQuery.tokens.length - 1; i >= 0; i--) {
            st.push(parsedQuery.tokens[i]);
        }
        prefixToInfix(st, sb);
        if (parsedQuery.hasOrderby > 0) {
            sb.append(" ORDERBY ");
            sb.append(parsedQuery.prop.toUpperCase());
            sb.append(" ");
            sb.append(parsedQuery.dirx.toUpperCase()); 
        }
        sb.append(")");
        return sb.toString();
    }

    private void prefixToInfix(Stack<String> st, StringBuilder sb) {
        
        if (!st.empty()) {
            String temp = st.pop();
            if (temp.equals("+") || temp.equals("-") || temp.equals("|")) {
                sb.append("(");
                prefixToInfix(st, sb);
                sb.append(" " + temp + " ");
                prefixToInfix(st, sb);
                sb.append(")");
            } else {
                sb.append(temp);
            }
        }
    }
  
}
