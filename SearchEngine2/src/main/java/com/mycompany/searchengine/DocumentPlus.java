/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.searchengine;

import se.kth.id1020.util.Document;

/**
 *
 * @author Xiao
 */
public class DocumentPlus extends Document{
    
    int count = 0;
    int occurrence = 0;
    
    public DocumentPlus(AttrPlus attrPlus){

        super(attrPlus.document.name, attrPlus.document.popularity);
        this.count = attrPlus.count;
        this.occurrence = attrPlus.occurrence;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Document{").append(name).append(", pop=").append(popularity).append("} ");
        sb.append("count=").append(this.count);
        sb.append(", occurrence=").append(this.occurrence);
        return sb.toString();
    }
}
