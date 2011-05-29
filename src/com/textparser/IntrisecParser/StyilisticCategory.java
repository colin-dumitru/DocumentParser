/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.IntrisecParser;

import com.textparser.Document.StylisticWord;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class StyilisticCategory implements Serializable{
    protected Integer _id;
    protected Map<Integer, String> _wordList;    
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public StyilisticCategory() {
        this._wordList = new HashMap<Integer, String>();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void addWordList(Map<String, StylisticWord> words) {
        this._wordList.clear();
        
        for(Map.Entry<String,StylisticWord> entry : words.entrySet())         {
            this._wordList.put(entry.getValue().id(), entry.getKey());
        }
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setWordList(Map<Integer, String> words) {
        this._wordList = words;        
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setId(Integer id) {
        this._id = id;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public Integer id() {
        return this._id;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public Map<Integer, String> wordList() {
        return this._wordList;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StyilisticCategory other = (StyilisticCategory) obj;
        if (!this._id.equals(other._id)) {
            return false;
        }
        if (this._wordList == null || !this._wordList.equals(other._wordList)) {
            return false;
        }
        return true;
    }
    
}
