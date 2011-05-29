/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.Document;

import com.textparser.IntrisecParser.StyilisticCategory;
import com.textparser.DocumentIO.Visitor;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class IntrisecDocument implements Serializable{
    protected List<StylisticSentence> _sentences;
    protected List<StyilisticInformation> _total;
    protected List<StyilisticCategory> _categories;
    
    protected String _name;
    protected String _docPath;
    protected String _binaryPath;
    
     protected transient Visitor _generator;
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public IntrisecDocument() {
        this._sentences = new LinkedList<StylisticSentence>();
        this._total = new LinkedList<StyilisticInformation>();
        this._categories = new LinkedList<StyilisticCategory>();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------    
    public IntrisecDocument(String xmlPath,Visitor loader) {
        this._binaryPath=xmlPath;
        loader.visit(this);
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
        final IntrisecDocument other = (IntrisecDocument) obj;
        if (this._sentences == null || !this._sentences.equals(other._sentences)) {
            return false;
        }
        if (this._total == null || !this._total.equals(other._total)) {
            return false;
        }
        if (this._categories == null || !this._categories.equals(other._categories)) {
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
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + (this._sentences != null ? this._sentences.hashCode() : 0);
        hash = 61 * hash + (this._total != null ? this._total.hashCode() : 0);
        hash = 61 * hash + (this._categories != null ? this._categories.hashCode() : 0);
        hash = 61 * hash + (this._name != null ? this._name.hashCode() : 0);
        hash = 61 * hash + (this._docPath != null ? this._docPath.hashCode() : 0);
        hash = 61 * hash + (this._binaryPath != null ? this._binaryPath.hashCode() : 0);
        hash = 61 * hash + (this._generator != null ? this._generator.hashCode() : 0);
        return hash;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------    
    
    
    public void write(){ //todo set xmlpath
        this._generator.visit(this);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public List<StyilisticCategory> categories() {
        return this._categories;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public String name(){
        return this._name;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public String path() {
        return this._docPath;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void clearSentences()     {
        this._sentences.clear();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void addSentence(StylisticSentence what) {
        this._sentences.add(what);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void addTotal(StyilisticInformation info) {
        this._total.add(info);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void addCategory(StyilisticCategory cat) {
        this._categories.add(cat);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setName(String name) {
        this._name = name;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public String getName() {
        return this._name;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setPath(String path) {
        this._docPath = path;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public Visitor getGenerator() {
        return _generator;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setGenerator(Visitor _generator) {
        this._generator = _generator;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public String getBinaryPath() {
        return _binaryPath;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public void setBinaryPath(String _binaryPath) {
        this._binaryPath = _binaryPath;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public List<StyilisticCategory> getCategories() {
        return _categories;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setCategories(List<StyilisticCategory> _categories) {
        this._categories = _categories;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public String getDocPath() {
        return _docPath;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setDocPath(String _docPath) {
        this._docPath = _docPath;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public List<StylisticSentence> getSentences() {
        return _sentences;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setSentences(List<StylisticSentence> _sentences) {
        this._sentences = _sentences;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public List<StyilisticInformation> getTotal() {
        return _total;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setTotal(List<StyilisticInformation> _total) {
        this._total = _total;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    
    
    

}
