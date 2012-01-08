/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.textparser.Translation;

import com.textparser.Document.ParsedDocument;
import java.io.File;

import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import com.textparser.Conversion.ConversionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author catalin.dumitru
 */
public class TranslationHelper extends Thread {

    protected BlockingQueue<ParsedDocument> _documents;
    protected List<TranslationListener> _listeners;
    protected boolean _running;
    protected int _documentsConverting;

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public TranslationHelper() {
        this._documents = new LinkedBlockingQueue<ParsedDocument>();
        this._listeners = new ArrayList<TranslationListener>();

        this._documentsConverting = 0;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public synchronized void addListener(TranslationListener listener) {
        this._listeners.add(listener);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public synchronized void removeListener(TranslationListener listener) {
        this._listeners.remove(listener);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public synchronized void safeStart() {
        this._running = true;
        this.start();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public synchronized void safeStop() {
        this._running = false;

        try {
            this.stop();
        } catch (Exception ex) {
        }
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public synchronized void notifyComplete(ParsedDocument document) {
        this._documentsConverting--;

        for (TranslationListener listener : this._listeners) {
            if (document == null) {
                listener.TranslationComplete(new ParsedDocument());
            } else {
                listener.TranslationComplete(document);
            }
        }

    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    @Override
    public void run() {
        ParsedDocument currentFile = null;

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
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public synchronized void addDocument(ParsedDocument document) {
        try {
            this._documents.add(document);
        } catch (NullPointerException ex) {
            this._documents.add(new ParsedDocument());
        } finally {
            this._documentsConverting++;
        }
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public synchronized int documentConverting() {
        return this._documentsConverting;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    protected synchronized ParsedDocument parse(ParsedDocument document) {
        try {
            if (document == null) {
                return null;
            }

            StringBuilder text = new StringBuilder(document.fullText());
            List<String> chunks = new ArrayList<String>();
            String chunk;
            StringBuilder tmp;

            while (text.length() > 0) {
                chunk = text.substring(0, 4500 < text.length() ? 4500 : text.length());
                tmp = new StringBuilder(chunk);
                text.delete(0, 4500);

                while (text.length() > 0 && text.charAt(0) != '.' && text.charAt(0) != ','
                        && text.charAt(0) != '\n' && text.charAt(0) != ' ' && text.charAt(0) != '\t'
                        && text.charAt(0) != ';' && text.charAt(0) != '(' && text.charAt(0) != ')'
                        && text.charAt(0) != '[' && text.charAt(0) != ']') {
                    tmp.append(text.charAt(0));
                    text.deleteCharAt(0);
                }

                chunks.add(tmp.toString());
            }

            tmp = new StringBuilder();

            for (String bit : chunks) {
                tmp.append(Translate.execute(bit, Language.AUTO_DETECT, Language.ENGLISH));
            }

            document.setFullText(tmp.toString());

        } catch (Exception ex) {
            Logger.getLogger(TranslationHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return document;
    }
}
