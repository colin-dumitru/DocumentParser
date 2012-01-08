/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.Document;

import com.textparser.ExtrisecParser.Chunk;
import com.textparser.DocumentIO.Visitor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author bkt
 */
public class ExtrinsecDocument implements Serializable{
    protected HashMap<byte[],Chunk> _hashes;
    protected String _name;
    protected String _docPath;
    protected String _binaryPath;
    
    protected transient Visitor _generator;

    public ExtrinsecDocument() {
        this._hashes = new HashMap<byte[], Chunk>();
    }

    public ExtrinsecDocument(String xmlPath,Visitor loader) {
        this._binaryPath=xmlPath;
        loader.visit(this);
    }
    
    public void setName(String _name) {
        this._name = _name;
    }

    public void addChunkHash(Chunk chk){
        this._hashes.put(chk.getHash(), chk);
    }

    public HashMap<byte[], Chunk> getHashes() {
        return _hashes;
    }

    public void setHashes(HashMap<byte[], Chunk> _hashes) {
        this._hashes = _hashes;
    }

    

    public String getName() {
        return _name;
    }

    public void write() {
        this._generator.visit(this);
    }

    public Visitor getGenerator() {
        return _generator;
    }

    public void setGenerator(Visitor _generator) {
        this._generator = _generator;
    }

    public String getBinaryPath() {
        return _binaryPath;
    }

    public void setBinaryPath(String _xmlPath) {
        this._binaryPath = _xmlPath;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExtrinsecDocument other = (ExtrinsecDocument) obj;
        if ((this._hashes == null || !new ArrayList<Chunk>(other._hashes.values()).containsAll(
                (new ArrayList<Chunk>(this._hashes.values()))))) {
            return false;
        }
        if ((this._name == null) ? (other._name != null) : !this._name.equals(other._name)) {
            return false;
        }
        if ((this._docPath == null) ? (other._docPath != null) : !this._docPath.equals(other._docPath)) {
            return false;
        }
        if ((this._binaryPath == null) ? (other._binaryPath != null) : !this._binaryPath.equals(other._binaryPath)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this._hashes != null ? this._hashes.hashCode() : 0);
        hash = 97 * hash + (this._name != null ? this._name.hashCode() : 0);
        hash = 97 * hash + (this._docPath != null ? this._docPath.hashCode() : 0);
        hash = 97 * hash + (this._binaryPath != null ? this._binaryPath.hashCode() : 0);
        return hash;
    }

    public String getDocPath() {
        return _docPath;
    }

    public void setDocPath(String _docPath) {
        this._docPath = _docPath;
    }
    
    
    
}

