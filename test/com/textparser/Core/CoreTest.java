/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.textparser.Core;

import java.util.LinkedList;
import java.util.Arrays;
import com.textparser.Conversion.ConversionHelper;
import com.textparser.ExtrisecParser.ExtrinsecParams;
import com.textparser.ExtrisecParser.ExtrinsecParser;
import com.textparser.IntrisecParser.IntrisecParser;
import com.textparser.Translation.TranslationHelper;
import java.io.File;
import java.util.List;
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
public class CoreTest {
    
    public CoreTest() {
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
     * Test of instance method, of class Core.
     */
    @Test
    public void testInstance() {
        System.out.println("instance");
        Core expResult = Core.instance();
        Core result = Core.instance();
        assertEquals(expResult, result);
    }

    
    @Test
    public void testReset() {
        System.out.println("reset");
        Core instance = Core.instance();
        instance.reset();
        
        List<IntrisecParser> _intrisecParsers = instance.getIntrisecParsers();
        List<ExtrinsecParser> _extrinsecParsers = instance.getExtrinsecParsers();
        List<ConversionHelper> _converters = instance.getConverters();
        List<TranslationHelper> _translators =  instance.getTranslators();
        
        int noConverters=instance.getConverters().size();
        int noExtP=instance.getExtrinsecParsers().size();
        int noIntP=instance.getIntrisecParsers().size();
        int noTr=instance.getTranslators().size();
        
        //check if the number of threads is correct
        assertEquals(instance.getNumConverters(),noConverters );
        assertEquals(instance.getNumExtrinsecParsers(),noExtP );
        assertEquals(instance.getNumIntrisecParsers(),noIntP );
        assertEquals(instance.getNumTranslators(), noTr);
        
        //check if threads are alive
        
        for(int i=0;i<instance.getNumConverters();i++){
            assertTrue(_converters.get(i).isAlive());
        }
        for(int i=0;i<instance.getNumExtrinsecParsers();i++){
            assertTrue(_extrinsecParsers.get(i).isAlive());
        }
        for(int i=0;i<instance.getNumIntrisecParsers();i++){
            assertTrue(_intrisecParsers.get(i).isAlive());
        }
//        for(int i=0;i<instance.getNumTranslators();i++){
//            assertTrue(_translators.get(i).isAlive());
//        }
    }
    

//    /**
//     * Test of convertDocument method, of class Core.
//     */
//    @Test
//    public void testConvertDocument() {
//        System.out.println("convertDocument");
//        File file = null;
//        Core instance = new Core();
//        instance.convertDocument(file);
//       
//    }
//
//    /**
//     * Test of translateDocument method, of class Core.
//     */
//    @Test
//    public void testTranslateDocument() {
//        System.out.println("translateDocument");
//        ParsedDocument doc = null;
//        Core instance = new Core();
//        instance.translateDocument(doc);
//    }
//
//    /**
//     * Test of parseIntrisecDocument method, of class Core.
//     */
//    @Test
//    public void testParseIntrisecDocument() throws Exception {
//        System.out.println("parseIntrisecDocument");
//        ParsedDocument doc = null;
////        Core instance = new Core();
////        instance.parseIntrisecDocument(doc);
//    }
//
//    /**
//     * Test of parseExtrinsecDocument method, of class Core.
//     */
//    @Test
//    public void testParseExtrinsecDocument() throws Exception {
//        System.out.println("parseExtrinsecDocument");
//        ParsedDocument doc = null;
////        Core instance = Core.instance();
////        instance.parseExtrinsecDocument(doc);
//    }
//    
//    

    /**
     * Test of intrisecParseDocuments method, of class Core.
     */
    @Test
    public void testIntrisecParseDocuments_List() throws Exception {
        System.out.println("intrisecParseDocuments");
        List<File> files = null;
//        Core instance = Core.instance();
//        List expResult = null;
//        List result = instance.intrisecParseDocuments(files);
//        assertEquals(expResult, result);
        
    }

    /**
     * Test of intrisecParseDocuments method, of class Core.
     */
    @Test
    public void testIntrisecParseDocuments_File() throws Exception {
        System.out.println("intrisecParseDocuments");
        File _directory = null;
        Core instance = Core.instance();
        instance.reset();
        List result = null;
        try {
            result = instance.intrisecParseDocuments(new File("demo"));
        } catch (InvalidDocumentInput ex) {
            fail(ex.toString());
        }
         
        //file should be serialized on disc,result should contain 
        assertNotNull(result);
    }

    /**
     * Test of extrinsecParseDocuments method, of class Core.
     */
    @Test
    public void testExtrinsecParseDocuments_List() throws Exception {
        System.out.println("extrinsecParseDocuments");
        List<File> files = null;
//        Core instance = new Core();
//        List expResult = null;
//        List result = instance.extrinsecParseDocuments(files);
//        assertEquals(expResult, result);
        
    }

    /**
     * Test of extrinsecParseDocuments method, of class Core.
     */
    @Test
    public void testExtrinsecParseDocuments_File() throws Exception {
        System.out.println("extrinsecParseDocuments");
        Core core = Core.instance();
        core.reset();
        ExtrinsecParams params=new ExtrinsecParams(Arrays.asList("etc","can","it"),Arrays.asList(
                "I","did","a","be","me","do","at") , 4,1);
        core.setExtrisecParams(params);
        List result = null;
        try {
            //docs_ex = core.extrinsecParseDocuments(new File("E:\\test"));
            result = core.extrinsecParseDocuments(new File("demo"));
        } catch (InvalidDocumentInput ex) {
            fail("InvalidDocumentInput");
        }
               
        assertNotNull(result);
    }

//    /**
//     * Test of intrisecParsingComplete method, of class Core.
//     */
//    @Test
//    public void testIntrisecParsingComplete() {
//        System.out.println("intrisecParsingComplete");
//        IntrisecDocument document = null;
//        Core instance = new Core();
//        instance.intrisecParsingComplete(document);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of extrinsecParsingComplete method, of class Core.
//     */
//    @Test
//    public void testExtrinsecParsingComplete() {
//        System.out.println("extrinsecParsingComplete");
//        ExtrinsecDocument document = null;
//        Core instance = new Core();
//        instance.extrinsecParsingComplete(document);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of conversionComplete method, of class Core.
//     */
//    @Test
//    public void testConversionComplete() {
//        System.out.println("conversionComplete");
//        ParsedDocument document = null;
//        Core instance = new Core();
//        instance.conversionComplete(document);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of TranslationComplete method, of class Core.
//     */
//    @Test
//    public void testTranslationComplete() {
//        System.out.println("TranslationComplete");
//        ParsedDocument document = null;
//        Core instance = new Core();
//        instance.TranslationComplete(document);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
