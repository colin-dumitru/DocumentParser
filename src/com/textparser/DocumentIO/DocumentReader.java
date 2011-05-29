/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.textparser.DocumentIO;

import com.textparser.Document.ExtrinsecDocument;
import com.textparser.Document.IntrisecDocument;
import com.textparser.Document.StyilisticInformation;
import com.textparser.Document.StylisticSentence;
import com.textparser.Document.StylisticWord;
import com.textparser.ExtrisecParser.Chunk;
import com.textparser.IntrisecParser.StyilisticCategory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import sun.awt.image.FileImageSource;

/**
 *
 * @author bkt
 */
public class DocumentReader implements Visitor {

    @Override
    public void visit(IntrisecDocument idoc) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(idoc.getBinaryPath()));
            IntrisecDocument ret = (IntrisecDocument) ois.readObject();
            
            idoc.setBinaryPath(ret.getBinaryPath());
            idoc.setCategories(ret.getCategories());
            idoc.setDocPath(ret.getDocPath());
            idoc.setName(ret.getName());
            idoc.setSentences(ret.getSentences());
            idoc.setTotal(ret.getTotal());
            
            ois.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DocumentReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DocumentReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        //        try {
        //            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //            DocumentBuilder db = dbf.newDocumentBuilder();
        //            org.w3c.dom.Document doc = db.parse(new File(idoc.getXmlPath()));
        //
        //            // normalize text representation
        //            doc.getDocumentElement().normalize();
        //
        //            //create the Agenda instance based on the name attr
        //            idoc.setName(doc.getDocumentElement().getAttributeNode("name").toString());
        //            idoc.setPath(doc.getDocumentElement().getAttributeNode("docPath").toString());
        //
        //            NodeList nl = doc.getElementsByTagName("sentences");
        //            for (int i = 0; i < nl.getLength(); i++) {
        //                Element e = (Element) nl.item(i);
        //
        //                StylisticSentence ss = new StylisticSentence();
        //
        //                NodeList infos = e.getElementsByTagName("info");
        //                for (int j = 0; j < infos.getLength(); j++) {
        //                    Element ee = (Element) infos.item(j);
        //                    StyilisticInformation si = new StyilisticInformation();
        //                    si.setId(new Integer(ee.getAttribute("id")));
        //                    Map<Integer, StylisticWord> _values = new HashMap<Integer, StylisticWord>();
        //
        //
        //                    NodeList entries = ee.getElementsByTagName("entry");
        //                    for (int k = 0; k < entries.getLength(); k++) {
        //                        Element eee = (Element) entries.item(k);
        //
        //                        NodeList word = eee.getElementsByTagName("word");  //1
        //
        //                        StylisticWord sw = new StylisticWord(new Integer(((Element) word.item(0)).getAttribute("id")),
        //                                new Float(((Element) word.item(0)).getAttribute("value")));
        //                        _values.put(new Integer(eee.getAttribute("key")), sw);
        //
        //                    }
        //                    si.setAll(_values.values());
        //                    ss.addInformation(si);
        //                }
        //
        //                idoc.addSentence(ss);
        //            }
        //
        //            //get stylistic info
        //            NodeList infos = doc.getElementsByTagName("stylisticInfo");
        //            for (int j = 0; j < infos.getLength(); j++) {
        //                Element ee = (Element) infos.item(j);
        //                StyilisticInformation si = new StyilisticInformation();
        //                si.setId(new Integer(ee.getAttribute("id")));
        //                Map<Integer, StylisticWord> _values = new HashMap<Integer, StylisticWord>();
        //
        //
        //                NodeList entries = ee.getElementsByTagName("entry");
        //                for (int k = 0; k < entries.getLength(); k++) {
        //                    Element eee = (Element) entries.item(k);
        //
        //                    NodeList word = eee.getElementsByTagName("word");  //1
        //
        //                    StylisticWord sw = new StylisticWord(new Integer(((Element) word.item(0)).getAttribute("id")),
        //                            new Float(((Element) word.item(0)).getAttribute("value")));
        //                    _values.put(new Integer(eee.getAttribute("id")), sw);
        //
        //                }
        //                si.setAll(_values.values());
        //                idoc.addTotal(si);
        //            }
        //
        //            //get sylistic categories
        //            NodeList categs = doc.getElementsByTagName("stylisticCategory");
        //            for (int j = 0; j < infos.getLength(); j++) {
        //                Element ee = (Element) infos.item(j);
        //                StyilisticCategory sc = new StyilisticCategory();
        //                sc.setId(new Integer(ee.getAttribute("id")));
        //                Map<Integer, String> _wordList = new HashMap<Integer, String>();
        //
        //                NodeList entries = ee.getElementsByTagName("entry");
        //                for (int k = 0; k < entries.getLength(); k++) {
        //                    Element eee = (Element) entries.item(k);
        //
        //                    _wordList.put(new Integer(eee.getAttribute("id")), eee.getAttribute("word"));
        //
        //                }
        //                sc.setWordList1(_wordList);
        //                idoc.addCategory(sc);
        //            }
        //
        //        } catch (Exception e) {
        //        }
        //        }

    }

    @Override
    public void visit(ExtrinsecDocument edoc) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(edoc.getBinaryPath()));
            
            ExtrinsecDocument ret = (ExtrinsecDocument) ois.readObject();
            
            edoc.setDocPath(ret.getDocPath());
            edoc.setGenerator(ret.getGenerator());
            edoc.setName(ret.getName());
            edoc.setBinaryPath(ret.getBinaryPath());
            edoc.setHashes(ret.getHashes());
            
            ois.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DocumentReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DocumentReader.class.getName()).log(Level.SEVERE, null, ex);
        }

//        try {
//            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//            DocumentBuilder db = dbf.newDocumentBuilder();
//            org.w3c.dom.Document doc = db.parse(new File(edoc.getXmlPath()));
//
//            // normalize text representation
//            doc.getDocumentElement().normalize();
//
//            //create the Agenda instance based on the name attr
//            edoc.setName(doc.getDocumentElement().getAttributeNode("name").toString());
//            edoc.setPath(doc.getDocumentElement().getAttributeNode("docPath").toString());
//
//
//            Element nl = doc.getElementById("chunks");
//            NodeList chunks= nl.getElementsByTagName("chunk");
//
//
//            for (int i = 0; i < chunks.getLength(); i++) {
//                Element e = (Element) chunks.item(i);
//                Chunk c = new Chunk();
//                c.setHash(e.getAttribute("hash").getBytes());   //todo oare merge?
//                c.setSentenceOffset(new Integer(e.getAttribute("sentenceOffset")));
//                c.setWordNo(new Integer(e.getAttribute("wordNo")));
//                edoc.addChunkHash(c);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
