/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.textparser.DocumentIO;

import com.textparser.Document.ExtrinsecDocument;
import com.textparser.Document.IntrisecDocument;
import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bkt
 */
public class DocumentWriter implements Visitor {

    @Override
    public void visit(IntrisecDocument doc) {
        try {
          // Create XML encoder.
          ObjectOutputStream xenc = new ObjectOutputStream(new FileOutputStream(doc.getBinaryPath()));

          // Write object.
          xenc.writeObject(doc);
            
        } catch (Exception ex) {
            Logger.getLogger(DocumentWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    //        try {
    //            File f = new File(doc.getXmlPath());
    //            if (!f.exists()) {
    //                f.createNewFile();
    //            }
    //
    //            FileOutputStream fos = new FileOutputStream(doc.getXmlPath());
    //            OutputFormat of = new OutputFormat("XML", "ISO-8859-1", true);
    //            of.setIndent(1);
    //            of.setIndenting(true);
    //            XMLSerializer serializer = new XMLSerializer(fos, of);
    //            // SAX2.0 ContentHandler.
    //            ContentHandler hd = serializer.asContentHandler();
    //            hd.startDocument();
    //
    //            AttributesImpl atts = new AttributesImpl();
    //
    //            //get the existing Agenda instance name
    //            atts.addAttribute("", "", "name", "CDATA", doc.name());
    //            atts.addAttribute("", "", "docPath", "CDATA", doc.path());
    //            atts.addAttribute("", "", "xmlPath", "CDATA", doc.getXmlPath());
    //            hd.startElement("", "", "intrinsecDocument", atts);
    //
    //            //add sentences
    //            hd.startElement("", "", "sentences", null);
    //            for (StylisticSentence sentence : doc.sentences()) {
    //                hd.startElement("", "", "sentence", null);
    //                hd.startElement("", "", "infos", null);
    //                for (StyilisticInformation info : sentence.information()) {
    //                    atts.clear();
    //                    atts.addAttribute("", "", "id", "CDATA", info.getId().toString());
    //
    //                    hd.startElement("", "", "info", atts);
    //                    for (Map.Entry<Integer, StylisticWord> entry : info.getAll().entrySet()) {
    //                        atts.clear();
    //                        atts.addAttribute("", "", "key", "CDATA", entry.getKey().toString());
    //                        
    //                        hd.startElement("", "", "entry", atts);
    //                        
    //                        atts.clear();
    //                        atts.addAttribute("", "", "id", "CDATA", entry.getValue().id().toString());
    //                        atts.addAttribute("", "", "value", "CDATA", entry.getValue().value().toString());
    //                        hd.startElement("", "", "word", atts);
    //                        hd.endElement("", "", "word");
    //                        
    //                        hd.endElement("", "", "entry");
    //                    }
    //                    hd.endElement("", "", "info");
    //                }
    //                hd.endElement("", "", "infos");
    //                hd.endElement("", "", "sentence");
    //            }
    //            hd.endElement("", "", "sentences");
    //
    //            //list of stylistic info
    //            hd.startElement("", "", "stylisticInfos", null);
    //            for (StyilisticInformation info : doc.total()) {
    //                atts.clear();
    //                atts.addAttribute("", "", "id", "CDATA", info.getId().toString());
    //
    //                hd.startElement("", "", "stylisticInfo", atts);
    //                for (Map.Entry<Integer, StylisticWord> entry : info.getAll().entrySet()) {
    //                    atts.clear();
    //                    atts.addAttribute("", "", "id", "CDATA", entry.getKey().toString());
    //                    hd.startElement("", "", "entry", atts);
    //                    atts.clear();
    //                    atts.addAttribute("", "", "id", "CDATA", entry.getValue().id().toString());
    //                    atts.addAttribute("", "", "value", "CDATA", entry.getValue().value().toString());
    //                    hd.startElement("", "", "word", atts);
    //                    hd.endElement("", "", "word");
    //                    hd.endElement("", "", "entry");
    //                }
    //                hd.endElement("", "", "stylisticInfo");
    //            }
    //            hd.endElement("", "", "stylisticInfos");
    //
    //            //list of stylistic category 
    //            hd.startElement("", "", "stylisticCategories", atts);
    //            for (StyilisticCategory cat : doc.categories()) {
    //                atts.clear();
    //                atts.addAttribute("", "", "id", "CDATA", cat.id().toString());
    //                hd.startElement("", "", "stylisticCategory", atts);
    //                
    //                for (Map.Entry<Integer, String> entry : cat.wordList().entrySet()) {
    //                    atts.clear();
    //                    atts.addAttribute("", "", "id", "CDATA", entry.getKey().toString());
    //                    atts.addAttribute("", "", "word", "CDATA", entry.getValue());
    //                    hd.startElement("", "", "entry", atts);
    //                    hd.endElement("", "", "entry");
    //                }
    //                hd.endElement("", "", "stylisticCategory");
    //            }
    //            hd.endElement("", "", "stylisticCategories");
    //
    //            hd.endElement("", "", "intrinsecDocument");
    //            hd.endDocument();
    //            fos.close();
    //        } catch (SAXException ex) {
    //            Logger.getLogger(XMLGenerator.class.getName()).log(Level.SEVERE, null, ex);
    //        } catch (IOException ex) {
    //        }
            
        
    }


    @Override
    public void visit(ExtrinsecDocument doc) {
        try {
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(doc.getBinaryPath()));
            oos.writeObject(doc);
            oos.flush();
            oos.close();
            
        } catch (Exception ex) {
            Logger.getLogger(DocumentWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        try {
//            File f = new File(doc.getXmlPath());
//            if (!f.exists()) {
//                f.createNewFile();
//            }
//
//            FileOutputStream fos = new FileOutputStream(doc.getXmlPath());
//            OutputFormat of = new OutputFormat("XML", "ISO-8859-1", true);
//            of.setIndent(1);
//            of.setIndenting(true);
//            XMLSerializer serializer = new XMLSerializer(fos, of);
//            // SAX2.0 ContentHandler.
//            ContentHandler hd = serializer.asContentHandler();
//            hd.startDocument();
//
//            AttributesImpl atts = new AttributesImpl();
//
//            //get the existing Agenda instance name
//            atts.addAttribute("", "", "name", "CDATA", doc.getName());
//            atts.addAttribute("", "", "docPath", "CDATA", doc.getPath());
//            atts.addAttribute("", "", "xmlPath", "CDATA", doc.getXmlPath());
//            hd.startElement("", "", "extrinsecDocument", atts);
//
//            //add sentences
//            hd.startElement("", "", "chunks", null); 
//            for (Chunk chk : doc.getHashes().values()) {
//                atts.clear();
//                atts.addAttribute("", "", "sentenceOffset", "CDATA", new Integer(chk.getSentenceOffset()).toString());
//                atts.addAttribute("", "", "hash", "CDATA", chk.getHash().toString());
//                atts.addAttribute("", "", "wordNo", "CDATA", new Integer(chk.getWordNo()).toString());
//
//                hd.startElement("", "", "chunk", atts);
//                hd.endElement("", "", "chunk");
//            }
//            hd.endElement("", "", "chunks");
//            
//            hd.endElement("", "", "extrinsecDocument");
//            hd.endDocument();
//            fos.close();
//        } catch (SAXException ex) {
//            Logger.getLogger(XMLGenerator.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(XMLGenerator.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

   
}
