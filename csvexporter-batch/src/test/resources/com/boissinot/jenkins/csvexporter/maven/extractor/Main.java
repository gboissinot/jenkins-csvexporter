package com.boissinot.jenkins.csvexporter.maven.extractor;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Administrateur
 * Date: 18/09/13
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
public class Main {


    public static void main(String[] args) throws Exception{
        URL url = new URL("http://controle/cgi-bin/viewvc.cgi/GD/Tools/NxExtractor/pom.xml?revision=release_1_17_1");
        //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("195.221.0.4", 8080));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        //195.221.0.4:8080

        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setDoOutput(false);
        StringBuilder builder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        int c;
        while ((c = br.read()) != -1) {
            builder.append((char) c);
        }


        System.out.println(builder.toString());
    }

    public static void main2(String[] args) throws Exception {


        File file = new File("C:\\Dev\\jenkins-csvexporter\\csvexporter-batch\\src\\test\\resources\\com\\boissinot\\jenkins\\csvexporter\\maven\\extractor\\pom-1.xml");

        StringBuilder builder = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        int c;
        while ((c = br.read()) != -1) {
            builder.append((char) c);
        }

        String pomContent = builder.toString();
        //Source pomContentSource = new StringSource(pomContent);
        //Jaxp13XPathTemplate template = new Jaxp13XPathTemplate();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(pomContent));
        Document document = documentBuilder.parse(is);


        XPathFactory xpathFact = XPathFactory.newInstance();
        XPath xpath = xpathFact.newXPath();
        //String text123 = (String) xpath.evaluate("/data/keyword[1]/@name", xmlDoc, XPathConstants.STRING);


        org.w3c.dom.NodeList developerNodeList = (org.w3c.dom.NodeList) xpath.evaluate("//developer", document, XPathConstants.NODESET);
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < developerNodeList.getLength(); i++) {

            StringBuffer developerInfo = new StringBuffer();

            Node developerNode = developerNodeList.item(i);

            if (developerNode instanceof Element) {

                //Extract id
                NodeList idNodeList = ((Element) developerNode).getElementsByTagName("id");
                if (idNodeList.getLength() != 0) {
                    Element el = (Element) idNodeList.item(0);
                    Text elText = (Text) el.getFirstChild();
                    developerInfo.append("/");
                    developerInfo.append(elText.getNodeValue());
                }

                //Extract email
                NodeList emailNodeList = ((Element) developerNode).getElementsByTagName("email");
                if (emailNodeList.getLength() != 0) {
                    Element el = (Element) emailNodeList.item(0);
                    Text elText = (Text) el.getFirstChild();
                    developerInfo.append("/");
                    developerInfo.append(elText.getNodeValue());
                }

            }
            developerInfo.delete(0,1);
            result.append(developerInfo);
            result.append("\n") ;
        }

        System.out.println(result.toString());


//        Node item = emailNodeList.item(0);
//
//        org.w3c.dom.NodeList developerNodeList = ( org.w3c.dom.NodeList)xpath.evaluate("//developer", document, XPathConstants.NODESET);
//        String emailStr1= (String)xpath.evaluate("//email", developerNodeList.item(0), XPathConstants.STRING);
//        xpath.reset();
//        String emailStr2= (String)xpath.evaluate("//email", developerNodeList.item(1), XPathConstants.STRING);
//        System.out.println(emailStr1);
//        System.out.println(emailStr2);


//        NodeList developersNodeList = document.getElementsByTagName("developer");
//
//
//
//
//        //List<Node> developersNodeList = template.evaluateAsNodeList("//developers ", pomContentSource);
//        for (int i=0;i<developersNodeList.getLength();i++) {
//
//            Node developersNode = developersNodeList.item(i);
//
////            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
////            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
////            InputSource is = new InputSource();
////            is.setCharacterStream(new StringReader(content));
////            Document document = documentBuilder.parse(is);
//
//            String emailValue = null;
//
//            NodeList nlEmail = document.getElementsByTagName("email");
//            if (nlEmail.getLength() != 0) {
//                Element el = (Element) nlEmail.item(0);
//                Text elText = (Text) el.getFirstChild();
//                emailValue = elText.getNodeValue();
//            } else {
//                NodeList nlId = document.getElementsByTagName("id");
//                if (nlId.getLength() != 0) {
//                    Element el = (Element) nlId.item(0);
//                    Text elText = (Text) el.getFirstChild();
//                    emailValue = elText.getNodeValue() + "@synchrotron-soleil.fr";
//                }
//            }
//
//            if (emailValue != null) {
//                System.out.println(emailValue);
//            }
//        }

    }
}
