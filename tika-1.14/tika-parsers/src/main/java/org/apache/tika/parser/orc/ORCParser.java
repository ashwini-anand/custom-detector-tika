package org.apache.tika.parser.orc;

/**
 * Created by asanand on 2/1/17.
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.orc.OrcFile;
import org.apache.orc.Reader;
import org.apache.orc.RecordReader;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AbstractParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.XHTMLContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;


public class ORCParser extends AbstractParser implements ORCConstants {

    private static final Set<MediaType> SUPPORTED_TYPES = Collections.singleton(MediaType.application(FILE_TYPE));

    public Set<MediaType> getSupportedTypes(ParseContext context) {
        return SUPPORTED_TYPES;
    }

    public void parse(
            InputStream stream, ContentHandler handler,
            Metadata metadata, ParseContext context)
            throws IOException, SAXException, TikaException {

        metadata.set("Type", "ORC");

        try {
            XHTMLContentHandler xhtml =
                    new XHTMLContentHandler(handler, metadata);
            xhtml.startDocument();

            xhtml.startElement("p");
            Configuration conf = new Configuration();
            FileSystem fs = FileSystem.getLocal(conf);
            TikaInputStream tstream = TikaInputStream.cast(stream);
            Path hpath = new Path(tstream.getPath().toString());
            Reader reader = OrcFile.createReader(hpath, OrcFile.readerOptions(conf).filesystem(fs));

            metadata.set(METADATA_SIZE, String.valueOf(reader.getMetadataSize()));
            metadata.set(NUMBER_OF_ROWS, String.valueOf(reader.getNumberOfRows()));
            metadata.set(FILE_VERSION, String.valueOf(reader.getFileVersion()));
            metadata.set(RAW_DATA_SIZE, String.valueOf(reader.getRawDataSize()));
            metadata.set(COMPRESSION_KIND, String.valueOf(reader.getCompressionKind()));
            metadata.set(COMPRESSION_SIZE, String.valueOf(reader.getCompressionSize()));
            metadata.set(ROW_INDEX_STRIDE, String.valueOf(reader.getRowIndexStride()));
            metadata.set(SCHEMA, String.valueOf(reader.getSchema()));
            metadata.set(NUMBER_OF_STRIPES, String.valueOf(reader.getStripes().size()));
            metadata.set(WRITER_VERSION, String.valueOf(reader.getWriterVersion()));
            metadata.set(FILE_TAIL, String.valueOf(reader.getFileTail()));

            RecordReader rows = reader.rows();
            VectorizedRowBatch batch = reader.getSchema().createRowBatch();
            while (rows.nextBatch(batch)) {
                for (int r = 0; r < batch.size; r++) {
                    xhtml.characters(batch.toString());
                }
            }
            xhtml.endElement("p");

            xhtml.endDocument();
            rows.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
