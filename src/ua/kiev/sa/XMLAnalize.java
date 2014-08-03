package ua.kiev.sa;

import java.io.BufferedWriter;
import java.io.File;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import java.io.ObjectOutputStream;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.ArrayList;

import java.util.TreeMap;

import javax.swing.tree.DefaultMutableTreeNode;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import org.xml.sax.SAXException;


public class XMLAnalize {
    private NodeList noodList;
    private ArrayList<String> category = new ArrayList<String>();
    private ArrayList<String> subcategory = new ArrayList<String>();
    protected TreeMap<String, ArrayList<String>> tm =
            new TreeMap<String, ArrayList<String>>();
    private Element root;

    public XMLAnalize() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File f = new File("Categories1.xml");
            Document doc = db.parse(f);
            root = doc.getDocumentElement();
            noodList = root.getChildNodes();
            filling(noodList, category);
        } catch (ParserConfigurationException e) {
            System.out.println(e);
        } catch (SAXException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public XMLAnalize(File f) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(f);
            root = doc.getDocumentElement();
            noodList = root.getChildNodes();
            filling(noodList, category);
        } catch (ParserConfigurationException e) {
            System.out.println(e);
        } catch (SAXException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    private TreeMap<String, ArrayList<String>> filling(NodeList list,
                                                       ArrayList<String> arr) {
        String categoryStr = "";
        tm.put(root.getNodeName(), category);
        for (int i = 0; i < list.getLength(); i++) {
            Node child = list.item(i);
            if (child instanceof Element) {
                Element childElement = (Element)child;
                if (childElement.getParentNode() == root) {
                    arr = new ArrayList<String>();
                    //                    System.out.println("!=");
                    category.add(childElement.getAttribute(childElement.getTagName() +
                            "Name"));
                }
                categoryStr =
                        childElement.getAttribute(childElement.getTagName() +
                                "Name");

                arr.add(categoryStr);
                //                System.out.println(arr);
                if (childElement.hasChildNodes()) {
                    arr = new ArrayList<String>();
                    tm.put(childElement.getAttribute(childElement.getTagName() +
                            "Name"), arr);
                    NodeList subchild = childElement.getChildNodes();
                    filling(subchild, arr);


                }
            }

        }
        return tm;
    }

    public void setTm(TreeMap<String, ArrayList<String>> tm1) {
        tm = tm1;
    }

    public TreeMap<String, ArrayList<String>> getTm() {
        return tm;
    }

    public static void deleteItemFromXml(Object node) {
        try {
            DefaultMutableTreeNode deletingNode=(DefaultMutableTreeNode)node;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse("Categories1.xml");
            Element rootNode = doc.getDocumentElement();
            NodeList noodList = rootNode.getChildNodes();
            for (int i = 0; i < noodList.getLength(); i++) {
                Node child = noodList.item(i); //категории: алк, гиг...
                if (child instanceof Element) {
                    Element childElement =
                            (Element)child; //категории: алк, гиг...
                    System.out.println(childElement.getAttribute("CategoryName"));
                    System.out.println(((DefaultMutableTreeNode)deletingNode).toString());
                    if (childElement.getAttribute("CategoryName").equals(((DefaultMutableTreeNode)deletingNode).toString())) {
                        rootNode.removeChild(childElement);
                    }
                }

            }
            Transformer transformer =
                    TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);
            String xmlString = result.getWriter().toString();
            System.out.println(xmlString);
            File f = new File("Categories1.xml");
            PrintWriter output = new PrintWriter(f);
            output.println(xmlString);
            output.close();

        } catch (ParserConfigurationException e) {
            System.out.println(e);
        } catch (SAXException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } catch (TransformerConfigurationException e) {
        } catch (TransformerException e) {
        } finally {

        }
    }

}

