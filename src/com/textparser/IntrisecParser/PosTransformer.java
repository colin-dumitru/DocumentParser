/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.IntrisecParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.util.InvalidFormatException;

/**
 *
 * @author Administrator
 */
public class PosTransformer implements SentenceTransformer{
    protected final static String EN_MODEL = "lib/en-pos-maxent.bin";
    
    protected opennlp.tools.postag.POSTaggerME _tagger;
    protected static POSModel _model;

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public PosTransformer() {
        
        try {
            /*single initialisation*/
            if(_model == null){
                _model = new POSModel(new FileInputStream(PosTransformer.EN_MODEL));
            }
            
            this._tagger = new POSTaggerME(_model);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PosTransformer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(PosTransformer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PosTransformer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    @Override
    public List<String> transform(List<String> words) {
        if(_tagger != null) {        
            return _tagger.tag(words);
        }
        
        return null;
    }

}
