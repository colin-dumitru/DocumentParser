/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.ExtrisecParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author bkt
 */
public class ExtrinsecParams {
    protected List<String> _stopWords;
    protected List<String> _shortWords;
    protected int _chunkSize;
    protected int _chunkDistance;   //distance(in words) between 2 adjacent chunks
    protected static final String[] _punctuationMarks={".",",",":","-",";","@","!","?","±","#","$",
                                    "%","^","&","*","(",")","_","+","{","}","[","]","'","\"","<",">",
                                    "|","`"};

    public static ExtrinsecParams DEFAULT = new ExtrinsecParams(new ArrayList<String>(),
                                                                new ArrayList<String>(),
                                                                1,1);
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public ExtrinsecParams(List<String> _stopWords, List<String> _shortWords, int _chunkSize,
            int _chunkDistance) {
        this._stopWords = _stopWords;
        this._shortWords = _shortWords;
        this._chunkSize = _chunkSize;
        this._chunkDistance = _chunkDistance;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public int getChunkSize() {
        return _chunkSize;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public List<String> getShortWords() {
        return _shortWords;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public List<String> getStopWords() {
        return _stopWords;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setChunkSize(int _chunkSize) {
        this._chunkSize = _chunkSize;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setShortWords(List<String> _shortWords) {
        for(String s: _shortWords)
            this._shortWords.add(s.toLowerCase());
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setStopWords(List<String> _stopWords) {
        for(String s: _stopWords)
            this._stopWords.add(s.toLowerCase());
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public Set<String> getAll(){
        Set<String> all=new HashSet<String>();        
        if(all.addAll(Arrays.asList(_punctuationMarks))&&all.addAll(_stopWords)
                &&all.addAll(_shortWords)){
            return all;
        }
        return null;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public int getChunkDistance() {
        return _chunkDistance;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setChunkDistance(int _chunkDistance) {
        this._chunkDistance = _chunkDistance;
    }
    
}
