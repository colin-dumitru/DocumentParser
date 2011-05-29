/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.textparse.Helper;

import com.textparser.Document.ExtrinsecDocument;
import com.textparser.Document.IntrisecDocument;
import com.textparser.Document.StyilisticInformation;
import com.textparser.Document.StylisticSentence;
import com.textparser.Document.StylisticWord;
import com.textparser.ExtrisecParser.Chunk;
import com.textparser.ExtrisecParser.ExtrinsecParser;
import com.textparser.IntrisecParser.StyilisticCategory;
import com.textparser.DocumentIO.DocumentWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bkt
 */
public class Helper {

    protected String _xmlPath;
    protected String _path;
    protected String _name;
    protected String[] _words;
    protected int _noStylisticWords;
    protected int _noStylisticCategories;
    protected int _noStylisticInformation;
    protected int _noStylisticInformationValues;
    protected int _noStylisticSentences;
    //maybe mockup words and stylisticinfo values todo
    protected int _noChunks;

    public Helper(String _xmlPath, String _path, String _name, String[] _words, int _noStylisticWords, int _noStylisticCategories, int _noStylisticInformation, int _noStylisticInformationValues, int _noStylisticSentences) {
        this._xmlPath = _xmlPath;
        this._path = _path;
        this._name = _name;
        this._words = _words;
        this._noStylisticWords = _noStylisticWords;
        this._noStylisticCategories = _noStylisticCategories;
        this._noStylisticInformation = _noStylisticInformation;
        this._noStylisticInformationValues = _noStylisticInformationValues;
        this._noStylisticSentences = _noStylisticSentences;
    }

    public Helper(String _xmlPath, String _path, String _name, int _noChunks) {
        this._xmlPath = _xmlPath;
        this._path = _path;
        this._name = _name;
        this._noChunks = _noChunks;
    }

    public IntrisecDocument createMockupIntrisecDocument() {
        IntrisecDocument doc = new IntrisecDocument();
        DocumentWriter instance = new DocumentWriter();
        doc.setGenerator(instance);
        doc.setName(this._name);
        doc.setPath(this._path);
        doc.setBinaryPath(this._xmlPath);

        int i, j, k;
        for (i = 0; i < this._noStylisticCategories; i++) {
            StyilisticCategory sc = new StyilisticCategory();
            sc.setId(i);

            Map<Integer, String> m = new HashMap<Integer, String>();
            for (j = 0; j < this._noStylisticWords; j++) {
                m.put(j, "ceva" + j);
            }
            sc.setWordList(m);
            doc.addCategory(sc);
        }

        for (j = 0; j < this._noStylisticSentences; j++) {
            StylisticSentence ss = new StylisticSentence();
            for (i = 0; i < this._noStylisticInformation; i++) {
                StyilisticInformation si = new StyilisticInformation();
                si.setId(i);
                for (k = 0; k < this._noStylisticInformationValues; k++) {
                    si.addValue(k, new Float(k));
                }
                doc.addTotal(si);
                ss.addInformation(si);
            }
            doc.addSentence(ss);
        }


        return doc;
    }

    public Chunk createMockupChunk(int _sentenceOffset, byte[] _hash, int _wordNo) {
        Chunk c = new Chunk();
        c.setHash(_hash);
        c.setSentenceOffset(_sentenceOffset);
        c.setWordNo(_wordNo);
        return c;
    }

    public byte[] generateHash() {
        String uuid = UUID.randomUUID().toString();
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ExtrinsecParser.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return md.digest(uuid.getBytes());
    }

    public ExtrinsecDocument createMockupExtrinsecDocument() {
        ExtrinsecDocument doc = new ExtrinsecDocument();
        DocumentWriter instance = new DocumentWriter();
        doc.setGenerator(instance);
        doc.setName(this._name);
        doc.setDocPath(this._path);
        doc.setBinaryPath(this._xmlPath);

        int i;
        for(i=0;i<this._noChunks;i++){
            doc.addChunkHash(this.createMockupChunk(i, this.generateHash(), i));
        }
        
        return doc;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public int getNoStylisticCategories() {
        return _noStylisticCategories;
    }

    public void setNoStylisticCategories(int _noStylisticCategories) {
        this._noStylisticCategories = _noStylisticCategories;
    }

    public int getNoStylisticInformation() {
        return _noStylisticInformation;
    }

    public void setNoStylisticInformation(int _noStylisticInformation) {
        this._noStylisticInformation = _noStylisticInformation;
    }

    public int getNoStylisticInformationValues() {
        return _noStylisticInformationValues;
    }

    public void setNoStylisticInformationValues(int _noStylisticInformationValues) {
        this._noStylisticInformationValues = _noStylisticInformationValues;
    }

    public int getNoStylisticSentences() {
        return _noStylisticSentences;
    }

    public void setNoStylisticSentences(int _noStylisticSentences) {
        this._noStylisticSentences = _noStylisticSentences;
    }

    public int getNoStylisticWords() {
        return _noStylisticWords;
    }

    public void setNoStylisticWords(int _noStylisticWords) {
        this._noStylisticWords = _noStylisticWords;
    }

    public String getPath() {
        return _path;
    }

    public void setPath(String _path) {
        this._path = _path;
    }

    public String[] getWords() {
        return _words;
    }

    public void setWords(String[] _words) {
        this._words = _words;
    }

    public String getOutputPath() {
        return _xmlPath;
    }

    public void setXmlPath(String _xmlPath) {
        this._xmlPath = _xmlPath;
    }
}
