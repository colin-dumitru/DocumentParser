/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.Conversion;

import com.textparser.Document.InvalidDocument;
import com.textparser.Document.ParsedDocument;
import java.io.File;

/**
 *
 * @author Administrator
 */
public interface DocumentConverter{
    public String extension();
    public ParsedDocument parse(File file) throws InvalidDocument;
}
