/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.Conversion;

import com.textparser.Document.InvalidDocument;
import com.textparser.Document.ParsedDocument;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class TxtConverter implements DocumentConverter{
    protected final int BUFFER_SIZE = 1024;
    
    
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public String extension() {
        return "txt";
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public ParsedDocument parse(File file) throws InvalidDocument{
        FileReader  in = null;
        ParsedDocument ret = new ParsedDocument();

        StringBuilder builder = new StringBuilder();
        char buffer[] = new char[BUFFER_SIZE];
       
        try {            
            in = new FileReader(file) ;
            
            /*the condition is purely to make it ocmpil - it will stop when eof exception
             is thrown*/
            while(in.read(buffer) >= 0) {
                builder.append(buffer);
            }            
            
        } catch (FileNotFoundException ex) {
            throw new InvalidDocument("File not found : " + file.getAbsolutePath());
        }catch (IOException ex) {
            /*eof*/
        }finally {
            try {
                in.close();
            } catch (IOException ex) {
                throw new InvalidDocument("IO exception : " + file.getAbsolutePath());
            }
        }
        
        ret.setFullText(builder.toString());       
        
        ret.setName(file.getName());
        ret.setPath(file.getAbsolutePath());
        
        return ret;
    }

}
