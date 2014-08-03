package ua.kiev.sa;

import com.sun.xml.internal.ws.util.xml.XmlUtil;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;

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

import org.xml.sax.SAXException;

public class AddNewCategoryFrame extends JDialog {

    public AddNewCategoryFrame() {
        addNewCategoryFrame = new JDialog();
        addNewCategoryFrame.setModal(true);
        addNewCategoryFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addNewCategoryFrame.add(createNewCategoryPanel());
        addNewCategoryFrame.setTitle("Добавьте категорию");
        addNewCategoryFrame.pack();
        addNewCategoryFrame.validate();
        addNewCategoryFrame.setLocationRelativeTo(null);
        addNewCategoryFrame.setVisible(true);

    }

    private JPanel createNewCategoryPanel() {
        addNewCategoryPanel = new JPanel();
        addNewCategoryPanel.setLayout(new GridBagLayout());

        category = new JLabel("Категория");
        addNewCategoryPanel.add(category,
                new GridBagConstraints(1, 0, 5, 1, 0, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0),
                        0, 0));

        subCat = new JLabel("Подкатегория");
        addNewCategoryPanel.add(subCat,
                new GridBagConstraints(1, 3, 5, 1, 0, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0),
                        0, 0));
        XMLAnalize xmla = new XMLAnalize();
        categoriesList = xmla.getTm().get("Категории");

        newCategoryCombo = new JComboBox(categoriesList.toArray());
        newCategoryCombo.setEditable(false);
        newCategoryCombo.insertItemAt("Новая", 0);
        addNewCategoryPanel.add(newCategoryCombo,
                new GridBagConstraints(1, 1, 5, 1, 0, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0),
                        0, 0));
        newSubCategoryField = new JTextField(30);
        addNewCategoryPanel.add(newSubCategoryField,
                new GridBagConstraints(1, 4, 5, 1, 0, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0),
                        0, 0));

        cancelButton = new JButton("Cancel");
        addNewCategoryPanel.add(cancelButton,
                new GridBagConstraints(4, 5, 1, 1, 0, 0,
                        GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0),
                        0, 0));
        okButton = new JButton("OK");
        addNewCategoryPanel.add(okButton,
                new GridBagConstraints(5, 5, 1, 1, 0, 0,
                        GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0),
                        0, 0));

        newCategoryCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (newCategoryCombo.getSelectedItem() == "Новая") {
                    newCatTextField =
                            new JTextField("Введите новую категорию", 30);

                    addNewCategoryPanel.add(newCatTextField,
                            new GridBagConstraints(1, 2, 5,
                                    1, 0, 0,
                                    GridBagConstraints.CENTER,
                                    GridBagConstraints.NONE,
                                    new Insets(5,
                                            0,
                                            5,
                                            0),
                                    0, 0));
                    validate();
                    repaint();
                    pack();
                } else {
                    if (((String)newCategoryCombo.getSelectedItem()) !=
                            "Новая") {
                        if (addNewCategoryPanel.getComponentCount() == 7) {
                            addNewCategoryPanel.remove(newCatTextField);
                            System.out.println("OK1");
                        } else
                            System.out.println("OK");
                        validate();
                        repaint();
                        pack();
                    }
                }
            }
        });


        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNewCategoryFrame.setVisible(false);
                addNewCategoryFrame.dispose();
                MyWallet.mainFrame.setEnabled(true);
                MyWallet.mainFrame.toFront();
            }
        });

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (newCategoryCombo.getSelectedItem().toString() ==
                        "Новая") {
                    String newCategory = newCatTextField.getText();
                    String subCategory = newSubCategoryField.getText();
                    DefaultMutableTreeNode a =
                            new DefaultMutableTreeNode(newCategory);
                    DefaultMutableTreeNode b =
                            new DefaultMutableTreeNode(newSubCategoryField.getText());
                    addingToXML(new File("Categories1.xml"), newCategory,
                            subCategory);
                    al1 = NodePanel.rootMap.get(NodePanel.root.toString());
                    al1.add(newCategory);
                    NodePanel.rootMap.put(NodePanel.root.getRoot().toString(),
                            al1);
                    al2 = new ArrayList<String>();
                    al2.add(newSubCategoryField.getText());
                    NodePanel.rootMap.put(newCategory, al2);
                    NodePanel.addNewNode(NodePanel.root, a);
                    NodePanel.addNewNode(a, b);
                    addNewCategoryFrame.setVisible(false);
                    addNewCategoryFrame.dispose();
                    MyWallet.mainFrame.validate();
                    MyWallet.mainFrame.repaint();
                    MyWallet.mainFrame.pack();
                    MyWallet.mainFrame.setEnabled(true);
                    MyWallet.mainFrame.toFront();
                } else {
                    String newCategory =
                            (String)newCategoryCombo.getSelectedItem();
                    String subCategory = newSubCategoryField.getText();
                    addingToXML(new File("Categories1.xml"), newCategory,
                            subCategory);
                    DefaultMutableTreeNode a =
                            new DefaultMutableTreeNode(newCategory); //alcohol
                    DefaultMutableTreeNode b =
                            new DefaultMutableTreeNode(subCategory); //punsh
                    NodePanel.addNewList(a, b);

                    /////////////////////////////////////////////////////
                    String s1 = (String)newCategoryCombo.getSelectedItem();
                    al1 = new ArrayList<String>();
                    al1.add(s1); //alcohol
                    al2 = new ArrayList<String>();
                    al2.add(newSubCategoryField.getText()); //punsh
                    ArrayList<String> arrayList =
                            NodePanel.rootMap.get(s1);
                    arrayList.add(subCategory);
                    NodePanel.rootMap.put(s1, arrayList);


                    /////////////////////////////////////////////////////////////


                    addNewCategoryFrame.setVisible(false);
                    addNewCategoryFrame.dispose();
                    MyWallet.mainFrame.validate();

                    MyWallet.mainFrame.repaint();

                    MyWallet.mainFrame.pack();
                    MyWallet.mainFrame.setEnabled(true);
                    MyWallet.mainFrame.toFront();


                }

            }


        });

        return addNewCategoryPanel;
    }

    public void addingToXML(File file, String cat, String subCat) {
        boolean b = true;
        boolean c = true;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            Element rootNode = doc.getDocumentElement();
            NodeList noodList = rootNode.getChildNodes();
            for (int i = 0; i < noodList.getLength(); i++) {
                Node child = noodList.item(i); //категории: алк, гиг...
                if (child instanceof Element) {
                    Element childElement =
                            (Element)child; //категории: алк, гиг...
                    if (childElement.getAttribute("CategoryName").equals(cat)) {
                        Element sub = doc.createElement("Subcategory");
                        Element categ = doc.createElement("Category");
                        b = false;
                        NodeList subCats = childElement.getChildNodes();


                        for (int j = 0; j < subCats.getLength(); j++) {
                            Node subNode = subCats.item(j);

                            if (subNode instanceof Element) {
                                Element elem = (Element)subNode;
                                if (elem.getAttribute("SubcategoryName").equalsIgnoreCase(subCat)) {
                                    c = false;
                                    break;
                                }

                            }
                        }
                        if (c) {
                            childElement.appendChild(sub);
                            sub.setAttribute("SubcategoryName", subCat);

                        }
                    }

                }

            }
            if (b) {
                Element sub = doc.createElement("Subcategory");
                Element categ = doc.createElement("Category");
                rootNode.appendChild(categ);
                categ.setAttribute("CategoryName", cat);
                categ.appendChild(sub);
                sub.setAttribute("SubcategoryName", subCat);
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
        }

        catch (ParserConfigurationException e) {
            System.out.println(e);
        } catch (SAXException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } catch (TransformerConfigurationException e) {
        } catch (TransformerException e) {
        }


    }


    private JButton okButton;
    private JButton cancelButton;
    private JTextField newCatTextField;
    private JDialog addNewCategoryFrame;
    private JPanel addNewCategoryPanel;
    private JComboBox newCategoryCombo;
    private JTextField newSubCategoryField;
    static ArrayList<String> al2 = new ArrayList<String>();
    static DefaultMutableTreeNode node0;
    static ArrayList<String> al1 = new ArrayList<String>();
    static HashMap<String, ArrayList<String>> rootMap =
            new HashMap<String, ArrayList<String>>();
    Hashtable ht = new Hashtable();
    ArrayList<String> value = new ArrayList<String>();
    private DefaultMutableTreeNode rootNode;
    private static DefaultTreeModel model;

    private User c = null;
    private JFrame addInOperFrame;
    private JPanel addInOperPanel;
    private String[][] cats;
    private Hashtable h;
    private Enumeration enumer;
    private JPanel jPan;
    private JScrollPane inScrollPane;
    private JLabel category;
    private JLabel subCat;
    private ArrayList<String> categoriesList;
    //    private DefaultMutableTreeNode root;
    private XMLAnalize xmla;


}
