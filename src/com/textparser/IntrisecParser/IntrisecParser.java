/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.IntrisecParser;

import com.textparser.Conversion.ConversionListener;
import com.textparser.Document.IntrisecDocument;
import com.textparser.Document.InvalidDocument;
import com.textparser.Document.ParsedDocument;
import com.textparser.Document.ParsedSentence;
import com.textparser.Document.StylisticWord;
import com.textparser.Document.StylisticSentence;
import com.textparser.Tokenizer.BasicTokenizer;
import com.textparser.Tokenizer.DocumentTokenizer;
import com.textparser.DocumentIO.DocumentWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author catalin.dumitru
 */
public class IntrisecParser extends Thread{
    protected final static String P_WORDS[] = {".",",","!","?",";","(",")","[","]","{","}","-"};
    protected final static String P_POS_WORDS[] = {"CC","CD","DT","EX","FW","IN","JJ","JJR","JJS","LS",
        "MD","Modal","NN","NNS","NP","NPS","PDT","POS","PP","PP$","RB","RBR","RBS","RP","SYM","TO",
        "UH","VB","VBD","VBG","VBN","VBP","VBZ","WDT","WP","WP$","WRB"};
    
    protected IntrisecParams _params;
    protected DocumentTokenizer _tokenizer;
    
    protected List<TokenParser> _parsers;
    
    protected BlockingQueue<ParsedDocument> _documents ;
    protected List<IntrisecListener> _listeners;
    protected int _remaining;
    protected boolean _running;

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public IntrisecParser() {
        this.initialize();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public IntrisecParser(IntrisecParams params) {
        this._params = params;
        
        this.initialize();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    protected void initialize() {
        this._documents = new LinkedBlockingQueue<ParsedDocument>();
        this._listeners = new LinkedList<IntrisecListener> ();
        this._remaining = 0;
        
        this._params = IntrisecParams.DEFAULT;
        
        try {
            this._tokenizer = new BasicTokenizer();
            this._tokenizer.setMode(DocumentTokenizer.MODE.INTRINSEC);   //!!!
        } catch (IOException ex) {
            Logger.getLogger(IntrisecParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this._parsers = new LinkedList<TokenParser>();   
        
        this.addPunctuationParser();
        this.addPronounParser();
        this.addStopParser();
        this.addPosParser();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    protected void addPunctuationParser() {
        TokenParser tmp = new TokenParser();
        int start = 0;
        Map<String, Integer> wordList = new HashMap<String, Integer>();
        
        for(String punct : IntrisecParser.P_WORDS) {
            wordList.put(punct, start++);
        }
        
        tmp.setWordList(wordList);
        tmp.setTransformer(new NullTransformer());
        tmp.setId(ParsersTypes.T_PUNCTUATION);
        
        this._parsers.add(tmp);

    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    protected void addPronounParser() {
        TokenParser tmp = new TokenParser();
        int start = 0;
        Map<String, Integer> wordList = new HashMap<String, Integer>();
        
        for(String punct : this._params.getPronounsWords()) {
            wordList.put(punct, start++);
        }
        
        tmp.setWordList(wordList);
        tmp.setTransformer(new NullTransformer());
        tmp.setId(ParsersTypes.T_PRONOUNS);
        
        this._parsers.add(tmp);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    protected void addStopParser() {
       TokenParser tmp = new TokenParser();
        int start = 0;
        Map<String, Integer> wordList = new HashMap<String, Integer>();
        
        for(String punct : this._params.getStopWords()) {
            wordList.put(punct, start++);
        }
        
        tmp.setWordList(wordList);
        tmp.setTransformer(new NullTransformer());
        tmp.setId(ParsersTypes.T_STOP_WORDS);
        
        this._parsers.add(tmp); 
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    protected void addPosParser() {
       TokenParser tmp = new TokenParser();
        int start = 0;
        Map<String, Integer> wordList = new HashMap<String, Integer>();
        
        for(String punct : IntrisecParser.P_POS_WORDS) {
            wordList.put(punct, start++);
        }
        
        tmp.setWordList(wordList);
        tmp.setTransformer(new PosTransformer());
        tmp.setId(ParsersTypes.T_POS_TAGS);
        
        this._parsers.add(tmp);  
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setParams(IntrisecParams params) {
        this._params = params;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void addDocument(ParsedDocument document) throws InvalidDocument {
        if(document == null)
            throw new InvalidDocument("No text found!");
        
        this._documents.add(document);
        this._remaining++;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void safeStop() {
        this._running = false;
        
        try{
            this.stop();
        } catch(Exception ex) {
            
        }
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void safeStart() {
        this._running = true;
        this.start();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void addListener(IntrisecListener listener) {
        this._listeners.add(listener);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void removeListener(IntrisecListener listener) {
        this._listeners.remove(listener);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    synchronized protected void sendParsingComplete(IntrisecDocument document) {
        for(IntrisecListener listener : this._listeners) {
            listener.intrisecParsingComplete(document);
        }
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    public void run() {
        ParsedDocument current = null;
        
        while(this._running) 
        {            
            try {
                current = this._documents.take();
            } catch (Exception ex) {
                /*server stopped*/
            }
            
            this._remaining--;
            
            if(current == null)
                this.sendParsingComplete(new IntrisecDocument());
            else
                this.sendParsingComplete(this.parseDocument(current));
        }
        
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    protected synchronized IntrisecDocument parseDocument(ParsedDocument document) {
        if(document == null)
            return new IntrisecDocument();
        
        IntrisecDocument ret = new IntrisecDocument();
        StylisticSentence ssentence = new StylisticSentence();
        
        /*reset the total*/
        for(TokenParser parser : this._parsers) {
            parser.resetTotal();                
        }
        
        /*tokenize the document*/
        this._tokenizer.convert(document);
        
        /*send each sentance from the document to each token parser*/
        for(ParsedSentence sentence : document.sentances()) {
            ssentence = new StylisticSentence();
            
            for(TokenParser parser : this._parsers) {
                /*gather the statistic and add it to the sentence*/
                ssentence.addInformation(parser.parseSentence(sentence.tokens()));                
            }
            //adding sentence with frequencies
            ret.addSentence(ssentence);
        }
        
        for(TokenParser parser : this._parsers) {
            //total # occurences of wds that each parser looked for
            ret.addTotal(parser.getTotal());   
            ret.addCategory(parser.asCategory());
        }     
        
        ret.setName(document.name());
        ret.setPath(document.path());
        
        return ret;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public synchronized int documentRemaining() {
        return this._remaining;        
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

}
