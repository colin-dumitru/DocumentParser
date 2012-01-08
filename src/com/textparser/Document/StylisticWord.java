/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.Document;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class StylisticWord implements Serializable{
   private Integer _id;
   private Float _value;
   
   //-----------------------------------------------------------------------------------------------
   //-----------------------------------------------------------------------------------------------
   public StylisticWord(Integer id, float value) {
       this._id = id;
       this._value = value;
   }
   //-----------------------------------------------------------------------------------------------
   //-----------------------------------------------------------------------------------------------
   public Integer id() {
       return this._id;       
   }
   //-----------------------------------------------------------------------------------------------
   //-----------------------------------------------------------------------------------------------
   public Float value() {
       return this._value;
   }
   //-----------------------------------------------------------------------------------------------
   //-----------------------------------------------------------------------------------------------
   public void setId(Integer id) {
       this._id = id;
   }
   //-----------------------------------------------------------------------------------------------
   //-----------------------------------------------------------------------------------------------
   public void setValue(Float value) {
       this._value = value;
   }
   //-----------------------------------------------------------------------------------------------
   //-----------------------------------------------------------------------------------------------
    @Override
   public StylisticWord clone() {
        return new StylisticWord(_id.intValue(), _value.floatValue());
       
   }
   //-----------------------------------------------------------------------------------------------
   //-----------------------------------------------------------------------------------------------
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StylisticWord other = (StylisticWord) obj;
        if (this._id != other._id && (this._id == null || !this._id.equals(other._id))) {
            return false;
        }
        if (this._value != other._value && (this._value == null || !this._value.equals(other._value))) {
            return false;
        }
        return true;
    }
   //-----------------------------------------------------------------------------------------------
   //-----------------------------------------------------------------------------------------------
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this._id != null ? this._id.hashCode() : 0);
        hash = 67 * hash + (this._value != null ? this._value.hashCode() : 0);
        return hash;
    }

    
}
