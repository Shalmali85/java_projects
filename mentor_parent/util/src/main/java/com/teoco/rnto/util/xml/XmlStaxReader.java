package com.teoco.rnto.util.xml;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;

/**
 * Created by guptaam on 12/16/2014.
 * Please use Java StAX - http://tutorials.jenkov.com/java-xml/stax-xmlstreamreader.html
 *Create a base class that can provide the XMLStreamReader, that can be used for various parsers

 */
public class XmlStaxReader {

    public static XMLStreamReader createStaxReader(InputStream is) throws XMLStreamException {
        XMLInputFactory myFactory = XMLInputFactory.newInstance();
        myFactory.setProperty(XMLInputFactory.IS_VALIDATING, false); // Can cause OOM
        myFactory.setProperty(XMLInputFactory.SUPPORT_DTD, false); // DTD file is usually not present
        myFactory.setProperty(XMLInputFactory.IS_COALESCING, true); // All text is collected before the CHARACTERS event.

        return myFactory.createXMLStreamReader(is);
    }

    public Object parse(InputStream is) throws XMLStreamException {

        XMLStreamReader myReader = createStaxReader(is);
        // loop through document for XML constructs of interest

        while (myReader.hasNext()) {
            int event = myReader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT: {

                   break;
                }
                case XMLStreamConstants.CHARACTERS: {

                    break;
                }
                case XMLStreamConstants.END_ELEMENT: {

                }
            }
        }
        return null;
    }
}
