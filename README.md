# custom-detector-tika

Building and running:

using command line and GUI

1) Go inside the folder tika-1.14
2) Run  $ mvn clean install -DskipTests
It will generate target file in tike-app
3) Go inside target file and run $ java -jar tika-app-1.14.jar
This will open GUI for tika.
4) There are two input files in this repository. any8xxyx and any9Notxxyx
Pick one of the file and drop into GUI. For any8xxyx, metadata should have content-type as text/xxyx (as it is file of type text/xxyx). For any9Notxxyx, tika will detect it as text/plain.
Reason: any file which contains 2 or more times xxyxxxyx in first 1024 bytes is a file of type xxyx. any8xxyx satisfies this while any9xxyx does not (any9xxyx is detected as text/plain by default detector.)

———————————————————————

changes made into the tika source code
1) Two files , XXYXDetector.java and XXYXParse.java, are created inside tika-1.14/tika-parsers/src/main/java/org/apache/tika/parser/xxyx
(codes are not formatted properly and some useless imports are there. will clean up)

2) Entries for them are made in tika-1.14/tika-parsers/src/main/resources/META-INF/services/ org.apache.tika.detect.Detector  and org.apache.tika.parser.Parser respectively.

3)Entry for mime-type is made in tika-1.14/tika-core/src/main/resources/org/apache/tika/mime tika-mimetypes.xml

Notes:

1)Parsed text is available as RecursiveJson.

2) I did not do it for tab separated values file as tika already have entry in their mime database for that type of file. 

3) Found bugs. Mentioned in Bugs.txt .


