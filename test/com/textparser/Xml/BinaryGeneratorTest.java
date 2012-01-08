/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.textparser.Xml;

import com.textparser.DocumentIO.DocumentWriter;
import java.io.File;
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
public class BinaryGeneratorTest {
    
    private Helper _helper;
    
    public BinaryGeneratorTest() {
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
     * Test of visit method, of class XMLGenerator.
     */
    @Test
    public void testVisit_IntrisecDocument() {
        System.out.println("testVisit_IntrisecDocument");
        
        this._helper=new Helper("demo/1.obj", "demo/1.txt", "1.txt", null, 3, 3, 3, 3, 3);
        //create mockup IntrinsecDoc , check if exists on disc after generating
        IntrisecDocument doc=this._helper.createMockupIntrisecDocument();
        
        DocumentWriter xgen=new DocumentWriter();
        xgen.visit(doc);
        
        File f=new File(_helper.getPath());
        assertTrue(f.exists());
        
    }

    /**
     * Test of visit method, of class XMLGenerator.
     */
    @Test
    public void testVisit_ExtrinsecDocument() {
        System.out.println("testVisit_ExtrinsecDocument");
        this._helper=new Helper("demo/1.obj", "demo/1.txt", "1.txt", 3);
        //create mockup extrinsecDoc , check if exists on disc after generating
        ExtrinsecDocument doc=this._helper.createMockupExtrinsecDocument();
        
        DocumentWriter xgen=new DocumentWriter();
        xgen.visit(doc);
        
        File f=new File(_helper.getPath());
        assertTrue(f.exists());
    }
}
