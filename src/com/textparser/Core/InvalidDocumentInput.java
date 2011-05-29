/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.textparser.Core;

/**
 *
 * @author Administrator
 */
public class InvalidDocumentInput extends Exception{

    public InvalidDocumentInput() {
        super("Invalid input documents specified!");
    }
    //----------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------
    public InvalidDocumentInput(String error) {
        super(error);
    }
    
    

}
