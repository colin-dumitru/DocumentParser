/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.Tokenizer;

import com.textparser.Document.ParsedDocument;
import com.textparser.Document.ParsedSentence;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

/**
 *
 * @author Administrator
 */
public class BasicTokenizer implements DocumentTokenizer {

    private MODE _mode;

    protected static String EN_MODEL = "lib/en-token.bin";
    protected static Set<String> EN_EXCLUDE_WORDS = new HashSet<String>(Arrays.asList(new String[]{
        "a", "to", "the"}));
    
    protected static TokenizerModel _model;
    protected Tokenizer _tokenizer;  
    
    protected Tokenizer _selfTokenizer;
    
    protected Set<String> _excludeWords;
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
      public void setExcludeWords(Set<String> _excludeWords) {
        this._excludeWords = _excludeWords;
    }
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public BasicTokenizer() throws IOException{
        if(_model == null){
            _model = new TokenizerModel(new FileInputStream(BasicTokenizer.EN_MODEL));
        }    
        
        this._tokenizer = new TokenizerME(_model);
    }

    public void setMode(MODE how){
        this._mode=how;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    public synchronized ParsedDocument convert(ParsedDocument what) {        
        final String stop = ".";
        
        /*this uses a lot of time - subject to */
        String result[] = _tokenizer.tokenize(what.fullText());
        
        ParsedSentence sentence = new ParsedSentence();        
        what.clearSentences();
        
        for(int i=0;i<result.length;i++) {
            if(result[i].equals(stop)) {
                if(this._mode==MODE.INTRINSEC)
                    sentence.addToken(result[i]);
                what.addSentance(sentence);                                
                sentence = new ParsedSentence();
                sentence.setOffset(i);                  //!!!subject to change
                /*all words might be needed for correct POS tag detection*/
            } else if(result[i].matches("[0-9]+") /*|| this._excludeWords.contains(word.toLowerCase())*/){
                /*exclude*/              
            } else if(this._mode==MODE.EXTRINSEC && this._excludeWords.contains(result[i])){
                /*exclude*/
            } else {
                //convert to lowercase
                sentence.addToken(result[i].toLowerCase());
            }
        }
        return what;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

}
