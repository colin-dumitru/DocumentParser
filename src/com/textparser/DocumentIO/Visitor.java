/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.textparser.DocumentIO;

import com.textparser.Document.ExtrinsecDocument;
import com.textparser.Document.IntrisecDocument;
import com.textparser.Document.StyilisticInformation;
import com.textparser.Document.StylisticSentence;
import com.textparser.IntrisecParser.StyilisticCategory;

/**
 *
 * @author bkt
 */
public interface Visitor {
    public void visit(IntrisecDocument doc);
    public void visit(ExtrinsecDocument doc);
}
