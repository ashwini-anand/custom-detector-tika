package org.apache.tika.parser.xxyx;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.input.CloseShieldInputStream;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AbstractParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.EmbeddedContentHandler;
import org.apache.tika.sax.OfflineContentHandler;
import org.apache.tika.sax.TaggedContentHandler;
import org.apache.tika.sax.TextContentHandler;
import org.apache.tika.sax.XHTMLContentHandler;
import org.apache.tika.detect.AutoDetectReader;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class XXYXParser extends AbstractParser {

        private static final Set<MediaType> SUPPORTED_TYPES = Collections.singleton(MediaType.text("xxyxtype"));
        public static final String HELLO_MIME_TYPE = "text/xxyxtype";
        
        public Set<MediaType> getSupportedTypes(ParseContext context) {
                return SUPPORTED_TYPES;
        }

        public void parse(
                        InputStream stream, ContentHandler handler,
                        Metadata metadata, ParseContext context)
                        throws IOException, SAXException, TikaException {

                metadata.set(Metadata.CONTENT_TYPE, HELLO_MIME_TYPE);
                metadata.set("Hi", "This is xxyxtype file. It contains keyword xxyxxxyx");

              	 try (AutoDetectReader reader = new AutoDetectReader(
                new CloseShieldInputStream(stream), metadata)) {
                  XHTMLContentHandler xhtml =
                    new XHTMLContentHandler(handler, metadata);
            	   xhtml.startDocument();

            	   xhtml.startElement("p");
            	   char[] buffer = new char[4096];
            	   int n = reader.read(buffer);
            	   while (n != -1) {
                   xhtml.characters(buffer, 0, n);
                   n = reader.read(buffer);
                  }
                  xhtml.endElement("p");

            	   xhtml.endDocument();
        	}

		
                
        }
}
