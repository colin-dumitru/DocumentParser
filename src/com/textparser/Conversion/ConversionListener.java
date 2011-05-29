/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.Conversion;

import com.textparser.Document.ParsedDocument;

/**
 *
 * @author Administrator
 */
public interface ConversionListener {
    public void conversionComplete(ParsedDocument document);
}
