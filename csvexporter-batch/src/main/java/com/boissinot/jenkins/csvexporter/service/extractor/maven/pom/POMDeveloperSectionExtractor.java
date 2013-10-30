package com.boissinot.jenkins.csvexporter.service.extractor.maven.pom;

import com.boissinot.jenkins.csvexporter.domain.maven.pom.Developer;
import com.boissinot.jenkins.csvexporter.exception.ExportException;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gregory Boissinot
 */
public class POMDeveloperSectionExtractor {

    public List<Developer> extract(String pomContent) {

        List<Developer> developerList = new ArrayList<Developer>();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder;
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(pomContent));
            Document document = documentBuilder.parse(is);

            XPathFactory xpathFact = XPathFactory.newInstance();
            XPath xpath = xpathFact.newXPath();
            org.w3c.dom.NodeList developerNodeList = (org.w3c.dom.NodeList) xpath.evaluate("//developer", document, XPathConstants.NODESET);

            for (int i = 0; i < developerNodeList.getLength(); i++) {

                Developer developer = new Developer();
                Node developerNode = developerNodeList.item(i);

                if (developerNode instanceof Element) {

                    //Extract id
                    NodeList idNodeList = ((Element) developerNode).getElementsByTagName("id");
                    if (idNodeList.getLength() != 0) {
                        Element el = (Element) idNodeList.item(0);
                        Text elText = (Text) el.getFirstChild();
                        developer.setId(elText.getNodeValue());
                    }

                    //Extract email
                    NodeList emailNodeList = ((Element) developerNode).getElementsByTagName("email");
                    if (emailNodeList.getLength() != 0) {
                        Element el = (Element) emailNodeList.item(0);
                        Text elText = (Text) el.getFirstChild();
                        developer.setEmail(elText.getNodeValue());
                    }

                }
                developerList.add(developer);
            }
        } catch (ParserConfigurationException pe) {
            throw new ExportException(pe);
        } catch (SAXException sa) {
            throw new ExportException(sa);
        } catch (XPathExpressionException xe) {
            throw new ExportException(xe);
        } catch (IOException ioe) {
            throw new ExportException(ioe);
        }

        return developerList;
    }
}
