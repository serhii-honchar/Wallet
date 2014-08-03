package ua.kiev.sa;


import com.sun.javafx.binding.StringFormatter;

import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.IOException;


import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.*;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class ManePanel extends JPanel {
    private Drawing visual = null;
    private final JButton deleteNewAccountButton;
    private  Connection conn=null;

    public ManePanel(User c1) {
        this.c = c1;
        // Создание панели, добавляем компоновщик и устанавливаем название окна
        setLayout(new GridBagLayout());
        // создаем новую панель для отображения графиков, добавляем ее на панель
        Dimension d = new Dimension(300, 200);
        creatingNessessaryDBs();
        visual = new Drawing();
        visual.setPreferredSize(d);
        visual.setBorder(null);
        add(visual,
                new GridBagConstraints(11, 2, 4, 8, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.REMAINDER,
                        new Insets(0, 0, 0, 5), 0, 0));
        System.out.println("Добавлена панель визуализации");
        //создаем метку с отображением логина
        userLabel = new JLabel("User " + c.getLogin());
        // создаем метку ПОПУЛЯРНЫЕ КАТЕГОРИИ
        incomingLabel = new JLabel("Популярные категории");
        //        JLabel costs = new JLabel("Популярные расходы");
        // создаем метку для графических отображений
        visualizationLabel = new JLabel("Диаграммы расходов");

        //добавляем метки на панель
        add(userLabel,
                new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.WEST,
                        GridBagConstraints.BOTH,
                        new Insets(0, 15, 0, 0), 10, 0));
        add(incomingLabel,
                new GridBagConstraints(7, 1, 3, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(0, 5, 0, 0), 0, 0));
        add(visualizationLabel,
                new GridBagConstraints(11, 1, 4, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.REMAINDER,
                        new Insets(5, 0, 5, 0), 0, 0));
        //создаем таблицу для отобаржения последних операций
        System.out.println("Добавлены метки");

        //      именуем столбцы таблицы

        /////////////////////////////////////////////////////////////////////////////
        //
        //
        //                       Таблица операций
        //
        //
        ///////////////////////////////////////////////////////////////////////////////
        dTableModel = new DefaultTableModel();
        dTableModel.setColumnIdentifiers(COLUMNNAMES);

        //==========================================
        // создаем таблицы для хранения операций, категорий и подкатегорий
        try {
            conn=MyWallet.cp.ds.getConnection();
            String creatingUserOperation="CREATE TABLE IF NOT EXISTS '"+c.getLogin()+"Operations' " +
                    "('data' DATE , " +
                    "'category' TEXT, " +
                    "'subcategory' text, " +
                    "'Account' Text, " +
                    "'Summa' float)";
            String creatingUserCategories="CREATE TABLE IF NOT EXISTS '"+c.getLogin()+"categories' " +
                    "('id' INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE , " +
                    "'category' text UNIQUE ON CONFLICT IGNORE)";
            String creatingUserSubcategories = "CREATE TABLE IF NOT EXISTS '"+c.getLogin()+"subcategories' " +
                    "('id' INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                    "'subcategory' text UNIQUE ON CONFLICT IGNORE, " +
                    "'id_cat' integer, " +
                    "foreign key (id_cat) references categories (id));";
            String creatingCategoty="CREATE TABLE IF NOT EXISTS 'categories'" +
                    "('id' INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                    "'category' text)";


            PreparedStatement ps=conn.prepareStatement(creatingCategoty);
            ps.execute();

            PreparedStatement pst = conn.prepareStatement(creatingUserOperation);
            pst.execute();
            PreparedStatement pst1 = conn.prepareStatement(creatingUserCategories);
            boolean b=pst1.execute();
            PreparedStatement pst2=conn.prepareStatement(creatingUserSubcategories);
            pst2.execute();
              //////////////////////
//
            System.out.println("Добавлена таюлица");

            int j=0;
            String query="Select count(*) from '"+c.getLogin()+"categories'";
            Statement st= null;
            try {
                st = conn.createStatement();
                ResultSet rs=st.executeQuery(query);
                while(rs.next()){
                    j=Integer.parseInt((String)rs.getString(1));
                }


            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }


            if(j==0){
                try {
                    String copingDBCategory="INSERT INTO '"+c.getLogin()+"categories' SELECT * FROM 'categories';";
                    PreparedStatement prepareCopingCat=conn.prepareStatement(copingDBCategory);
                    prepareCopingCat.execute();
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                ////////////////////
            }
                int i=0;
                String querySub="Select count(*) from '"+c.getLogin()+"subcategories'";
                try {
                    Statement st1= conn.createStatement();

                    ResultSet rs1=st1.executeQuery(querySub);
                    while(rs1.next()){
                        i=Integer.parseInt((String)rs1.getString(1));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                if(i==0){
                String copingDBSubCategory="INSERT INTO '"+c.getLogin()+"subcategories' SELECT * FROM 'subcategories' ;";
                PreparedStatement prepareCopingSubCat= null;
                try {
                    prepareCopingSubCat = conn.prepareStatement(copingDBSubCategory);
                    prepareCopingSubCat.execute();


                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                }

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        System.out.println("Скопированы таблицы");

        initCats();
        System.out.println("проинициированы категории");


        randomOperate();
        System.out.println("Добавлена случайные операции");

//        try {
//            run.getT1().join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//            randomOperateXML();

        sorter = new SortFilterModel(dTableModel);
        lastOperationsTable = new JTable(sorter);
        lastOperationsTable.setCellEditor(null);

        //создаем таблицу, добавляем полосы прокрутки и размещаем на панели
        lastOperationsTable.getTableHeader().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() < 2)
                    return;
                int tableColumn =
                        lastOperationsTable.columnAtPoint(event.getPoint());
                int modelColumn =
                        lastOperationsTable.convertColumnIndexToModel(tableColumn);
                sorter.sort(modelColumn);
            }

        });

        scrollLastOperationsTable = new JScrollPane(lastOperationsTable);

        add(scrollLastOperationsTable,
                new GridBagConstraints(1, 2, 5, 14, 0, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(0, 5, 0, 5), 0, 0));

        System.out.println("Добавлена талица операций");

        /////////////////////////////////////////////////////////////////////////////
        //
        //
        //                       Таблица счетов
        //
        //
        ///////////////////////////////////////////////////////////////////////////////

        accountListLabel= new JLabel("Текущие счета");
        add(accountListLabel,
                new GridBagConstraints(11, 12, 4, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.REMAINDER,
                        new Insets(5, 0, 5, 0), 0, 0));

//        accountsTableModel=new DefaultTableModel();
//        accountsTableModel.setColumnIdentifiers(ACCOUNTCOLUMNNAMES);
        JPanel accountsPanel=new JPanel();
        setAccountsTable();
        accountsTable=new JTable(accountsTableModel);
        accountsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        accountsTable.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        accountsTable.setPreferredScrollableViewportSize(new Dimension(200, 200));
                JScrollPane scrollPane = new JScrollPane(accountsTable);
        ListSelectionModel lsm=accountsTable.getSelectionModel();
        accountsTable.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                    deleteNewAccountButton.setEnabled(false);//To change body of overridden methods use File | Settings | File Templates.
            }
        });
//        accountsTable.setSelectionModel(ListSelectionModel.SINGLE_SELECTION);
        lsm.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
              deleteNewAccountButton.setEnabled(true);
            }
        });


        accountsPanel.setLayout(new BorderLayout());
        accountsPanel.add(scrollPane, BorderLayout.CENTER);
        accountsPanel.getPreferredSize().setSize(100,100);

        add(accountsPanel,
                new GridBagConstraints(11, 13, 4, 3, 0, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(0, 5, 0, 5), 0, 0));


        System.out.println("Добавлена таблица аккаунтов");

        ////////////////////////////////////////////////////////////////////
        //                                                                //
        //            Работа с категориями                                //
        //                                                                //
        //                                                                //
        //                                                                //
        //                                                                //
        ////////////////////////////////////////////////////////////////////

        ScrollCategoriesPane = new JScrollPane();
        NodePanel np = new NodePanel();
        tree = new JTree(np.getModel());
        tree.setEditable(true);
        //        tree.setMinimumSize(new Dimension(800, 500));
        tree.setVisibleRowCount(25);
        ScrollCategoriesPane.setViewportView(tree);
        tree.setRootVisible(false);

        ScrollCategoriesPane.validate();
        //        ScrollCategoriesPane.setMinimumSize(new Dimension(300, 500));
        //добавляем на панель
        add(ScrollCategoriesPane,
                new GridBagConstraints(7, 2, 3, 14, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(0, 5, 0, 5), 0, 0));
        //создаем кнопку для Добавления категории и размещаем ее на панели
        addNewCategoryButton = new JButton("Добавить категорию");
        add(addNewCategoryButton,
                new GridBagConstraints(7, 17, 3, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0), 0, 0));
        addNewCategoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                new AddNewCategoryFrame();
            }

        });

        //кнопка для удаления категорий и внесения изменений в файл XML
        deleteCategoryButton = new JButton("Удалить категорию");
        add(deleteCategoryButton,
                new GridBagConstraints(7, 18, 3, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0), 0, 0));
        deleteCategoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                XMLAnalize.deleteItemFromXml(tree.getLastSelectedPathComponent());
                NodePanel.deleteItem(e,
                        tree.getLastSelectedPathComponent());
            }
        });

        System.out.println("Добавлена работа с категориями");

        //создаем кнопку для отображения баланса по дням и месяцам
        balanceDayAndMonthButton = new JButton("Баланс по дням/месяцам");
        add(balanceDayAndMonthButton,
                new GridBagConstraints(11, 19, 1, 1, 0, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(5, 10, 5, 10), 0, 0));
        //создаем кнопку для отображения информации в графическом виде
        showGraphicsButton = new JButton("Графики");
        add(showGraphicsButton,
                new GridBagConstraints(11, 11, 4, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.REMAINDER,
                        new Insets(5, 0, 5, 0), 0, 0));
        //создаем кнопку для отображения отчета по счетам и категориям
        reportForCategoriesButton =
                new JButton("Отчет по счетам и категориям");
        add(reportForCategoriesButton,
                new GridBagConstraints(11, 21, 1, 1, 0, 0,
                        GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0), 0, 0));
        //создаем кнопку для сохранения и восстановления данных
        saveAndRestoreButton = new JButton("Сохранение и восстановление");
        add(saveAndRestoreButton,
                new GridBagConstraints(11, 23, 1, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0), 0, 0));
        System.out.println("Добавлены кнопки");

        //////////////////////////////////////////////////////////////////////
        //
        //              Добавление транзакции
        //
        /////////////////////////////////////////////////////////////////////
        addNewInOperationButton = new JButton("Добавить доход");
        add(addNewInOperationButton,
                new GridBagConstraints(1, 17, 1, 1, 0, 0, GridBagConstraints.WEST,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        addNewInOperationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                AddNewOperationFrame newCategoryFrame =
                        new AddNewOperationFrame(c);
            }
        });


        addNewOutOperationButton = new JButton("Добавить расход");
        add(addNewOutOperationButton,
                new GridBagConstraints(1, 18, 1, 1, 0, 0, GridBagConstraints.WEST,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
        addNewOutOperationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddNewOperationFrame newCategoryFrame =
                        new AddNewOperationFrame(c);
            }
        });


        addNewAccountButton = new JButton("Добавить счет");
        add(addNewAccountButton,
                new GridBagConstraints(12, 17, 1, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0), 0, 0));
        addNewAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddNewAccount anc = new AddNewAccount(c);
            }

        });

        deleteNewAccountButton = new JButton("Удалить счет");
        deleteNewAccountButton.setEnabled(false);
        add(deleteNewAccountButton,
                new GridBagConstraints(12, 18, 1, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.NONE,
                        new Insets(5, 0, 5, 0), 0, 0));
        deleteNewAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection connection=null;
                try {
                    connection=MyWallet.cp.ds.getConnection();
                    String accName=(String)accountsTableModel.getValueAt(accountsTable.getSelectedRow(), 0);
                    String deleteAccount= "DELETE FROM Counts Where Account='"+accName +"'";

                    PreparedStatement pst=connection.prepareStatement(deleteAccount);
                    int i=pst.executeUpdate();
                    if(i==1){
                        accountsTableModel.removeRow(accountsTable.getSelectedRow());
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                finally {

                    try {
                        connection.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }

            }
        });
        //
        System.out.println("Добавлены кнопки управления транзакциями");

        rs = new RunningString();

        add(rs,
                new GridBagConstraints(0, 24, 24, 1, 0, 0, GridBagConstraints.CENTER,
                        GridBagConstraints.BOTH,
                        new Insets(5, 0, 5, 0), 0, 0));
        Timer t1 = new Timer(5000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                visual.updateUI();
                visual.repaint();
            }
        });
        t1.setRepeats(true);
        t1.start();

        Timer t = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                rs.repaint();
                rs.updateUI();

            }
        });
        t.setRepeats(true);
        t.start();
        validate();
        revalidate();
        System.out.println(getSize());
        MyWallet.mainFrame.setMinimumSize(this.getSize());
        repaint();
        OperationToDB run=new OperationToDB(c, Operation.operate);
        Thread sqlThread=new Thread(run);
        sqlThread.start();

    }

    private void initCats() {

        try {
            conn=MyWallet.cp.ds.getConnection();
            String getCategories="SELECT * FROM '"+c.getLogin()+"categories'";
            Statement pst=conn.createStatement();
            ResultSet rs=pst.executeQuery(getCategories);
            System.out.println("сформированный резалт сет");
            ArrayList <String> cat=new ArrayList<String>();
            while(rs.next()){
                String s=(String)rs.getString(2);
                cat.add(s);
                String getSubCategories="Select * from '"+c.getLogin()+"subcategories' where id_cat=" +
                        "(Select id from '"+c.getLogin()+"categories' where category='"+s+"')";
                Statement pst1=conn.createStatement();
                ResultSet rs1=pst1.executeQuery(getSubCategories);
                ArrayList<String> subcat=new ArrayList<String>();
                while (rs1.next()){
                    String subString =(String)rs1.getString(2);
                    subcat.add(subString);
                    allCat.put(s,subcat);
}
            }
            System.out.println(allCat);
        } catch (Exception e) {
            System.out.println("ошибка");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        }

    private void creatingNessessaryDBs() {
        try {
            Connection connection=MyWallet.cp.ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    private void randomOperate() {



        for (int i = 0; i < 100; i++) {
                int r1 =
                        (int)(Math.random() * Math.random() * (allCat.size() -1));
                System.out.println(r1);
                String s1 = allCat.get("Категории").get(r1);
                int r = (int)(Math.random() * Math.random() * (allCat.get(s1).size()));
                String s2 = allCat.get(s1).get(r);
                float sum = (float)(Math.random() * 100);

                Operation randomOperation =
                        new Operation(c.getLogin(), s1, s2, Operation.createDateFromString("14.02.2014 00:22"),
                                c.getAcc().get(0).getAccount(),
                                sum);
                Operation.operate.add(randomOperation);
                ManePanel.setLastOperationsTable();

//                AddNewOperationFrame.addingOperationToXML(fileOperation, randomOperation);
            }





    }


//    public static void setCurrentBalance(String str) {
//        currentBalance.setText(str);
//    }

    public static void setLastOperationsTable() {
        Object[] table = new Object[5];
        table[0] =
                Operation.createDate(Operation.operate.get(Operation.operate.size() -
                        1).getD());
        table[1] =
                Operation.operate.get(Operation.operate.size() - 1).getCat();
        table[2] =
                Operation.operate.get(Operation.operate.size() - 1).getSubcat();
        table[3] =
                Operation.operate.get(Operation.operate.size() - 1).getAccount();
        table[4] = Operation.operate.get(Operation.operate.size() - 1).getF();
        dTableModel.addRow(table);

    }


    private void randomOperateXML() {
        XMLAnalize xmla = new XMLAnalize();
        for (int i = 0; i < 1000; i++) {
            int r1 =
                    (int)(Math.random() * Math.random() * (xmla.getTm().size() -
                            1));
            int r2 =
                    (int)(Math.random() * Math.random() * (xmla.getTm().size() -
                            1));

            String s1 = xmla.getTm().get("Категории").get(r1);
            int r =
                    (int)(Math.random() * Math.random() * (xmla.getTm().get(s1).size()));
            String s2 = xmla.getTm().get(s1).get(r);
            float sum = (float)(Math.random() * 100);

            Operation randomOperation =
                    new Operation(c.getLogin(), s1, s2, Operation.createDateFromString("14.02.2014 00:22"),
                            c.getAcc().get(0).getAccount(),
                            sum);
            Operation.operate.add(randomOperation);

            ManePanel.setLastOperationsTable();

//            AddNewOperationFrame.addingOperationToXML(fileOperation,
//                    randomOperation);
        }

    }

    public static void setAccountsTable() {
        Statement st=null;
        Connection conn=null;
        try {
            conn=MyWallet.cp.ds.getConnection();
            st=conn.createStatement();
            accountsTableModel=new DefaultTableModel();

            ResultSet rs=st.executeQuery("SELECT Counts.Account,Counts.Balance, Counts.Currency " +
                    "from Counts where Counts.id_users=(SELECT Users.id FROM Users Where Users.Login= '" + c.getLogin() + "'limit 1)");
            ResultSetMetaData rsmd=rs.getMetaData();
            int n=rsmd.getColumnCount();


                accountsTableModel.setColumnIdentifiers(ACCOUNTCOLUMNNAMES);

            while(rs.next()){
                Vector<String>  row = new Vector<String>();

                for(int col = 1; col <= rsmd.getColumnCount(); col++) {
                    row.add(rs.getString(col));

                }
                accountsTableModel.addRow(row);
                accountsTableModel.fireTableDataChanged();
            }

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally{
            assert st != null;
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
    private RunningString rs;
    private final SortFilterModel sorter;
    private JLabel userLabel;
    private JLabel incomingLabel;
    private static JTable lastOperationsTable;
    private static JScrollPane scrollLastOperationsTable;

    private JLabel accountListLabel;

    private JButton addNewCategoryButton;
    private JButton balanceDayAndMonthButton;
    private JButton showGraphicsButton;
    private JButton reportForCategoriesButton;
    private JButton saveAndRestoreButton;
    private JButton addNewOutOperationButton;
    private JTree tree;
    private static User c = null;
    private JLabel visualizationLabel;
    private JButton addNewInOperationButton;
    private JScrollPane ScrollCategoriesPane;
    private JButton deleteCategoryButton;
    private JButton addNewAccountButton;
//    private final JTable accountsTable = new JTable();
    private static final String[] COLUMNNAMES =
            { "Дата операции", "Категория", "Подкатегория", "Счет", "Сумма" };
    private static final String[] ACCOUNTCOLUMNNAMES={"Счет", "Сумма", "Валюта"};
    private static DefaultTableModel dTableModel;
    private File fileOperation;
    private JTable accountsTable=null;
    public static DefaultTableModel accountsTableModel=null;
    public HashMap<String, ArrayList<String>> allCat=new HashMap<String, ArrayList<String>>();
}
