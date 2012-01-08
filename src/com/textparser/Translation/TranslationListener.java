/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.Translation;

import com.textparser.Conversion.*;
import com.textparser.Document.ParsedDocument;

/**
 *
 * @author Administrator
 */
public interface TranslationListener {
    public void TranslationComplete(ParsedDocument document);
}
