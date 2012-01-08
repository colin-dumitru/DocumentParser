/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.ExtrisecParser;

import com.textparser.Document.ExtrinsecDocument;
import com.textparser.Document.InvalidDocument;
import com.textparser.Document.ParsedDocument;
import com.textparser.Document.ParsedSentence;
import com.textparser.Tokenizer.BasicTokenizer;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bkt
 */
public class ExtrinsecParser extends Thread{
    protected ExtrinsecParams _params;
    protected BasicTokenizer _tokenizer;

    protected BlockingQueue<ParsedDocument> _documents ;
    protected List<ExtrinsecListener> _listeners;
    protected int _remaining;
    protected boolean _running;

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public ExtrinsecParser() {
        this.initialize();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public ExtrinsecParser(ExtrinsecParams params) {
        this._params = params;
        this.initialize();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void initialize(){
        this._documents = new LinkedBlockingQueue<ParsedDocument>();
        this._listeners = new LinkedList<ExtrinsecListener> ();
        this._remaining = 0;

        try {
            this._tokenizer = new BasicTokenizer();
            this._tokenizer.setMode(BasicTokenizer.MODE.EXTRINSEC);   //!!!
        } catch (IOException ex) {
            Logger.getLogger(ExtrinsecParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void setParams(ExtrinsecParams params) {
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
    public void addListener(ExtrinsecListener listener) {
        this._listeners.add(listener);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public void removeListener(ExtrinsecListener listener) {
        this._listeners.remove(listener);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    synchronized protected void sendParsingComplete(ExtrinsecDocument document) {
        for(ExtrinsecListener listener : this._listeners) {
            listener.extrinsecParsingComplete(document);
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
                this.sendParsingComplete(new ExtrinsecDocument());
            else
                this.sendParsingComplete(this.parseDocument(current));
        }

    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    protected synchronized ExtrinsecDocument parseDocument(ParsedDocument document) {
        if(document == null)
            return null;
        
        ExtrinsecDocument ret=new ExtrinsecDocument();
        //at this moment the parsed doc has its fullText set

        //set the ignore list for tokenizer
        this._tokenizer.setExcludeWords(this._params.getAll());
        this._tokenizer.convert(document);

        //now the ParsedDoc has its sentences set, excluding the word list given
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ExtrinsecParser.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        int chunkLen=this._params.getChunkSize();
        int chunkDistance=this._params.getChunkDistance();
        StringBuilder sb=new StringBuilder();
        //for each sentence create chunks (set sentence no,hash,first word position
        int size;
        for(ParsedSentence sentence : document.sentances()) {
            size=sentence.tokens().size();
            for(int i=0;i<size;i+=chunkDistance){
                    Chunk ch=new Chunk();
                    ch.setSentenceOffset(sentence.getOffset());

                    //sb.delete(0, sb.length()-1);    //hmm
                    sb.setLength(0);
                    for(int j=i;j<chunkLen&&j<size;j++){
                        sb.append(sentence.getToken(j));
                    }
                    ch.setHash(md.digest(sb.toString().getBytes()));
                    ch.setWordNo(i);
                    ret.addChunkHash(ch);
            }
        }

        ret.setName(document.name());
        ret.setDocPath(document.path());
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
