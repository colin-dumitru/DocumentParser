/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.textparser.Conversion;

import com.textparser.Document.InvalidDocument;
import com.textparser.Document.ParsedDocument;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author catalin.dumitru
 */
public class ConversionHelper extends Thread {

    protected BlockingQueue<File> _documents;
    protected List<ConversionListener> _listeners;
    protected Map<String, DocumentConverter> _converters;
    protected boolean _running;
    protected int _documentsConverting;

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public ConversionHelper() {
        this._documents = new LinkedBlockingQueue<File>();
        this._listeners = new ArrayList<ConversionListener>();
        this._converters = new HashMap<String, DocumentConverter>();

        this._documentsConverting = 0;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public synchronized void addConverter(DocumentConverter converter) {
        this._converters.put(converter.extension(), converter);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public synchronized void removeConverter(DocumentConverter converter) {
        this._converters.remove(converter);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public synchronized void addListener(ConversionListener listener) {
        this._listeners.add(listener);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public synchronized void removeListener(ConversionListener listener) {
        this._listeners.remove(listener);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public synchronized void addDocument(File document) {
        this._documents.add(document);
        this._documentsConverting++;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public synchronized int documentConverting() {
        return this._documentsConverting;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public ParsedDocument parse(File document) {
        if (document == null || !document.exists() || !document.isFile()) {
            return null;
        }

        String extension = document.getName().substring(document.getName().lastIndexOf('.') + 1);
        DocumentConverter converter = this._converters.get(extension);

        if (converter == null) {
            ParsedDocument ret = new ParsedDocument();
            ret.setPath(document.getAbsolutePath());
            ret.setName(document.getName());
            new Exception("Nu s-a gasit converter pentru fisierul : " + document.getAbsolutePath()).printStackTrace();
            return ret;
        }

        try {
            return converter.parse(document);
        } catch (InvalidDocument ex) {
            Logger.getLogger(ConversionHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        ParsedDocument ret = new ParsedDocument();
        ret.setPath(document.getAbsolutePath());
        ret.setName(document.getName());
        return ret;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public void safeStart() {
        this._running = true;
        this.start();
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

    public synchronized void notifyComplete(ParsedDocument document) {
        this._documentsConverting--;

        for (ConversionListener listener : this._listeners) {
            listener.conversionComplete(document);
        }

    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    @Override
    public void run() {
        File currentFile = null;

        while (this._running) {

            try {
                currentFile = this._documents.take();
            } catch (Exception ex) {
                /*server stopped*/
            }
            if(currentFile == null)
                this.notifyComplete(new ParsedDocument());
            else
                this.notifyComplete(this.parse(currentFile));
        }
    }
}
