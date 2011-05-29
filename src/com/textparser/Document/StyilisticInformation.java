/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.Document;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class StyilisticInformation implements Serializable{
    protected int _id;
    
    protected Map<Integer, StylisticWord> _values;
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public StyilisticInformation() {
        this._values = new HashMap<Integer, StylisticWord>();
        this._id = 0;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public Integer getId() {
        return this._id;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setId(Integer id) {
        this._id = id;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void resetValues() {
        for(Map.Entry<Integer, StylisticWord> entry : this._values.entrySet()) {
            entry.getValue().setValue(0.0f);                        
        }      
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void addValue(Integer id, Float value) {
        this._values.put(id, new StylisticWord(id, value));
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public StylisticWord findValue(Integer id) {
        return this._values.get(id);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public Map<Integer, StylisticWord> getAll() {
        return this._values;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public void setAll(Collection<StylisticWord> values) {
        this._values.clear();
        
        for(StylisticWord word : values) {
            this._values.put(word.id(), word.clone());
        }
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
        final StyilisticInformation other = (StyilisticInformation) obj;
        if (this._id != other._id) {
            return false;
        }
        if (this._values == null || !this._values.equals(other._values)) {
            return false;
        }
        return true;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this._id;
        hash = 97 * hash + (this._values != null ? this._values.hashCode() : 0);
        return hash;
    }

    
    
}
