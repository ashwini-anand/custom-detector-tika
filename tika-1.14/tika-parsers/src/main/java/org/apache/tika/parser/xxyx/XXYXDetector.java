package org.apache.tika.parser.xxyx;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.namespace.QName;

import org.apache.tika.detect.Detector;
import org.apache.tika.detect.XmlRootExtractor;
import org.apache.tika.io.LookaheadInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.commons.io.IOUtils;


public class XXYXDetector implements Detector {
    private static final long serialVersionUID = -1709652690773421147L;

    public MediaType detect(InputStream stream, Metadata metadata)
            throws IOException {
        
        MediaType type = MediaType.OCTET_STREAM;
	InputStream truncStream = new LookaheadInputStream(stream, 1024);
        String fileData = IOUtils.toString(truncStream, "UTF-8");
	String findStr = "xxyxxxyx";

        try{
           int count =0;
	   int lastIndex = 0;
    	   while(lastIndex != -1){
		lastIndex = fileData.indexOf(findStr,lastIndex);
		if(lastIndex != -1){
        		count ++;
        		lastIndex += findStr.length();
    		}

	  }
          if(count >= 2) {
                type = MediaType.text("xxyxtype");
          } 
        } catch(Exception e) {
		System.out.println("Hiiiiiiiiiiiiiiiii , There was error in  XXYXDetector—————**************************************************************************—————————————————");
            
        }
        return type;
    }
}


