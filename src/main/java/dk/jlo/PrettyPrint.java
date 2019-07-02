package dk.jlo;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.nio.charset.Charset;

public class PrettyPrint {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));

        prettify(reader, System.out, 4);
    }

    @SuppressWarnings("SameParameterValue")
    private static void prettify(BufferedReader reader, PrintStream outputStream, int indent) throws Exception {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.isEmpty()) { // skip blank lines
                if(line.indexOf('<') > 0) {
                    String comment = line.substring(0, line.indexOf('<'));
                    System.out.println("<!-- " + comment + " -->");
                    line = line.substring(line.indexOf('<'));
                }
                Document document = documentBuilder.parse(new InputSource(new StringReader(line)));
                prettify(document, outputStream, indent);
            }
        }
    }

    private static void prettify(Document document, OutputStream outputStream, int indent) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        if (indent > 0) {
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(indent));
        }
        Result result = new StreamResult(outputStream);
        Source source = new DOMSource(document);
        transformer.transform(source, result);
    }
}
