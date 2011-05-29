/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class StylisticSentence implements Serializable{
    protected List<StyilisticInformation> _informations;
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public StylisticSentence() {
        this._informations = new ArrayList<StyilisticInformation>();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void addInformation(StyilisticInformation information) {
        this._informations.add(information);                
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public List<StyilisticInformation> information() {
        return this._informations;        
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
        final StylisticSentence other = (StylisticSentence) obj;
        if (this._informations == null || !this._informations.equals(other._informations)) {
            return false;
        }
        return true;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this._informations != null ? this._informations.hashCode() : 0);
        return hash;
    }

}
