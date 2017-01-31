package org.apache.tika.parser.xxyx;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


import org.apache.commons.io.input.CloseShieldInputStream;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AbstractParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.XHTMLContentHandler;
import org.apache.tika.detect.AutoDetectReader;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class XXYXParser extends AbstractParser implements XXYX {

    private static final Set<MediaType> SUPPORTED_TYPES = Collections.singleton(MediaType.text(FILE_TYPE));
    private static final int bufferSize = 4096;

    public Set<MediaType> getSupportedTypes(ParseContext context) {
        return SUPPORTED_TYPES;
    }

    public void parse(
            InputStream stream, ContentHandler handler,
            Metadata metadata, ParseContext context)
            throws IOException, SAXException, TikaException {

        metadata.set(Metadata.CONTENT_TYPE, MIME_TYPE);
        metadata.set("Hi", "This is xxyxtype file. It contains keyword xxyxxxyx");
        metadata.set("IDENTIFIER_COUNT", Integer.toString(XXYXDetector.getNumOfOccurances()));

        try (AutoDetectReader reader = new AutoDetectReader(
                new CloseShieldInputStream(stream), metadata)) {
            XHTMLContentHandler xhtml =
                    new XHTMLContentHandler(handler, metadata);
            xhtml.startDocument();

            xhtml.startElement("p");
            char[] buffer = new char[bufferSize];
            int n = reader.read(buffer);
            while (n != -1) {
                xhtml.characters(buffer, 0, n);
                n = reader.read(buffer);
            }
            xhtml.endElement("p");

            xhtml.endDocument();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
