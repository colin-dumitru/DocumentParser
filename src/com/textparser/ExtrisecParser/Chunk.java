/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.ExtrisecParser;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author bkt
 */
public class Chunk implements Serializable{
    private int _sentenceOffset;    //to find offset
    private byte[] _hash;
    private int _wordNo;    //in his sentence  

    public void setHash(byte[] _hash) {
        this._hash = _hash;
    }

    public void setSentenceOffset(int _sentenceNo) {
        this._sentenceOffset = _sentenceNo;
    }

    public void setWordNo(int _wordNo) {
        this._wordNo = _wordNo;
    }

    public byte[] getHash() {
        return _hash;
    }

    public int getSentenceOffset() {
        return _sentenceOffset;
    }

    public int getWordNo() {
        return _wordNo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Chunk other = (Chunk) obj;
        if (this._sentenceOffset != other._sentenceOffset) {
            return false;
        }
        if (!Arrays.equals(this._hash, other._hash)) {
            return false;
        }
        if (this._wordNo != other._wordNo) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this._sentenceOffset;
        hash = 71 * hash + Arrays.hashCode(this._hash);
        hash = 71 * hash + this._wordNo;
        return hash;
    }

    
    
}
