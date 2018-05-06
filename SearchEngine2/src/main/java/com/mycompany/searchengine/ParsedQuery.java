/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.searchengine;



/**
 *
 * @author Xiao
 */
public class ParsedQuery {
    
    public String[] tokens;
    public int hasOrderby;
    public String prop;
    public String dirx;
    
    public ParsedQuery(String query){
        
        this.hasOrderby = query.trim().indexOf("orderby");
        if (this.hasOrderby < 0){
            tokens = query.trim().split(" ");
        } else if (this.hasOrderby > 0){
            String[] twoParts = query.trim().split("orderby");
            tokens = twoParts[0].trim().split(" ");
            String[] orderFactors = twoParts[1].trim().split(" ");
            this.prop = orderFactors[0];
            this.dirx = orderFactors[1];
        }
    }
    
}
