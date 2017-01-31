package org.apache.tika.parser.xxyx;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.detect.Detector;
import org.apache.tika.detect.XmlRootExtractor;
import org.apache.tika.io.LookaheadInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.commons.io.IOUtils;

import java.nio.charset.StandardCharsets;


public class XXYXDetector implements Detector, XXYX {
    private static final long serialVersionUID = -1709652690773421147L;

    private static int numOfOccurances =0;
    private static final int lookAheadSize = 1024;
    private static final int thresholdOccurance = 2;
    private static final String typeIdentifier = "xxyxxxyx";

    public static int getNumOfOccurances() {
        return numOfOccurances;
    }

    public MediaType detect(InputStream stream, Metadata metadata)
            throws IOException {

        MediaType type = MediaType.OCTET_STREAM;

        InputStream truncStream = null;
        try {
            truncStream = new LookaheadInputStream(stream, lookAheadSize);
            String fileData = IOUtils.toString(truncStream, StandardCharsets.UTF_8);
            numOfOccurances =0;
            int lastIndex = 0;
            while (lastIndex != -1) {
                lastIndex = fileData.indexOf(typeIdentifier, lastIndex);
                if (lastIndex != -1) {
                    numOfOccurances++;
                    lastIndex += typeIdentifier.length();
                }

            }
            if (numOfOccurances >= thresholdOccurance) {
                type = MediaType.text(FILE_TYPE);
            }
        } catch (Exception e) {
            System.out.println("----Exception in XXYXDetector----------------------");
            e.printStackTrace();
        } finally {
            truncStream.close();
        }
        return type;
    }
}


