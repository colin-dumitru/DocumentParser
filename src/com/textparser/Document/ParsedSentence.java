/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.Document;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class ParsedSentence {
    private int _offset;    //!!!
    private List<String> _tokens;

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public ParsedSentence(List<String> _tokens) {
        this._tokens = _tokens;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public ParsedSentence() {
        this._tokens = new ArrayList<String>();
    }    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public List<String> tokens() {
        return this._tokens;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setTokens(List<String> tokens) {
        this._tokens = tokens;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void addToken(String token) {
        this._tokens.add(token);                
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setOffset(int _offset) {
        this._offset = _offset;
    }

    public int getOffset() {
        return _offset;
    }

    public String getToken(int pos){
        return this._tokens.get(pos);
    }

    

}
