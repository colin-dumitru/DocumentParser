/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.textparser.Core;

import com.google.api.translate.Translate;
import com.textparser.Conversion.ConversionHelper;
import com.textparser.Conversion.ConversionListener;
import com.textparser.Conversion.TxtConverter;
import com.textparser.Document.ExtrinsecDocument;
import com.textparser.Document.IntrisecDocument;
import com.textparser.Document.InvalidDocument;
import com.textparser.Document.ParsedDocument;
import com.textparser.ExtrisecParser.ExtrinsecListener;
import com.textparser.ExtrisecParser.ExtrinsecParams;
import com.textparser.ExtrisecParser.ExtrinsecParser;
import com.textparser.IntrisecParser.IntrisecListener;
import com.textparser.IntrisecParser.IntrisecParams;
import com.textparser.IntrisecParser.IntrisecParser;
import com.textparser.Translation.TranslationHelper;
import com.textparser.Translation.TranslationListener;
import com.textparser.DocumentIO.DocumentWriter;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author catalin.dumitru
 */
public class Core implements IntrisecListener, ExtrinsecListener, ConversionListener, TranslationListener {

    enum STATES {

        S_INTRISEC, S_EXTERN
    }
    protected static final int D_INTRISEC_PARSERS = 4;
    protected static final int D_EXTRINSEC_PARSERS = 4;
    protected static final int D_CONVERTERS = 2;
    protected static final int D_TRANSLATORS = 2;
    private static Core _instance;
    protected List<IntrisecParser> _intrisecParsers;
    protected List<ExtrinsecParser> _extrinsecParsers;
    protected List<ConversionHelper> _converters;
    protected List<TranslationHelper> _translators;
    protected IntrisecParams _intrisecParams;
    protected ExtrinsecParams _extrinsecParams;
    protected int _numIntrisecParsers;
    protected int _numExtrinsecParsers;
    protected int _numConverters;
    protected int _numTranslators;
    protected STATES _state;
    /*the number of files remaining to be parsed*/
    protected int _filesParsing;
    /*the files received from the parses*/
    protected BlockingQueue<IntrisecDocument> _intrisecDocuments;
    protected BlockingQueue<ExtrinsecDocument> _extrinsecDocuments;

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public static Core instance() {
        if (_instance == null) {
            _instance = new Core();
        }
        return Core._instance;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    protected Core() {
        this._intrisecParsers = new LinkedList<IntrisecParser>();
        this._extrinsecParsers = new LinkedList<ExtrinsecParser>();
        this._converters = new LinkedList<ConversionHelper>();
        this._translators = new LinkedList<TranslationHelper>();

        this._numIntrisecParsers = Core.D_INTRISEC_PARSERS;
        this._numExtrinsecParsers = Core.D_EXTRINSEC_PARSERS;
        this._numConverters = Core.D_CONVERTERS;
        this._numTranslators = Core.D_TRANSLATORS;

        this._intrisecDocuments = new LinkedBlockingQueue<IntrisecDocument>();
        this._extrinsecDocuments = new LinkedBlockingQueue<ExtrinsecDocument>();

        Translate.setHttpReferrer("www.google.com");

        this.reset();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public void setIntrisecParams(IntrisecParams params) {
        this._intrisecParams = params;
        //!!!  we need to reset parsers' params too after setting params for core
        for (IntrisecParser parser : this._intrisecParsers) {
            if (parser != null) {
                parser.setParams(params);
            }
        }
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public void setExtrisecParams(ExtrinsecParams params) {
        this._extrinsecParams = params;
        //!!!  we need to reset parsers' params too after setting params for core
        for (ExtrinsecParser parser : this._extrinsecParsers) {
            if (parser != null) {
                parser.setParams(params);
            }
        }
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public void reset() {
        for (TranslationHelper translator : this._translators) {
            if (translator != null) {
                translator.safeStop();
            }
        }

        this._translators.clear();

        for (int i = 0; i < this._numTranslators; i++) {
            TranslationHelper tmp = new TranslationHelper();
            this.initTranslator(tmp);
            this._translators.add(tmp);
        }
        //-----------------------------------------------------------------------------------------
        for (ConversionHelper converter : this._converters) {
            if (converter != null) {
                converter.safeStop();
            }
        }

        this._converters.clear();

        for (int i = 0; i < this._numConverters; i++) {
            ConversionHelper tmp = new ConversionHelper();
            this.initConverter(tmp);
            this._converters.add(tmp);
        }
        //-----------------------------------------------------------------------------------------        
        for (IntrisecParser parser : this._intrisecParsers) {
            if (parser != null) {
                parser.safeStop();
            }
        }

        this._intrisecParsers.clear();

        for (int i = 0; i < this._numIntrisecParsers; i++) {
            IntrisecParser tmp = new IntrisecParser();
            this.initIntrisecParser(tmp);
            this._intrisecParsers.add(tmp);
        }
        //-----------------------------------------------------------------------------------------
        for (ExtrinsecParser parser : this._extrinsecParsers) {
            if (parser != null) {
                parser.safeStop();
            }
        }

        this._extrinsecParsers.clear();

        for (int i = 0; i < this._numExtrinsecParsers; i++) {
            ExtrinsecParser tmp = new ExtrinsecParser();
            this.initExtrinsecParser(tmp);
            this._extrinsecParsers.add(tmp);
        }
        //-----------------------------------------------------------------------------------------
        this._state = STATES.S_INTRISEC;
        this._filesParsing = 0;
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    protected void initIntrisecParser(IntrisecParser parser) {
        parser.setParams(this._intrisecParams);
        parser.addListener(this);
        parser.safeStart();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    protected void initExtrinsecParser(ExtrinsecParser parser) {
        parser.setParams(this._extrinsecParams);
        parser.addListener(this);
        parser.safeStart();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    protected void initConverter(ConversionHelper helper) {
        helper.addConverter(new TxtConverter());
        helper.addListener(this);
        helper.safeStart();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    protected void initTranslator(TranslationHelper helper) {
        helper.addListener(this);
        helper.safeStart();
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    protected void convertDocument(File file) {
        if (this._converters.size() <= 0) {
            return;
        }

        /*we find the converter with the least amount of document converting*/
        ConversionHelper minConverter = this._converters.get(0);
        int minDocs = minConverter.documentConverting();

        for (int i = 1; i < this._converters.size(); i++) {
            ConversionHelper tmp = this._converters.get(i);

            if (tmp.documentConverting() < minDocs) {
                minDocs = tmp.documentConverting();
                minConverter = tmp;
            }
        }

        minConverter.addDocument(file);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    protected void translateDocument(ParsedDocument doc) {
        if (this._translators.size() <= 0) {
            return;
        }

        /*we find the translator with the least amount of document translating*/
        TranslationHelper minTranslator = this._translators.get(0);
        int minDocs = minTranslator.documentConverting();

        for (int i = 1; i < this._translators.size(); i++) {
            TranslationHelper tmp = this._translators.get(i);

            if (tmp.documentConverting() < minDocs) {
                minDocs = tmp.documentConverting();
                minTranslator = tmp;
            }
        }

        /*then we send the document to the translator*/
        minTranslator.addDocument(doc);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    protected void parseIntrisecDocument(ParsedDocument doc) throws InvalidDocument {
        if (this._intrisecParsers.size() <= 0) {
            return;
        }

        /*we find the parser with the least amount of document parsing*/
        IntrisecParser minParser = this._intrisecParsers.get(0);
        int minDocs = minParser.documentRemaining();

        for (int i = 1; i < this._intrisecParsers.size(); i++) {
            IntrisecParser tmp = this._intrisecParsers.get(i);

            if (tmp.documentRemaining() < minDocs) {
                minDocs = tmp.documentRemaining();
                minParser = tmp;
            }
        }
        minParser.addDocument(doc);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    protected void parseExtrinsecDocument(ParsedDocument doc) throws InvalidDocument {
        if (this._extrinsecParsers.size() <= 0) {
            return;
        }

        /*we find the parser with the least amount of document parsing*/
        ExtrinsecParser minParser = this._extrinsecParsers.get(0);
        int minDocs = minParser.documentRemaining();

        for (int i = 1; i < this._extrinsecParsers.size(); i++) {
            ExtrinsecParser tmp = this._extrinsecParsers.get(i);

            if (tmp.documentRemaining() < minDocs) {
                minDocs = tmp.documentRemaining();
                minParser = tmp;
            }
        }
        minParser.addDocument(doc);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public List<IntrisecDocument> intrisecParseDocuments(List<File> files) throws InvalidDocumentInput {
        this._state = STATES.S_INTRISEC;

        this._intrisecDocuments.clear();
        this._filesParsing = 0;

        List<IntrisecDocument> ret = new LinkedList<IntrisecDocument>();

        /*first stem - translation*/
        for (File file : files) {
            if (file.isFile()) {
                this._filesParsing++;
                this.convertDocument(file);
            }

        }

        try {
            while (this._filesParsing > 0 || this._intrisecDocuments.size() > 0) {
                IntrisecDocument idoc = this._intrisecDocuments.take();
                idoc.setGenerator(new DocumentWriter());

                try {
                    idoc.setBinaryPath(idoc.path().substring(0, idoc.path().lastIndexOf(".")) + ".bin");
                    idoc.write();
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }

                ret.add(idoc);
            }
        } catch (InterruptedException ex) {
            System.out.println(ex.toString());
        }

        return ret;

        /*now we wait for the rest of documents to complete*/
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public List<IntrisecDocument> intrisecParseDocuments(File _directory) throws InvalidDocumentInput {
        if (!_directory.isDirectory()) {
            throw new InvalidDocumentInput(_directory.getName() + " is not a directory!");
        }

        return this.intrisecParseDocuments(Arrays.asList(_directory.listFiles()));

    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public List<ExtrinsecDocument> extrinsecParseDocuments(List<File> files) throws InvalidDocumentInput {
        this._state = STATES.S_EXTERN;

        this._extrinsecDocuments.clear();
        this._filesParsing = 0;

        List<ExtrinsecDocument> ret = new LinkedList<ExtrinsecDocument>();

        /*first stem - translation*/
        for (File file : files) {
            if (file.isFile()) {
                this.convertDocument(file);
                this._filesParsing++;
            }
        }

        try {
            while (this._filesParsing > 0 || this._extrinsecDocuments.size() > 0) {
                ExtrinsecDocument edoc = this._extrinsecDocuments.take();

                try {
                    edoc.setGenerator(new DocumentWriter());
                    edoc.setBinaryPath(edoc.getDocPath().substring(0, edoc.getDocPath().lastIndexOf(".")) + ".bin");
                    edoc.write();
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
                ret.add(edoc);
            }
        } catch (InterruptedException ex) {
            System.out.println(ex.toString());
        }

        return ret;

        /*now we wait for the rest of documents to complete*/
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public List<ExtrinsecDocument> extrinsecParseDocuments(File _directory) throws InvalidDocumentInput {
        if (!_directory.isDirectory()) {
            throw new InvalidDocumentInput(_directory.getName() + " is not a directory!");
        }

        return this.extrinsecParseDocuments(Arrays.asList(_directory.listFiles()));

    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    @Override
    public void intrisecParsingComplete(IntrisecDocument document) {
        this._filesParsing--;

        if (document != null) {
            this._intrisecDocuments.add(document);
        } else {
            this._intrisecDocuments.add(new IntrisecDocument());
        }
    }
    //----------------------------------------------------------------------------------------------

    @Override
    public void extrinsecParsingComplete(ExtrinsecDocument document) {
        this._filesParsing--;
        this._extrinsecDocuments.add(document);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    @Override
    public void conversionComplete(ParsedDocument document) {
        this.translateDocument(document);
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    @Override
    public void TranslationComplete(ParsedDocument document) {
        if (document == null) {
            document = new ParsedDocument();
        }

        switch (this._state) {
            case S_INTRISEC:
                try {
                    this.parseIntrisecDocument(document);
                } catch (InvalidDocument ex) {

                    this._filesParsing--;
                    Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case S_EXTERN:
                try {
                    this.parseExtrinsecDocument(document);
                } catch (InvalidDocument ex) {

                    this._filesParsing--;
                    Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }

    public List<ConversionHelper> getConverters() {
        return _converters;
    }

    public List<ExtrinsecParser> getExtrinsecParsers() {
        return _extrinsecParsers;
    }

    public List<IntrisecParser> getIntrisecParsers() {
        return _intrisecParsers;
    }

    public List<TranslationHelper> getTranslators() {
        return _translators;
    }

    public int getNumConverters() {
        return _numConverters;
    }

    public int getNumExtrinsecParsers() {
        return _numExtrinsecParsers;
    }

    public int getNumIntrisecParsers() {
        return _numIntrisecParsers;
    }

    public int getNumTranslators() {
        return _numTranslators;
    }
}
