/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.IntrisecParser;

import com.textparser.Document.StylisticWord;
import com.textparser.Document.StyilisticInformation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Administrator
 */
public class TokenParser {
   
   
   //-----------------------------------------------------------------------------------------------
   //-----------------------------------------------------------------------------------------------
   protected Map<String, StylisticWord> _wordList;
   protected SentenceTransformer _transfomer;
   protected StyilisticInformation _total;
   protected Integer _id;
   
   //-----------------------------------------------------------------------------------------------
   //-----------------------------------------------------------------------------------------------
   public TokenParser() {
       this._wordList = new HashMap<String, StylisticWord>();
       this._total = new StyilisticInformation();
   }
   //-----------------------------------------------------------------------------------------------
   //-----------------------------------------------------------------------------------------------
   public void setId(Integer id) {
       this._id = id;
       this._total.setId(id);
   }
   //-----------------------------------------------------------------------------------------------
   //-----------------------------------------------------------------------------------------------
   public Integer id() {
       return this._id;
   }
   //-----------------------------------------------------------------------------------------------
   //-----------------------------------------------------------------------------------------------
   public void setWordList(Map<String, Integer> words) {
       this._wordList.clear();
       
       for(Map.Entry<String,Integer> word : words.entrySet())        {
            this._wordList.put(word.getKey(), new StylisticWord(word.getValue(), 0.0f));
       }
       
       this._total.setAll(this._wordList.values());
   }
   //-----------------------------------------------------------------------------------------------
   //-----------------------------------------------------------------------------------------------
   public Map<String, StylisticWord> wordList() {
       return this._wordList;
   }
   //-----------------------------------------------------------------------------------------------
   //-----------------------------------------------------------------------------------------------
   //apelat doar de IntrinsecParser cu arg new POSTransformer
   public void setTransformer(SentenceTransformer transformer) {
       this._transfomer = transformer;
   }
   //-----------------------------------------------------------------------------------------------
   //-----------------------------------------------------------------------------------------------
   public void resetTotal() {
       this._total.resetValues();       
   }
   //-----------------------------------------------------------------------------------------------
   //-----------------------------------------------------------------------------------------------
   public StyilisticInformation getTotal() {
       return this._total;       
   }
   //-----------------------------------------------------------------------------------------------
   //-----------------------------------------------------------------------------------------------
   public StyilisticCategory asCategory() {
       StyilisticCategory ret = new StyilisticCategory();
       
       ret.setId(_id);
       ret.addWordList(_wordList);
       
       return ret;
   }
   //-----------------------------------------------------------------------------------------------
   //-----------------------------------------------------------------------------------------------
   public StyilisticInformation parseSentence(List<String> words) {
       if(this._transfomer == null || this._wordList.size() <= 0 || words.size() <= 0)       
           return null;
       
       StyilisticInformation ret = new StyilisticInformation();
       ret.setAll(this._wordList.values());
       ret.setId(_id);

       //numai pt pos se intoarce o lista diferita in transform
       for(String word : this._transfomer.transform(words)) {
           StylisticWord tmp = this._wordList.get(word);

           //daca cuvantul e in lista celor pt care trebuie sa calculam frecv
           if(tmp != null) {
               tmp.setValue(tmp.value() + 1.0f);  
               
               tmp = ret.findValue(tmp.id());           
               if(tmp != null) {
                   tmp.setValue(tmp.value() + 1.0f);                 
               } 
               
               tmp = _total.findValue(tmp.id());           
               if(tmp != null) {
                   tmp.setValue(tmp.value() + 1.0f);                 
               } 
           }   
       }
              
       return ret;
   }
   //-----------------------------------------------------------------------------------------------
   //-----------------------------------------------------------------------------------------------
}
