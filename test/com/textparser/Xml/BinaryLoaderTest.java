/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.textparser.Xml;

import com.textparser.DocumentIO.DocumentWriter;
import com.textparser.DocumentIO.DocumentReader;
import com.textparse.Helper.Helper;
import com.textparser.Document.ExtrinsecDocument;
import com.textparser.Document.IntrisecDocument;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author bkt
 */
public class BinaryLoaderTest {

    Helper _helper;

    public BinaryLoaderTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of visit method, of class XMLLoader.
     */
    @Test
    public void testVisit_IntrisecDocument() {
        System.out.println("");
        this._helper = new Helper("demo/1.obj", "demo/1.txt", "1.txt", null, 3, 3, 3, 3, 3);
        //create mockup IntrinsecDoc , check if exists on disc after generating
        IntrisecDocument expectedDoc = this._helper.createMockupIntrisecDocument();

        DocumentWriter xgen = new DocumentWriter();
        xgen.visit(expectedDoc);

        DocumentReader xload = new DocumentReader();
        IntrisecDocument resultDoc = new IntrisecDocument(this._helper.getOutputPath(), xload);

        assertEquals(expectedDoc, resultDoc);
    }

    /**
     * Test of visit method, of class XMLLoader.
     */
    @Test
    public void testVisit_ExtrinsecDocument() {
        System.out.println("visit");
        this._helper = new Helper("demo/1.obj", "demo/1.txt", "1.txt", 3);
        //create mockup extrinsecDoc , check if obj equals to the one that we load
        ExtrinsecDocument expectedDoc = this._helper.createMockupExtrinsecDocument();

        DocumentWriter xgen = new DocumentWriter();
        xgen.visit(expectedDoc);

        DocumentReader xload = new DocumentReader();
        ExtrinsecDocument resultDoc = new ExtrinsecDocument(this._helper.getOutputPath(), xload);

        assertEquals(expectedDoc, resultDoc);
    }
}
