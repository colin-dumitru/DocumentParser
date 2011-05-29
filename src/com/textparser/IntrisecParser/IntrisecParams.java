/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.IntrisecParser;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author catalin.dumitru
 */
public class IntrisecParams {
    protected List <String> _stopWords;
    protected List <String> _pronounsWords;

    public static IntrisecParams DEFAULT = new IntrisecParams(new ArrayList<String>(),
                                                                new ArrayList<String>());

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public IntrisecParams(List<String> stopWords, List<String> pronouns) {
        this._stopWords = stopWords;
        this._pronounsWords = pronouns;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setStopWords(List<String> _stopWords) {
        for(String s: _stopWords)
            this._stopWords.add(s.toLowerCase());
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setPronounsList(List<String> pronouns) {
        for(String s: pronouns)
            this._pronounsWords.add(s.toLowerCase());
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public List<String> getStopWords() {
        return this._stopWords;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public List<String> getPronounsWords() {
        return this._pronounsWords;
    }


}
