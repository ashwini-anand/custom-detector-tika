package org.apache.tika.parser.orc;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.tika.detect.Detector;
import org.apache.tika.io.LookaheadInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.commons.io.IOUtils;

/**
 * Created by asanand on 2/1/17.
 */
public class ORCDetector implements Detector, ORCConstants {
    private static final long serialVersionUID = -1709652690773421147L;

    private static final int LOOK_AHEAD_SIZE = 10;
    private static final String TYPE_IDENTIFIER = "ORC";


    public MediaType detect(InputStream stream, Metadata metadata)
            throws IOException {

        MediaType type = MediaType.OCTET_STREAM;

        InputStream truncStream = null;
        try {
            truncStream = new LookaheadInputStream(stream, LOOK_AHEAD_SIZE);
            String fileData = IOUtils.toString(truncStream, StandardCharsets.UTF_8);
            if (fileData.contains(TYPE_IDENTIFIER)) {
                type = MediaType.application(FILE_TYPE);
            }
        } catch (Exception e) {
            System.out.println("----Exception in ORCDetector----------------------");
            e.printStackTrace();
        } finally {
            truncStream.close();
        }
        return type;
    }
}
