
import com.textparser.Core.Core;
import com.textparser.Core.InvalidDocumentInput;
import com.textparser.Document.ExtrinsecDocument;
import com.textparser.Document.IntrisecDocument;
import com.textparser.Document.InvalidDocument;
import com.textparser.ExtrisecParser.ExtrinsecParams;
import com.textparser.DocumentIO.DocumentReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class TestMain {
    public static void main(String args[]) throws InvalidDocument, IOException {
        long start = System.currentTimeMillis();
        
        List<IntrisecDocument> docs_in;
        List<ExtrinsecDocument> docs_ex;

        Core core = Core.instance();
        ExtrinsecParams params=new ExtrinsecParams(Arrays.asList("etc","can","it"),Arrays.asList(
                "I","did","a","be","me","do","at") , 4,1);
        core.setExtrisecParams(params);
        
        //ExtrinsecDocument ed=new ExtrinsecDocument("/Users/bkt/test/1.xml", new XMLLoader());
        try {
            docs_ex = core.extrinsecParseDocuments(new File("demo"));
            //docs_ex = core.extrinsecParseDocuments(new File("demo"));
        } catch (InvalidDocumentInput ex) {
            Logger.getLogger(TestMain.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        System.out.println(System.currentTimeMillis() - start);
    }

}
