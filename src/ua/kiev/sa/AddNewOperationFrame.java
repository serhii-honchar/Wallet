package ua.kiev.sa;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
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

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

public class AddNewOperationFrame extends JDialog {
Connection conn=null;
    public AddNewOperationFrame(User user) {
        c = user;
        addNewOperationFrame = new JDialog();
        addNewOperationFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addNewOperationFrame.setModal(true);
        addNewOperationFrame.add(addInOperPanel());
        addNewOperationFrame.setTitle("Введите новую операцию");
        addNewOperationFrame.setLayout(new GridBagLayout());
        Dimension d = new Dimension(200, 300);
        addNewOperationFrame.setMinimumSize(d);
        addNewOperationFrame.pack();
        addNewOperationFrame.validate();
        addNewOperationFrame.setLocationRelativeTo(null);
        addNewOperationFrame.setVisible(true);
        this.setLocationRelativeTo(null);
    }


    private JPanel addInOperPanel() {
        addInOperPanel = new JPanel();
        addInOperPanel.setLayout(new GridBagLayout());
        Font f = new Font(Font.MONOSPACED, Font.BOLD, 24);

        JLabel operLabel = new JLabel("Операция");
        operLabel.setFont(f);
        addInOperPanel.add(operLabel,
                new GridBagConstraints(0, 1, 15, 1, 0, 0, GridBagConstraints.NORTH,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0,
                        0));
        // создание метки с датой
        JLabel operDate = new JLabel("Дата");
        addInOperPanel.add(operDate,
                new GridBagConstraints(3, 3, 1, 1, 0, 0, GridBagConstraints.SOUTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0), 0,
                        0));
        //создание текстового поля с датой

        dataField = new JTextField(15);
        dataField.setText(Operation.createDateForTextField());
        dataField.setToolTipText("Дата операции в формате DD.MM.YY HH:MM");
        addInOperPanel.add(dataField,
                new GridBagConstraints(1, 4, 3, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 5, 0), 0,
                        0));

        //////////////////////////////////////////////////////
        //
        //       Работа с категориями
        //
        /////////////////////////////////////////////////////

        //метка категории
        JLabel category = new JLabel("Категория");
        addInOperPanel.add(category,
                new GridBagConstraints(5, 3, 1, 1, 0, 0, GridBagConstraints.SOUTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0), 0,
                        0));

//        xmla = new XMLAnalize();
//        al1 = xmla.getTm().get("Категории");
        // список категорий
        try {
            conn=MyWallet.cp.ds.getConnection();
            Statement stmt= conn.createStatement();
            String getCategories="Select category from '"+c.getLogin()+"categories'";
            ResultSet rt=stmt.executeQuery(getCategories);
            while (rt.next()){

             al1.add((String)rt.getString("category"));
            }

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        categoryChooserComboBox = new JComboBox(al1.toArray());
        categoryChooserComboBox.setEditable(false);
        categoryChooserComboBox.setToolTipText("Выберите категорию");
        categoryChooserComboBox.setSelectedIndex(0);
        addInOperPanel.add(categoryChooserComboBox,
                new GridBagConstraints(4, 4, 3, 1, 0, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0), 0,
                        0));


        choosenCategory = (String)categoryChooserComboBox.getItemAt(0);
        choosenSubcategory = "";
        categoryChooserComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                choosenCategory =
                        (String)categoryChooserComboBox.getSelectedItem();
                subCategoryChooserComboBox.removeAllItems();
                try {
                    conn=MyWallet.cp.ds.getConnection();
                    Statement stmt = conn.createStatement();
                    String selSubcat="Select subcategories from "+c.getLogin()+"subcategory where id_category=" +
                            "Select id from "+c.getLogin()+"categories where category='"+choosenCategory+"')";
                    ResultSet rs=stmt.executeQuery(selSubcat);
                    while(rs.next()){
                        al2.add((String)rs.getString(1));

                    }
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                for (String s : al2) {
                    subCategoryChooserComboBox.addItem(s);
                }
                addInOperPanel.revalidate();
                addInOperPanel.repaint();
                subCategoryChooserComboBox.validate();
            }
        });

        // список подкатегорий
        JLabel subcategory = new JLabel("Подкатегория");
        addInOperPanel.add(subcategory,
                new GridBagConstraints(5, 5, 1, 1, 0, 0,
                        GridBagConstraints.SOUTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0), 0,
                        0));

        subCategoryChooserComboBox =
                new JComboBox(xmla.getTm().get(choosenCategory).toArray());
        subCategoryChooserComboBox.setEditable(false);
        subCategoryChooserComboBox.setToolTipText("Выберите подкатегорию");
        addInOperPanel.add(subCategoryChooserComboBox,
                new GridBagConstraints(4, 6, 3, 1, 0, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0), 0,
                        0));

        subCategoryChooserComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                choosenSubcategory =
                        (String)subCategoryChooserComboBox.getSelectedItem();
            }
        });

        // кнопка добавления категории
        addNewCategorButton = new JButton("Добавить категорию");
        addInOperPanel.add(addNewCategorButton,
                new GridBagConstraints(5, 7, 1, 1, 0, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 5, 5), 0,
                        0));
        addNewCategorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddNewCategoryFrame newCategoryFrame =
                        new AddNewCategoryFrame();

                categoryChooserComboBox.removeAll();
                XMLAnalize xmla1 = new XMLAnalize();

                for (String s : xmla.getTm().get("Категории")) {
                    categoryChooserComboBox.addItem(s);
                }
                subCategoryChooserComboBox.removeAll();
                for (String s : xmla1.getTm().get(choosenCategory)) {
                    subCategoryChooserComboBox.addItem(s);

                }


            }
        });

        //////////////////////////////////////////////////////////////////////
        //
        //    Выбор счета
        //
        //////////////////////////////////////////////////////////////////////

        accountName = new JLabel("Счет");
        addInOperPanel.add(accountName,
                new GridBagConstraints(8, 3, 1, 1, 0, 0,
                        GridBagConstraints.SOUTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 5, 5), 0,
                        0));

        accountChooserComboBox =
                new JComboBox(Account.getAccountsNames().toArray());
        accountChooserComboBox.setSelectedIndex(0);
        accountChooserComboBox.setEditable(false);
        accountChooserComboBox.setToolTipText("Выберите аккаунт");
        addInOperPanel.add(accountChooserComboBox,
                new GridBagConstraints(8, 4, 1, 1, 0, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(5, 5, 5, 5), 0,
                        0));
        setCurrentAccountName((String)accountChooserComboBox.getItemAt(0));
        System.out.println(currentAccountName);
        accountChooserComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setCurrentAccountName((String)accountChooserComboBox.getSelectedItem());

                currentSumm = getCurrentSumm();
                currentSumLabel.setText("На счету до транзакции: " +
                        currentSumm);
                postOperationSumLabel.setText("На счету после транзакции: " +
                        setFutureSumm(summ.getText()));
            }
        });

        ///////////////////////////////////////////////////////////////////////
        //
        //                Работа с суммой
        //
        ///////////////////////////////////////////////////////////////////////

        summName = new JLabel("Сумма");
        addInOperPanel.add(summName,
                new GridBagConstraints(13, 3, 2, 1, 0, 0, GridBagConstraints.SOUTH,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0), 0,
                        0));

        summ = new JTextField(30);
        summ.setToolTipText("Введите сумму транзакции");
        summ.setText("0.0");
        addInOperPanel.add(summ,
                new GridBagConstraints(13, 4, 2, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 5), 0,
                        0));

        summ.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                System.out.println("++++++++++++++++++++++++");

                currentSumLabel.setText("На счету до транзакции: " +
                        currentSumm);
                postOperationSumLabel.setText("На счету после транзакции: " +
                        setFutureSumm(summ.getText()));
            }

            public void insertUpdate(DocumentEvent e) {
                currentSumLabel.setText("На счету до транзакции: " +
                        currentSumm);
                postOperationSumLabel.setText("На счету после транзакции: " +
                        setFutureSumm(summ.getText()));
            }

            public void removeUpdate(DocumentEvent e) {
                currentSumLabel.setText("На счету до транзакции: " +
                        currentSumm);
                postOperationSumLabel.setText("На счету после транзакции: " +
                        setFutureSumm(summ.getText()));
            }
        });
        currentSumLabel =
                new JLabel("На счету до транзакции: " + getCurrentSumm());
        addInOperPanel.add(currentSumLabel,
                new GridBagConstraints(13, 5, 2, 1, 0, 0,
                        GridBagConstraints.WEST,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0), 0,
                        0));
        postOperationSumLabel =
                new JLabel("На счету после транзакции: " + setFutureSumm(summ.getText()));
        addInOperPanel.add(postOperationSumLabel,
                new GridBagConstraints(13, 6, 2, 1, 0, 0,
                        GridBagConstraints.WEST,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0), 0,
                        0));
        ////////////////////////////////////////////////////////////////////////////////////
        //
        //      Работа с кнопками
        //
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        inCancel = new JButton("Cancel");
        addInOperPanel.add(inCancel,
                new GridBagConstraints(13, 7, 1, 1, 0, 0, GridBagConstraints.WEST,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0), 0,
                        0));
        inCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addInOperPanel.setVisible(false);
                addNewOperationFrame.dispose();
                MyWallet.mainFrame.setEnabled(true);
                MyWallet.mainFrame.toFront();
            }
        });
        inOK = new JButton("OK");
        addInOperPanel.add(inOK,
                new GridBagConstraints(14, 7, 1, 1, 0, 0, GridBagConstraints.EAST,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 5), 0,
                        0));
        inOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Operation newOperation =
                        new Operation(c.getLogin(), (String)categoryChooserComboBox.getSelectedItem(),
                                (String)subCategoryChooserComboBox.getSelectedItem(),
                                Operation.createDateFromString(dataField.getText()),
                                (String)accountChooserComboBox.getSelectedItem(),
                                Float.valueOf(summ.getText()));
                Operation.operate.add(newOperation);
                ArrayList<Account> al = c.getAcc();
                for (Account c : al) {
                    if (((String)accountChooserComboBox.getSelectedItem()).equals(c.getAccount())) {
                    }
                    System.out.println("все ок");
                    c.setSum(Float.valueOf(summ.getText()));
                }

//                ManePanel.setCurrentBalance(String.valueOf(c.getAcc().get(0).getSum()));
                addNewOperationFrame.dispose();
                ManePanel.setLastOperationsTable();
            }
        });
        pack();
        addInOperPanel.validate();
        addInOperPanel.repaint();
        return addInOperPanel;


    }


    private JPanel addInOperPanel;
    private JButton inOK;
    private JButton inCancel;
    private JDialog addNewOperationFrame;
    private JComboBox categoryChooserComboBox;
    static ArrayList<String> al2 = new ArrayList<String>();

    static ArrayList<String> al1 = new ArrayList<String>();
    static HashMap<String, ArrayList<String>> rootMap =
            new HashMap<String, ArrayList<String>>();
    Hashtable ht = new Hashtable();
    ArrayList<String> value = new ArrayList<String>();
    private DefaultMutableTreeNode root;
    private User c = null;
    private JComboBox subCategoryChooserComboBox;
    private String choosenCategory;
    private String choosenSubcategory;
    private JButton addNewCategorButton;
    private JLabel accountName;

    private JComboBox accountChooserComboBox;
    private JLabel summName;
    private JTextField summ;
    private JLabel currentSumLabel;
    private JLabel postOperationSumLabel;
    private XMLAnalize xmla;
    private JTextField dataField;
    private String currentAccountName;
    private Float currentSumm;
    private Float futureSumm;

    public void setCurrentAccountName(String currentAccountName) {
        this.currentAccountName = currentAccountName;
    }

    public String getCurrentAccountName() {
        return currentAccountName;
    }

    public Float getCurrentSumm() {
        ArrayList<Account> acc = c.getAcc();
        for (Account account : acc) {
            System.out.println(currentAccountName + "111111111111111111111");
            if ((account.getAccount()).equals(currentAccountName)) {
                currentSumm = account.getSum();
                System.out.println(currentSumm);
            }
        }
        System.out.println(currentSumm + "currentSumm");
        return currentSumm;
    }


    private Float setFutureSumm(String string) {
        futureSumm = getCurrentSumm() + Float.parseFloat(string);
        System.out.println(futureSumm + "futureSumm ");
        return futureSumm;
    }


//    public static void addingOperationToXML(File file, Operation op) {
//        boolean b = true;
//        boolean c = true;
//        try {
//            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//            DocumentBuilder db = dbf.newDocumentBuilder();
//            Document doc = db.parse(file);
//            Element rootNode = doc.getDocumentElement();
//            NodeList noodList = rootNode.getChildNodes();
//            for (int i = 0; i < noodList.getLength(); i++) {
//                Node child = noodList.item(i);
//                Attr dataAtr = doc.createAttribute("Data");
//                dataAtr.setNodeValue(Operation.createDate(op.getD()));
//
//            }
//
//            Transformer transformer =
//                    TransformerFactory.newInstance().newTransformer();
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//            StreamResult result = new StreamResult(new StringWriter());
//            DOMSource source = new DOMSource(doc);
//            transformer.transform(source, result);
//            String xmlString = result.getWriter().toString();
//            System.out.println(xmlString);
//            PrintWriter output = new PrintWriter(file);
//            output.println(xmlString);
//            output.close();
//        }
//
//        catch (ParserConfigurationException e) {
//            System.out.println(e);
//        } catch (SAXException e) {
//            System.out.println(e);
//        } catch (IOException e) {
//            System.out.println(e);
//        } catch (TransformerConfigurationException e) {
//        } catch (TransformerException e) {
//        }
//
//
//    }
//

}
