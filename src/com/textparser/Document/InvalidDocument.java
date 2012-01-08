/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.Document;

/**
 *
 * @author catalin.dumitru
 */
public class InvalidDocument extends Exception{

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public InvalidDocument() {
        super("Invalid document found");
    }

    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    public InvalidDocument(String exception) {
        super(exception);
    }
}
