/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.Tokenizer;

import com.textparser.Document.ParsedDocument;

/**
 *
 * @author Administrator
 */
public interface DocumentTokenizer{
    public static enum MODE{EXTRINSEC,INTRINSEC};
    public ParsedDocument convert(ParsedDocument what);
    public void setMode(MODE how);
}
