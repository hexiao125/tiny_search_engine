/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.searchengine;

import se.kth.id1020.Driver;
import se.kth.id1020.TinySearchEngineBase;

/**
 *
 * @author Xiao
 */
public class Main {
    public static void main(String[] args) throws Exception{
        TinySearchEngineBase searchEngine = new TinySearchEngine();
        Driver.run(searchEngine);
    }
}
